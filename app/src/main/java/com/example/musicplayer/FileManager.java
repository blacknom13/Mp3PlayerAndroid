package com.example.musicplayer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileManager extends ListActivity {

	  private String path;
	  ArrayAdapter adapter;
	  TextView title;
	  Button back;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.file_manager);
	    back=(Button)findViewById(R.id.bBack);
	    title=(TextView)findViewById(R.id.tVTitle);

	    // Use the current directory as title
	   // path=getIntent().getExtras().getString("set");
	   // if (path.equals(""))
	   path =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
	    refreshList(path);	    
	    back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String filename="/";
				if (!path.equals("/") && path.lastIndexOf("/")!=0)
				{
					path=path.substring(0, path.lastIndexOf("/"));
				}
				else
					path="/";
				//Toast.makeText(FileManager.this, path, 5000).show();
				refreshList(path);
			}
		});
	  }

	  @Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    String filename = (String) getListAdapter().getItem(position);
	    if (path.endsWith(File.separator)) {
	      filename = path + filename;
	    } else {
	      filename = path + File.separator + filename;
	    }
	    if (new File(filename).isDirectory()) {
	    	path=filename;
	      refreshList(filename);
	    } else {
	    	//if (android.provider.MediaStore.Audio.Media.IS_MUSIC.equals(filename)){
	    	//Toast.makeText(this, filename,5000).show();
	    	Intent i=new Intent();
	    	Bundle b=new Bundle();
	    	b.putString("back", filename);
	    	i.putExtras(b);
	    	setResult(RESULT_OK, i);
	    	finish();
	    	//}
	    	//else
	    	//	Toast.makeText(this, "Not a music file!", 5000).show();
	    }
	  }
	  
	  private void refreshList(final String path)
	  {
		    title.setText(path);

		    // Read all files sorted into the values-array
		    //android:textColor="#FFEBDA01"
		    List<String> values = new ArrayList<String>();
		    File dir = new File(path);
		    if (!dir.canRead()) {
		      setTitle(getTitle() + " (inaccessible)");
		    }
		    String[] list = dir.list();
		    if (list != null) {
		      for (String file : list) {
		        if (!file.startsWith(".")) 
		          values.add(file);
		        
		      }
		    }
		   // Collections.sort(values);
		    Collections.sort(values, new Comparator<String>() {
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

		    // Put the data into the list
		    if (adapter!=null)
		    	adapter.clear();
		    adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_activated_1, android.R.id.text1, values);
		    setListAdapter(adapter);
	  }
	}