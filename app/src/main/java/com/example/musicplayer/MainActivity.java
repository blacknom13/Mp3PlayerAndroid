package com.example.musicplayer;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainActivity extends AppCompatActivity implements OnClickListener,OnLongClickListener{

	final String[] states={"no repeatation","repeat this song","repeat this folder","move to next folder"};
	final int[] ids={R.drawable.no_repeatation, R.drawable.repeat_one_neon ,R.drawable.repeat_album_neon ,R.drawable.move_to_next_neon};
	Button play,next,prev,load,repeat,nextFolder,prevFolder;
	int cursorColor,currentId,folderDirection,state,trackCounter,currentTrack;
	boolean wasRed,wasBlue,withLatency,first;
	TextView left,right,elapsed,title,album,artist,tracks;
	SeekBar cursor;
	MediaPlayer mus;
	String path,showTime;
	final Handler handler=new Handler();
	List <String> files,folderTree;
	ImageView art;
	SharedPreferences sharedPreferences;
	static com.example.musicplayer.MainActivity mainActivity;
	Uri pathe;
	
	
	void init()
	{
		//Toast.makeText(this, "I started", Toast.LENGTH_SHORT).show();
		play=(Button)findViewById(R.id.bPlay);
		next=(Button)findViewById(R.id.bNext);
		prev=(Button)findViewById(R.id.bPrev);
		load=(Button)findViewById(R.id.bLoad);
		repeat=(Button)findViewById(R.id.bRepeat);
		nextFolder=(Button)findViewById(R.id.bNextFolder);
		prevFolder=(Button)findViewById(R.id.bPrevFolder);
		play.setOnClickListener(this);
		next.setOnClickListener(this);
		prev.setOnClickListener(this);
		load.setOnClickListener(this);
		repeat.setOnClickListener(this);
		nextFolder.setOnClickListener(this);
		prevFolder.setOnClickListener(this);
		cursor=(SeekBar)findViewById(R.id.mSeekBar);
		cursor.getProgressDrawable().setColorFilter(Color.parseColor("#60f2e6"), Mode.SRC_IN);
		cursor.setClickable(true);
		left=(TextView)findViewById(R.id.tVLeft);
		right=(TextView)findViewById(R.id.tVRight);
		elapsed=(TextView)findViewById(R.id.tVElapsed);
		title=(TextView)findViewById(R.id.tVTitle);
		album=(TextView)findViewById(R.id.tVAlbum);
		artist=(TextView)findViewById(R.id.tVArtist);
		tracks=(TextView)findViewById(R.id.tVTrackCounter);
		art=(ImageView)findViewById(R.id.ivArt);
		title.setOnLongClickListener(this);
		album.setOnLongClickListener(this);
		artist.setOnLongClickListener(this);
		//path=getIntent().getExtras().getString("set");
		currentId=R.id.bPrev;
		trackCounter=0;
		elapsed.setVisibility(View.INVISIBLE);
//		path=new String("/");
//		if ((new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath()).list().length)>0){
//			String filen=new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath()).list()[1];
//			path=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath()+"/"+filen;
//		}
		mainActivity=this;
		//path=Environment.DIRECTORY_MUSIC;
		//Toast.makeText(this, path, Toast.LENGTH_LONG).show();
		//needs revisit
		//showTime=PreferenceManager.getDefaultSharedPreferences(this).getString("show_time", "elap");
		//sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
		//state=sharedPreferences.getInt("state", 3);
		//folderDirection=R.id.bNextFolder;
		first=true;
//		repeat.setText(sharedPreferences.getString("repeat", "no_repeatation"));
		//if (sharedPreferences.contains("songPath")){
		//path=sharedPreferences.getString("songPath", path);
		//prepareSongPlayer(path);}
		//if (sharedPreferences.contains("currentPosition"))
		//	mus.seekTo(sharedPreferences.getInt("currentPosition", 0));
		//if (sharedPreferences.contains("songPath")){
		//	withLatency=!sharedPreferences.getString("songPath",";").equals(";");
		//	repeat.performClick();
		//	startPlayProgressUpdater();}
		//folderTree=new ArrayList<String>();
		//mus.pause();
		//int size=sharedPreferences.getInt("treeSize", 0);
		//if (size>0){
			
		//	for (int i=0;i<size;i++)
		//		folderTree.add(sharedPreferences.getString("tree"+i, "void"));
		//}
		//else
//		new Runnable() {
//			public void run() {
//				initFolderTree("/mnt/sdcard/Music");
//				removingVoids();
//				Toast.makeText(getBaseContext(), String.valueOf(folderTree.size()), Toast.LENGTH_LONG).show();
//			}
//		}.run();
		//if (sharedPreferences.contains("firstTime"))
		//	first=sharedPreferences.getBoolean("firstTime", true);
		//else
		//	first=true;
		//if (!sharedPreferences.getBoolean("firstTime", true))
		//for (int i=0;i<folderTree.size();i++)
		//Toast.makeText(this, path, Toast.LENGTH_LONG).show();
		//prepareSongPlayer(path);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		init();

//		pathe=(Uri)(getIntent().getExtras().get("seto"));
//		path=getIntent().getExtras().getString("set");
//		initFolderTree(path);
//		if (pathe!=null){
//			prepareSongPlayer(pathe);
//			path="/";
//			//Toast.makeText(this, "I was here", Toast.LENGTH_LONG).show();
//		}
//		else
//		{
//			prepareSongPlayer(path);
//			//Toast.makeText(this, "Was here too", Toast.LENGTH_SHORT).show();
//		}
//		try {
//			mus.setDataSource(this, pathe);
//			mus.start();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//prepareSongPlayer(pathe);
	}

	private boolean isExternalMediaWritable() {
		// TODO Auto-generated method stub
		String state= Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
			return true;
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		MenuInflater inflater=getMenuInflater();
//		menu.add(0, 0, 0, "me").setIcon(R.drawable.settings);
		inflater.inflate(R.menu.main, menu);
		return true;
	}

//	@Override
//	public boolean onMenuOpened(int featureId, Menu menu) {
//		// TODO Auto-generated method stub
//		if (featureId==Window.FEATURE_OPTIONS_PANEL && menu!=null)
//		{
//			if(menu.getClass().getSimpleName().equals("MenuBuilder"))
//			{
//				try {
//					Method m=menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
//					m.setAccessible(true);
//					m.invoke(menu, true);
//				} catch (NoSuchMethodException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}catch (Exception e){
//					throw new RuntimeException(e);
//				}
//			}
//		}
//		return super.onMenuOpened(featureId, menu);
//	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode==RESULT_OK)
//		{
//			Bundle bun=data.getExtras();
//			path=bun.getString("back");
//			prepareSongPlayer(path);
//			//Toast.makeText(this, path, 5000).show();
//			//Uri musicPath= android.provider.MediaStore.Audio.Media.getContentUriForPath(path);
//		}
//	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.oExit:
			finish();
			break;
		case R.id.action_settings:
			Intent settings=new Intent(this,MyPreferences.class);
			startActivity(settings);
			//getIntent().getExtras().get("showTime");
			
			//Toast.makeText(MainActivity.this, showTime, Toast.LENGTH_LONG).show();
			break;
		case R.id.oAbout:
			Intent about=new Intent(this,AboutApp.class);
			startActivity(about);
			//File f=getDatabasePath("prefs.xml");
			//Toast.makeText(this, f.getAbsolutePath(), Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		pathe=(Uri)(getIntent().getExtras().get("seto"));
		path=getIntent().getExtras().getString("set");
		if (pathe!=null){
			//Toast.makeText(this, String.valueOf(getIntent().getExtras().get("seto")+ "from onResume"), Toast.LENGTH_LONG).show();
			prepareSongPlayer(pathe);
			path="/";
			
		}
		else if (path!=null)
		{
			prepareSongPlayer(path);
			initFolderTree(getIntent().getExtras().getString("ExternalStorage"));
			//Toast.makeText(this, "Was here too", Toast.LENGTH_SHORT).show();
		}
		//showTime=PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("show_time", "elap");
	}

	public void startPlayProgressUpdater() {
		if (mus!=null || mus.isPlaying()){
		//Toast.makeText(this, String.valueOf(Color.BLACK), 5000).show();
		int delayed=1;
		int visible=0;
		int elap=mus.getDuration()-mus.getCurrentPosition();
		//cursorColor=update_progress(cursorColor);
		//cursor.getProgressDrawable().setColorFilter(cursorColor, Mode.SRC_IN);
		if (!cursor.isPressed())
			cursor.setProgress(mus.getCurrentPosition());
		else
		{
			mus.seekTo(cursor.getProgress());
		}
		//if (showTime.equals("elap"))
			left.setText(String.format("%d : %02d",mus.getCurrentPosition()/60000,(mus.getCurrentPosition()/1000)%60));
		//else
		//	left.setText(String.format("-%d : %02d",elap/60000,(elap/1000)%60));
		
		if (!mus.isPlaying())
		{
			delayed=50;
			visible=left.getVisibility();
			if (visible==View.VISIBLE)
				visible=View.INVISIBLE;
			else
				visible=0;
		}
		left.setVisibility(visible);
        //elapsed.setText(String.format("-%d : %02d",elap/60000,(elap/1000)%60));
        //if (mus.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 10*delayed);
        }
	}
	//}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.bPrev:
			case R.id.bNext:
				currentId=v.getId();
				if (mus!=null){
				moveSong(v.getId());
				}else moveSong(currentId);
				break;
			case R.id.bPlay:
				if (mus!=null){
					if (mus.isPlaying())
					{
						play.setBackgroundResource(com.example.musicplayer.R.drawable.play_neon);
						mus.pause();
					}
					else
					{
						mus.start();
						play.setBackgroundResource(com.example.musicplayer.R.drawable.pause_neon);
						//startPlayProgressUpdater();
					}
				}else moveSong(currentId);
				break;
			case R.id.bLoad:
					Intent i=new Intent(this,AdvancedFileManager.class);
					i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					if (path.equals("/")){
						//Toast.makeText(this, "been here", Toast.LENGTH_SHORT).show();
						i.putExtra("set",path);
					}
					else
						i.putExtra("set", path.substring(0, path.lastIndexOf("/")));
					startActivity(i);
				break;
			case R.id.bRepeat:
				state++;
				state%=4;
				Toast.makeText(com.example.musicplayer.MainActivity.this, states[state], Toast.LENGTH_SHORT).show();
				repeat.setText(states[state]);
				repeat.setBackgroundResource(ids[state]);
				if (mus!=null){
				if (state==1)
					mus.setLooping(true);
				else
					mus.setLooping(false);}
				break;
			case R.id.bNextFolder:
				currentId=R.id.bNext;
				folderDirection=R.id.bNextFolder;
				moveFolder(currentId,true);
				//moveSong(currentId);
				break;
			case R.id.bPrevFolder:
				currentId=R.id.bPrev;
				folderDirection=R.id.bPrevFolder;
				moveFolder(R.id.bPrev,true);
				folderDirection=R.id.bNextFolder;
				//moveSong(currentId);
				break;
		}
	}

	int update_progress(int prevColor)
	{
		if (!wasBlue)
		{
			if (prevColor>=0xff0000ff)
			{
				prevColor=0xff0001fe;
				wasBlue=true;
			}
			else
				prevColor=prevColor+0x1;
		}
		else if (!wasRed && wasBlue)
		{
			if (prevColor>=0xff00ff00)
			{
				prevColor=0xff01fe00;
				wasRed=true;
			}
			else
				prevColor=prevColor+0x100-0x1;
		}	
		//if (wasRed && wasBlue)
		else
		{
			if (prevColor>=0xffff0000)
			{
				prevColor=0xff00000;
				wasBlue=false;
				wasRed=false;
			}
			else
				prevColor=prevColor+0x10000-0x100;
		}
		return (prevColor);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		sharedPreferences.edit().putString("songPath",path).commit();
//		sharedPreferences.edit().putInt("currentPosition", mus.getCurrentPosition()).commit();
//		sharedPreferences.edit().putBoolean("firstTime", false).commit();
//		sharedPreferences.edit().putInt("state", state-1).commit();
		//sharedPreferences.edit().putInt("treeSize", folderTree.size()).commit();
		//sharedPreferences.edit().remove("treeSize").commit();
		//for (int i=0;i<folderTree.size();i++)
		//	sharedPreferences.edit().remove("tree"+i).commit();
		if (mus!=null && mus.isPlaying())
		{
			mus.stop();
			//mus.release();
		}
		finish();
	}
	
	void prepareSongPlayer(Uri path)
	{
		try {
			//mus=MediaPlayer.create(this, musicPath);
			if (mus!=null && mus.isPlaying())
			{
				mus.stop();
			}
			if (mus!=null)
				mus.release();
			mus=new MediaPlayer();
	//		mus.
			mus.setDataSource(this,path);
		//	Toast.makeText(MainActivity.this, String.valueOf(path)+"from prepare", Toast.LENGTH_LONG).show();
			//Toast.makeText(this, path, Toast.LENGTH_LONG).show();
			//cursor.getProgressDrawable().setColorFilter(Color.BLUE, Mode.SRC_IN);
			//setImage(path);
			getSongInfo(null,path);
			mus.prepare();
			mus.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			
				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					cursor.setMax(mp.getDuration());
					//right.setText(mp.getDuration());
					wasRed=false;
					wasBlue=false;
					cursorColor=0xff000000;
					if (!withLatency){
						mp.start();
						play.setBackgroundResource(com.example.musicplayer.R.drawable.pause_neon);
					}else
						withLatency=false;
					if (repeat.getText().equals("repeat this song"))
						mp.setLooping(true);
					right.setText(String.format("%d : %02d",mp.getDuration()/60000,(mp.getDuration()/1000)%60));
					if (first){
						startPlayProgressUpdater();
						first=false;
						}
				}
			});
			mus.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					if (!mp.isLooping()){
						//Toast.makeText(getBaseContext(), "been on complete", Toast.LENGTH_LONG).show();					
						moveSong(R.id.bNext);
					}
				}
			});
			//mus.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(getBaseContext(), "here is illegal", Toast.LENGTH_LONG).show();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(getBaseContext(), "been in Ioexeption", Toast.LENGTH_LONG).show();
			if (folderDirection==R.id.bPrevFolder)
				moveSong(R.id.bNext);
			else
				moveSong(currentId);
		}	
		//Toast.makeText(this, path, 5000).show();
		//right.setText("rouly");

	}
	
	
	void prepareSongPlayer(String path)
	{
		try {
			//mus=MediaPlayer.create(this, musicPath);
			if (mus!=null && mus.isPlaying())
			{
				mus.stop();
			}
			if (mus!=null)
				mus.release();
			mus=new MediaPlayer();
			
	//		mus.
			mus.setDataSource(path);
			//Toast.makeText(this, path, Toast.LENGTH_LONG).show();
			//cursor.getProgressDrawable().setColorFilter(Color.BLUE, Mode.SRC_IN);
			//setImage(path);
			getSongInfo(path,null);
			mus.prepare();
			mus.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			
				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					cursor.setMax(mp.getDuration());
					//right.setText(mp.getDuration());
					wasRed=false;
					wasBlue=false;
					cursorColor=0xff000000;
					if (!withLatency){
						mp.start();
						play.setBackgroundResource(com.example.musicplayer.R.drawable.pause_neon);
					}else
						withLatency=false;
					if (repeat.getText().equals("repeat this song"))
						mp.setLooping(true);
					right.setText(String.format("%d : %02d",mp.getDuration()/60000,(mp.getDuration()/1000)%60));
					if (first){
						startPlayProgressUpdater();
						first=false;
						}
				}
			});
			mus.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					if (!mp.isLooping()){
						//Toast.makeText(getBaseContext(), "been on complete", Toast.LENGTH_LONG).show();					
						moveSong(R.id.bNext);
					}
				}
			});
			//mus.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(getBaseContext(), "here is illegal", Toast.LENGTH_LONG).show();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(getBaseContext(), "been in Ioexeption", Toast.LENGTH_LONG).show();
			if (folderDirection==R.id.bPrevFolder)
				moveSong(R.id.bNext);
			else
				moveSong(currentId);
		}	
		//Toast.makeText(this, path, 5000).show();
		//right.setText("rouly");

	}

