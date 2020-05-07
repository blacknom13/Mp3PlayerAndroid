package com.example.musicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.*;

public class AdvancedFileManager extends AppCompatActivity implements OnItemClickListener{

	final int RESULT=2;
	TabHost fileManager;
	ArrayList<com.example.musicplayer.Song> songList;
	ArrayList<MyFile> myFileList;
	ListView songView,fileView;
	TextView thePath;
	Button back;
	com.example.musicplayer.FileAdapter fileAdapter;
	File main;
	boolean noExternalStorage;
	String finalInternalStorage, finalExternalStorage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advanced_file_manager);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			// Permission is not granted
			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
					Manifest.permission.READ_EXTERNAL_STORAGE)) {
				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
			} else {
				// No explanation needed, we can request the permission.
				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
						RESULT);

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		} else {
			// Permission has already been granted
		}
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			// Permission is not granted
			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
					Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
			} else {
				// No explanation needed, we can request the permission.
				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
						RESULT);

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		} else {
			// Permission has already been granted
		}
		finalExternalStorage="";
		finalInternalStorage="";
		File [] dirs= ContextCompat.getExternalCacheDirs(getApplicationContext());
		String [] internalStorage=dirs[0].getAbsolutePath().split(File.separator);
		finalInternalStorage=File.separator+internalStorage[1]+File.separator+internalStorage[2]+
				File.separator+internalStorage[3]+File.separator;
		String [] externalStorage;
		if (dirs.length==2) {
			externalStorage = dirs[1].getAbsolutePath().split(File.separator);
			finalExternalStorage = File.separator + externalStorage[1] + File.separator + externalStorage[2] + File.separator;
			noExternalStorage=false;
		}else
		{
			noExternalStorage=true;
		}
