package com.example.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class StartUp extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		final boolean first=true;//PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("firstTime", true);
		if (first){
		setContentView(R.layout.start_up);
		
		Thread st=new Thread(new Runnable() {
			public void run() {
				try {
					//Toast.makeText(getBaseContext(), String.valueOf(first), Toast.LENGTH_LONG).show();
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					Intent i=new Intent("com.example.fig.ADVANCEDFILEMANAGER");
					//Intent i=new Intent("com.example.fig.FILEMANAGER");
					startActivity(i);
				}
			}
		});
		st.start();}
//		else{
//			Intent i=new Intent("com.example.fig.MAINACTIVITY");
//			startActivity(i);
//		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
}