//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
//		outState.putString("left", left.getText().toString());
//		outState.putString("right", right.getText().toString());
//		if (mus!=null)
//		outState.putInt("musPos", mus.getCurrentPosition());
//	}
//	
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onRestoreInstanceState(savedInstanceState);
//		right.setText(savedInstanceState.getString("right"));
//		left.setText(savedInstanceState.getString("left"));
//		mus.seekTo(savedInstanceState.getInt("musPos"));
//	}
	
	private void moveSong(int viewId) {
		// TODO Auto-generated method stub
		String filename=path.substring(path.lastIndexOf("/")+1);
		int prevSong=0,opp=1;
		boolean broken=false;
		path=path.substring(0, path.lastIndexOf("/"));
		File x=new File(path);
		if (x.isDirectory()){
		files=Arrays.asList(x.list());
		//Collections.sort(files);
	    Collections.sort(files, new Comparator<String>() {
	    	public int compare(String a,String b)
	    	{
	    		if (!new File(path+"/"+a).isDirectory() && new File(path+"/"+b).isDirectory())
	    			return 1;
	    		else if (new File(path+"/"+a).isDirectory() && !new File(path+"/"+b).isDirectory())
	    			return -1;
	    		else
	    			return a.compareTo(b);
	    	}
		});
		if (viewId==R.id.bNext)
			opp*=-1;
		int j=opp;
		do{
			if (repeat.getText().equals("no repeatation")||repeat.getText().equals("repeat this song")){
				if (files.indexOf(filename)-j<0 || files.indexOf(filename)-j>=files.size())
				{
					Toast.makeText(com.example.musicplayer.MainActivity.this, "The playlist finished", Toast.LENGTH_LONG).show();
					broken=true;
					break;
				}
				else
					prevSong=(files.indexOf(filename)-j);
			}else if (repeat.getText().equals("move to next folder")){
				if (files.indexOf(filename)-j<0 || files.indexOf(filename)-j>=files.size())
				{
					if (folderDirection==R.id.bPrevFolder)
						moveFolder(R.id.bPrev,true);
					else
						moveFolder(viewId, false);
					if (new File(path).isDirectory()){
					files=Arrays.asList(new File(path).list());
					Collections.sort(files, new Comparator<String>() {
				    	public int compare(String a,String b)
				    	{
				    		if (!new File(path+"/"+a).isDirectory() && new File(path+"/"+b).isDirectory())
				    			return 1;
				    		else if (new File(path+"/"+a).isDirectory() && !new File(path+"/"+b).isDirectory())
				    			return -1;
				    		else
				    			return a.compareTo(b);
				    	}
					});
					prevSong=(files.size()-1)*((opp==1)?1:0);
					j=0;}
					//filename=files.get(0);
				}
				else
					prevSong=(files.indexOf(filename)-j);}
			else{
				if (files.indexOf(filename)-j<0 || files.indexOf(filename)-j>=files.size()){
					//Toast.makeText(MainActivity.this, repeat.getText(), Toast.LENGTH_LONG).show();
					prevSong=(files.indexOf(filename)-j)+(files.size()*opp);
				}
				else
					prevSong=(files.indexOf(filename)-j);
				}
			j+=opp;
		}while (new File(path+"/"+files.get(prevSong)).isDirectory());
		if (!broken){
		filename=files.get(prevSong);
		path+="/"+filename;
		if (mus !=null && mus.isPlaying())
			mus.stop();
		//Toast.makeText(getBaseContext(), path, Toast.LENGTH_LONG).show();
		prepareSongPlayer(path);}else
		{
			path+="/"+filename;
			//Toast.makeText(getBaseContext(), path, Toast.LENGTH_LONG).show();
		}
		}
	}

	