//		main=dirs[0];
		main=new File(finalInternalStorage);
		main=new File(finalInternalStorage+File.separator+"Main My Music Player");
		File internal=new File (main.getAbsolutePath()+File.separator+"internal storage");
		File external=null;
		if(!noExternalStorage)
			external=new File(main.getAbsolutePath()+File.separator+"external storage");
		if (!main.exists())
			main.mkdir();
		if (!internal.exists())
			internal.mkdir();
		if (!noExternalStorage && !external.exists())
			external.mkdir();
		if (getIntent().getExtras()!=null && !getIntent().getExtras().getString("set").equals(""))
			main=new File(getIntent().getExtras().getString("set"));
		thePath=(TextView)findViewById(R.id.tVPath);
		back=(Button)findViewById(R.id.bBack);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String f=thePath.getText().toString();
				if (!f.endsWith("Main My Music Player")) {
					f = f.substring(0, f.lastIndexOf("/"));
					if (f.length() == 0)
						f = "/";
				}

				getFileList(new File(f));
			}
		});
		fileManager=(TabHost)findViewById(R.id.tabhost);
		fileManager.setup();
		TabSpec spec= fileManager.newTabSpec("Music");
		
		spec.setIndicator("Music",getResources().getDrawable(R.drawable.note_neon));
		//spec.setContent;
		//Intent i=new Intent(this,MusicTab.class);
		spec.setContent(R.id.tMusic);
		fileManager.addTab(spec);

		spec=fileManager.newTabSpec("Manager");
		
		spec.setContent(R.id.tFileManager);
		spec.setIndicator("File Manager",getResources().getDrawable(R.drawable.folder_neon));
		fileManager.addTab(spec);
		
		
		fileView=(ListView)findViewById(R.id.lVFiles);
		fileView.setOnItemClickListener(this);
		
		myFileList=new ArrayList<MyFile>();
		
		getFileList(main);
		
		
		songView=(ListView)findViewById(R.id.lVSongs);
		songView.setOnItemClickListener(this);
		songList=new ArrayList<com.example.musicplayer.Song>();
		getSongList();
		Collections.sort(songList, new Comparator<com.example.musicplayer.Song>() {

			@Override
			public int compare(com.example.musicplayer.Song lhs, com.example.musicplayer.Song rhs) {
				// TODO Auto-generated method stub
				return (lhs.getTitle().compareTo(rhs.getTitle()));
			}
		});
		SongAdapter songAdapter=new SongAdapter(this, songList);
		songView.setAdapter(songAdapter);
	}

	private void getFileList(File f) {
//		// TODO Auto-generated method stub
		Bitmap icon1=BitmapFactory.decodeResource(getResources(), R.drawable.no_icon);
		Bitmap icon2=BitmapFactory.decodeResource(getResources(), R.drawable.folder_neon);
		List<File> fileList;
		myFileList.clear();
		if (f.getAbsolutePath().endsWith("Main My Music Player/internal storage")) {
			thePath.setText(finalInternalStorage.substring(0, finalInternalStorage.length() - 1));
			f=new File(finalInternalStorage);
		}
		else if (f.getAbsolutePath().endsWith("Main My Music Player/external storage")) {
			if (!noExternalStorage) {
				thePath.setText(finalExternalStorage.substring(0, finalExternalStorage.length() - 1));
				f = new File(finalExternalStorage);
			}
		}
		else
			thePath.setText(f.getAbsolutePath());
		fileList=Arrays.asList(f.listFiles());
		for (int i=0;i<fileList.size();i++)
		{
			File currentFile=fileList.get(i);
			String fileName=currentFile.getName();
			String fileSize;

			if (currentFile.isDirectory())
			{
				fileSize="------";
				myFileList.add(new MyFile(0, fileName, fileSize, icon2,true));
			}
			else
			{
				fileSize=String.format(Locale.US,"%.2f MB", currentFile.length()/1048576.0);
				myFileList.add(new MyFile(0, fileName, fileSize, icon1,false));
			}
			
		}
		Collections.sort(myFileList, new Comparator<MyFile>() {

			@Override
			public int compare(MyFile lhs, MyFile rhs) {
				// TODO Auto-generated method stub
				if (lhs.isDir() && rhs.isDir() || (!lhs.isDir() && !rhs.isDir()))
					return (lhs.getFileName().compareToIgnoreCase(rhs.getFileName()));
				else if (lhs.isDir() && !rhs.isDir())
					return (-1);
					else
						return (1);
			}
		});
		fileAdapter=new com.example.musicplayer.FileAdapter(this, myFileList);
		fileView.setAdapter(fileAdapter);
	}

	private void getSongList() {
		// TODO Auto-generated method stub
		ContentResolver cr=getApplicationContext().getContentResolver();
		Uri musicUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor musicCursor=cr.query(musicUri, null, null, null, null);
		Uri albumUri= Albums.EXTERNAL_CONTENT_URI;
		Cursor albumCursor=cr.query(albumUri, null, null, null, null);
		Toast.makeText(this, String.valueOf(musicCursor.getCount()), Toast.LENGTH_LONG).show();
		if(musicCursor != null && musicCursor.moveToFirst())
		{
			int title=musicCursor.getColumnIndex(Media.TITLE);
			int album=musicCursor.getColumnIndex(Media.ALBUM);
			int artist=musicCursor.getColumnIndex(Media.ARTIST);
			int id=musicCursor.getColumnIndex(Media._ID);
			int isMusic=musicCursor.getColumnIndex(Media.IS_MUSIC);
			int albId=musicCursor.getColumnIndex(Media.ALBUM_ID);
			//int ss=musicCursor.getColumnIndex(Media.)
			//int songPath=musicCursor.getColumnIndex(Media)
			
			
			do
			{
				Bitmap b=null;//BitmapFactory.decodeResource(getResources(), R.drawable.no_icon);
				String x="";
				if (musicCursor.getString(isMusic).equals("1"))
				{
					String songTitle=musicCursor.getString(title);
					String songAlbum=musicCursor.getString(album);
					String songArtist=musicCursor.getString(artist);
					long songId=musicCursor.getLong(id);
					long albumId=musicCursor.getLong(albId);
					
//					if(albumCursor != null && albumCursor.moveToFirst()){
//						int albIdFromAlb=albumCursor.getColumnIndex(Albums._ID);
//						int art=albumCursor.getColumnIndex(Albums.ALBUM_ART);
//					
//						do{
//							if (albumId==albumCursor.getLong(albIdFromAlb))
//							{
//								if (albumCursor.getString(art)!=null){
//									x=albumCursor.getString(art);
//									b=BitmapFactory.decodeFile(albumCursor.getString(art));
//								break;}
//							}
//						}while (albumCursor.moveToNext());
//					}
					//if (x.length()<=0)
						//b=BitmapFactory.decodeResource(getResources(), R.drawable.no_icon);
					//Toast.makeText(this, songTitle, Toast.LENGTH_SHORT).show();
					songList.add(new com.example.musicplayer.Song(songId,songTitle,songAlbum,songArtist,b));
				}
			}while(musicCursor.moveToNext());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch(parent.getId()){
		case R.id.lVFiles:
			String currFile=((TextView)view.findViewById(R.id.tVFileName)).getText().toString();
			//myFileList.clear();
			//String fileN=((MyFile)view).getFileName();
			File x=new File(thePath.getText().toString()+"/"+currFile);
			if (x.isDirectory())
				getFileList(x);
			else
			{
				if (com.example.musicplayer.MainActivity.getInstance()!=null)
					com.example.musicplayer.MainActivity.getInstance().finish();
				Intent i =new Intent(this, com.example.musicplayer.MainActivity.class);
//				if (i.hasExtra("set"))
//					i.getExtras().remove("set");
				i.putExtra("set", x.getAbsolutePath());
				//Toast.makeText(this, x.getAbsolutePath(), Toast.LENGTH_LONG).show();
				startActivity(i);
			}
			break;
		case R.id.lVSongs:
//			Toast.makeText(this, String.valueOf("bye"), Toast.LENGTH_LONG).show();
//			ContentResolver cr=getContentResolver();
		//	MediaPlayer mus=new MediaPlayer();
			if (com.example.musicplayer.MainActivity.getInstance()!=null)
				com.example.musicplayer.MainActivity.getInstance().finish();
			Uri musicTable=ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI,id);
			Intent intent=new Intent(this, com.example.musicplayer.MainActivity.class);
			//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			//Toast.makeText(this, String.valueOf(id), Toast.LENGTH_LONG).show();
			intent.putExtra("seto", musicTable);
			startActivity(intent);
//			try {
//				mus.setDataSource(this, musicTable);
//				mus.prepare();
//				mus.start();
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Cursor mus=cr.query(musicTable,null,null, null, null);
//			if (mus!=null && mus.moveToFirst())
//				do{
//					
//				}while m
			break;
		default:
			break;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode){
			case RESULT:
				if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
					getSongList();
				}else
				{

				}
		}
	}
}
