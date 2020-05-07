package com.example.musicplayer;

import android.graphics.Bitmap;

public class MyFile{
	
	private long id;
	private String fileName;
	private String size;
	private Bitmap icon;
	private boolean isDir;
	public MyFile(long fId,String name,String fileSize,Bitmap bm,boolean isD)
	{
		id=fId;
		fileName=name;
		size=fileSize;
		icon=bm;
		isDir=isD;
	}
	public long getId() {
		return id;
	}
	public String getFileName() {
		return fileName;
	}
	public String getSize() {
		return size;
	}
	public Bitmap getIcon() {
		return icon;
	}
	public boolean isDir() {
		return isDir;
	}
	
	
	

}