private void moveFolder(int nextPrevFolder,boolean fromMoveFold) {
	// TODO Auto-generated method stub
	int opp=1;
	if (nextPrevFolder==R.id.bNext)
		opp*=-1;
	String file=path;
	if (fromMoveFold)
		file=path.substring(0,path.lastIndexOf("/"));
	//Toast.makeText(getBaseContext(), file, Toast.LENGTH_LONG).show();
	//path=folderTree.get(folderTree.indexOf(path)-1*opp);
	if (folderTree.indexOf(file)-opp>=folderTree.size() || folderTree.indexOf(file)-opp<0)
	{
		file=folderTree.get(folderTree.indexOf(file)-opp+(folderTree.size()*opp));
		//Toast.makeText(getBaseContext(), file, Toast.LENGTH_LONG).show();
		//broken=true;
		//break;
	}
	else
		file=folderTree.get(folderTree.indexOf(file)-opp);
	//Toast.makeText(getBaseContext(), file, Toast.LENGTH_LONG).show();
	path=file;pathCounter(path);
	if (fromMoveFold){
		pathCounter(path);
		path+="/"+listAndSort(file).get(0);
		if(!new File(path).isDirectory())
			prepareSongPlayer(path);
		else
			moveSong(R.id.bNext);
		//Toast.makeText(getBaseContext(), path, duration)
	}
	
	//oveSong(nextPrevFolder);
}

