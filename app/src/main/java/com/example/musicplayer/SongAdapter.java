package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * This is demo code to accompany the Mobiletuts+ series:
 * Android SDK: Creating a Music Player
 * 
 * Sue Smith - February 2014
 */

public class SongAdapter extends BaseAdapter {
	
	//song list and layout
	private ArrayList<com.example.musicplayer.Song> songs;
	private LayoutInflater songInf;
	
	//constructor
	public SongAdapter(Context c, ArrayList<com.example.musicplayer.Song> theSongs){
		songs=theSongs;
		songInf=LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		return songs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return songs.get(arg0).getID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//map to song layout
		LinearLayout songLay = (LinearLayout)songInf.inflate
				(R.layout.item_look, parent, false);
		//get title and artist views
		TextView songView = (TextView)songLay.findViewById(R.id.tVSongName);
		TextView albumView= (TextView)songLay.findViewById(R.id.tVSongAlbum);
		TextView artistView = (TextView)songLay.findViewById(R.id.tVSongArtist);
		ImageView albumArt= (ImageView)songLay.findViewById(R.id.iVAlbumArt);
		//get song using position
		com.example.musicplayer.Song currSong = songs.get(position);
		//get title and artist strings
		songView.setText(currSong.getTitle());
		artistView.setText(currSong.getArtist());
		albumView.setText(currSong.getAlbum());
		albumArt.setImageBitmap(currSong.getArt());
		//set position as tag
		songLay.setTag(position);
		return songLay;
	}

	
}
