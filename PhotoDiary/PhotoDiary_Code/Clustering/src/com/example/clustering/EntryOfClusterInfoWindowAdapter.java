package com.example.clustering;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class EntryOfClusterInfoWindowAdapter extends BaseAdapter {

	private ArrayList<String> allclusterpaths;
	private LayoutInflater inflater;
	private Context context;

	public EntryOfClusterInfoWindowAdapter(Context context, ArrayList<String> allclusterpaths) {
		this.allclusterpaths = allclusterpaths;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.allclusterpaths.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return allclusterpaths.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.entryofclusterinfowindow, parent, false);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.entryofclusterinfowindow_imageview);

		String path = getItem(position);

		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path), 64, 64);

		iv.setImageBitmap(ThumbImage);
		return convertView;
	}
}