//	private void setImage(String path) {
//		// TODO Auto-generated method stub
//		path=path.substring(0, path.lastIndexOf("/"));
//		File x=new File(path);
//		files=Arrays.asList(x.list());
//		Collections.sort(files);
//		int j=0;
//		while (j<files.size())
//		{
//		Toast.makeText(MainActivity.this, String.valueOf(isImage(new File(path+"/"+files.get(j)))), 5000).show();
//			if (!isImage(new File(path+"/"+files.get(j)))) 
//				j++;
//			else
//			{
//				art.setImageBitmap(BitmapFactory.decodeFile(path+"/"+files.get(j)));
//				break;
//			}
//		}
//		art.setBackgroundDrawable(Drawable.createFromPath(path+"/3-idiots1.jpg"));
//	}

//	private boolean isImage(File file) {
//		// TODO Auto-generated method stub
//		BitmapFactory.Options options=new BitmapFactory.Options();
//		options.inJustDecodeBounds=true;
//		BitmapFactory.decodeFile(path, options);
//		return options.outWidth != -1 && options.outHeight !=-1;
//	}
//	
	private void getSongInfo(String path,Uri pathe)
	{
		String paths="";
		boolean number=false;
		if (pathe!=null){
			paths=pathe.toString();
			number=true;
		}
		ContentResolver contentResolver= getContentResolver();
		Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor musCursor=contentResolver.query(musicUri, null, null, null, null);
		long id=0;
		String []h=musCursor.getColumnNames();
//		for (int i = 0; i < h.length; i++) {
			//Toast.makeText(getBaseContext(), musicUri.toString(), Toast.LENGTH_LONG).show();
//		}
		//Uri artwork=MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		//Cursor artCursor=contentResolver.query(artwork, null, null, null, null);

		//Uri albumartUri=ContentUris.(musicUri, );
		
		if(musCursor!=null && musCursor.moveToFirst())
		{
			int ide=musCursor.getColumnIndex(MediaStore.Audio.Media._ID);
			int name=musCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
			int ti=musCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
			int alb=musCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
			int arti=musCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
			int albId=musCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
			//int albumid=musCursor.getColumnIndex(MediaStore.Audio.Media._ID);
			//int albumName=artCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
			String filename="";
			long x=0;
			if (!number){
				
				filename=path.substring(path.lastIndexOf("/")+1);
				//
			}
			else{
				
				x=Long.valueOf(paths.substring(paths.lastIndexOf("/")+1));
				
			}
			
			
			boolean found=false;
			//if (musCursor.moveToFirst())
			do
			{
				if (!number)
				{
					if (musCursor.getString(name).equals(filename))
						found=true;
					//Toast.makeText(MainActivity.this, String.valueOf(musCursor.getLong(ide)), Toast.LENGTH_LONG).show();
				}
				else
				{
					if (musCursor.getLong(ide)==x)
						found=true;
					//Toast.makeText(MainActivity.this, String.valueOf(found), Toast.LENGTH_LONG).show();
					//Toast.makeText(MainActivity.this, String.valueOf(musCursor.getString(name)), Toast.LENGTH_LONG).show();
				}
				if (found)
					{
						if (musCursor.getString(ti)==null)
							title.setText("Unknown Title");
						else
							title.setText(musCursor.getString(ti));
						if (musCursor.getString(alb)==null)
							album.setText("Unknown Album");
						else
							album.setText(musCursor.getString(alb));
						if (musCursor.getString(arti)==null)
							artist.setText("Unknown Artist");
						else
							artist.setText(musCursor.getString(arti));
						id=musCursor.getLong(albId);
						found=false;
						break;
					}
			}while (musCursor.moveToNext());
			
			musCursor.close();
		}
			
			Uri artwork=MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
			Cursor artCursor=contentResolver.query(artwork, null, null, null, null);
			Bitmap bitmap=BitmapFactory.decodeResource(com.example.musicplayer.MainActivity.this.getResources(), R.drawable.album_neon);
			View view=this.getWindow().getDecorView();
			RelativeLayout relative=(RelativeLayout)findViewById(R.id.RelativeLayout);
			String imagePath="";
			if (artCursor!=null && artCursor.moveToFirst()){
	
					int albumArt=artCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
					int albumName=artCursor.getColumnIndex(MediaStore.Audio.Albums._ID);
					//artCursor.moveToFirst();
					//Toast.makeText(this, id, Toast.LENGTH_LONG).show();
					h=artCursor.getColumnNames();
					//for (int i=0;i<h.length;i++)
					//Toast.makeText(this, h[i], Toast.LENGTH_LONG).show();
					do{
						//Toast.makeText(this, artCursor.getString(albumName), Toast.LENGTH_LONG).show();
					if (artCursor.getLong(albumName)==id){	
						if (artCursor.getString(albumArt)!=null){
							bitmap=BitmapFactory.decodeFile(artCursor.getString(albumArt));
							imagePath= artCursor.getString(albumArt);
						}	
					}
				}
				while (artCursor.moveToNext());
			art.setImageBitmap(bitmap);
		//	relative.setBackgroundResource(R.drawable.album_neon);
			if (imagePath.length()>0)
				view.setBackgroundDrawable(BitmapDrawable.createFromPath(imagePath));
				//Toast.makeText(this, String.valueOf(imagePath.length()), Toast.LENGTH_LONG).show();
			else
				relative.setBackgroundColor(Color.BLACK);
				//Toast.makeText(this, String.valueOf(imagePath.length()), Toast.LENGTH_LONG).show();
		}
		
		artCursor.close();

	}
	
	void initFolderTree(final String initialPath)
	{
		File x=new File(initialPath);
		if (x.canRead()){
			List<String> dirs=Arrays.asList(Objects.requireNonNull(x.list()));
			if (dirs.size()!=0){
				//folderTree.add(initialPath);
				//Toast.makeText(MainActivity.this, "here", Toast.LENGTH_SHORT).show();
			//}
			//else{
				Collections.sort(dirs,new Comparator<String>() {
					public int compare(String a,String b)
					{
						if (!new File(initialPath+"/"+a).isDirectory() && new File(initialPath+"/"+b).isDirectory())
						    return 1;
						else if (new File(initialPath+"/"+a).isDirectory() && !new File(initialPath+"/"+b).isDirectory())
						    return -1;
						else
						    return a.compareTo(b);
					}
				});
				int i=0;
				while (i<dirs.size())
				{
					if (!new File(initialPath+"/"+dirs.get(i)).isDirectory())
						break;
					initFolderTree(initialPath+"/"+dirs.get(i));
					i++;
				}
				folderTree.add(initialPath);
			}
		}
	}
	
	private List<String> listAndSort(final String p)
	{
		File x=new File(p);
		List<String> dirs=Arrays.asList(x.list());
		Collections.sort(dirs,new Comparator<String>() {
			public int compare(String a,String b)
			{
				if (!new File(p+"/"+a).isDirectory() && new File(p+"/"+b).isDirectory())
				    return 1;
				else if (new File(p+"/"+a).isDirectory() && !new File(p+"/"+b).isDirectory())
				    return -1;
				else
				    return a.compareTo(b);
			}
		});
		return dirs;
	}
	
	private void removingVoids() {
		for (int i = 0; i < folderTree.size(); i++) {
			List<String>x=Arrays.asList(new File(folderTree.get(i)).list());
			for (int j=0;j<x.size();j++)
				x.set(j, folderTree.get(i)+"/"+x.get(j));
			if (folderTree.containsAll(x))
			{
				folderTree.remove(i);
			}
		}
	}
	
	private void pathCounter(String folderPath) {
		ContentResolver contentResolver=getContentResolver();
		Uri musUri=Uri.fromFile(new File(folderPath));
		Toast.makeText(getBaseContext(), musUri.toString(), Toast.LENGTH_LONG).show();
		Cursor c=contentResolver.query(musUri, null, null, null, null);
		
		if (c.moveToFirst())
		{
			int musFiles=c.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);
			while(c.moveToNext())
			{
				if (c.getInt(musFiles)!=0)
					trackCounter++;
			}
		}
		tracks.setText(String.valueOf(trackCounter));
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.tVTitle:
			String temp=title.getText().toString();
			title.setText("");
			title.setHint("Type the new name");
			if (!title.getText().toString().equals("")){
				Cursor c=getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
				if (c!=null && c.moveToFirst())
				{
					int ti=c.getColumnIndex(MediaStore.Audio.Media.TITLE);
					int alb=c.getColumnIndex(MediaStore.Audio.Media.ALBUM);
					int ar=c.getColumnIndex(MediaStore.Audio.Media.ARTIST);
					do{
						if (temp.equals(c.getString(ti)) && album.getText().toString().equals(c.getString(alb))
								&& artist.getText().toString().equals(c.getString(ar)))
						{
							//getContentResolver().update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values, where, selectionArgs)
						}
					}while(c.moveToNext());
				}
			}
			break;
		case R.id.tVAlbum:
			break;
		case R.id.tVArtist:
			break;
		}
		return false;
	}

//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		finish();
//	}

	public static com.example.musicplayer.MainActivity getInstance(){
		return mainActivity;
	}
}
