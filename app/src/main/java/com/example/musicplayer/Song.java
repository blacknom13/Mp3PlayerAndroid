package com.example.musicplayer;

import android.graphics.Bitmap;

/*
 * This is demo code to accompany the Mobiletuts+ series:
 * Android SDK: Creating a Music Player
 * 
 * Sue Smith - February 2014
 */

public class Song {
	
	private long id;
	private String title;
	private String album;
	private String artist;
	private Bitmap art;
	
	public Song(long songID, String songTitle, String songAlbum, String songArtist,Bitmap albumArt){
		id=songID;
		title=songTitle;
		album=songAlbum;
		artist=songArtist;
		art=albumArt;
	}
	
	public long getID(){return id;}
	public String getTitle(){return title;}
	public String getArtist(){return artist;}
	public String getAlbum(){return album;}
	public Bitmap getArt(){return art;}
}
