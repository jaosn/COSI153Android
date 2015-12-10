package com.example.clustering;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import android.R.color;
import android.content.Context;
import android.gesture.GestureLibrary;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageInfoWindowAdapter implements InfoWindowAdapter {

	private Context context;
	private LayoutInflater inflater;
	private View view;
	DatabaseHelper db;
	private MyItem clickeditem;

	// private DB db;
	public ImageInfoWindowAdapter(Context context, MyItem clickeditem) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(this.context);
		db = new DatabaseHelper(context);
		this.clickeditem = clickeditem;
		// TODO Auto-generated constructor stub
	}

	public void setClickedItem(MyItem clickeditem) {
		this.clickeditem = clickeditem;
	}

	@Override
	public View getInfoContents(Marker arg0) {

		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.infowindow, null);
		ImageView iv = (ImageView) view.findViewById(R.id.infowindow_imageview);

		Bitmap bitmap = BitmapFactory.decodeFile(clickeditem.getUri());

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		int width, height, dw, dh;
		double wh, temp1, temp2;
		temp1 = bitmap.getWidth();
		temp2 = bitmap.getHeight();
		wh = temp1 / temp2;
		dw = display.getWidth();
		dh = display.getHeight();

		if (wh >= 1) {
			width = dw / 2;
			height = (int) (width / wh);
		} else {
			height = dh / 3;
			width = (int) (wh * height);
		}

		Drawable d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));
		iv.setImageDrawable(d);

		return view;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		return null;
	}

}
