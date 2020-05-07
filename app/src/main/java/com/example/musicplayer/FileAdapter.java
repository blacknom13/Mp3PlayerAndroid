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

public class FileAdapter extends BaseAdapter{

	private ArrayList<MyFile> files;
	private LayoutInflater fileInf;
	
	public FileAdapter(Context c,ArrayList<MyFile> myFiles)
	{
		files=myFiles;
		fileInf=LayoutInflater.from(c);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return files.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout fileLay=(LinearLayout)fileInf.inflate(R.layout.file,parent,false);
		TextView fileName=(TextView)fileLay.findViewById(R.id.tVFileName);
		TextView fileSize=(TextView)fileLay.findViewById(R.id.tVFileSize);
		ImageView icon=(ImageView)fileLay.findViewById(R.id.iVFileIcon);
		MyFile selectedFile=files.get(position);
		
		fileName.setText(selectedFile.getFileName());
		fileSize.setText(selectedFile.getSize());
		icon.setImageBitmap(selectedFile.getIcon());
		
		fileLay.setTag(position);
		
		return fileLay;
	}

}
