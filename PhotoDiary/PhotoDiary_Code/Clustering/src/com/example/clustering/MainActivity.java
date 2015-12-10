package com.example.clustering;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.AllPermission;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.*;
//import com.google.android.gms.maps.model.*;

public class MainActivity extends FragmentActivity {

	private GoogleMap googleMap;

	private ListView DrawerList;
	private ArrayAdapter<String> DrawerAdapter;

	private ImageInfoWindowAdapter IIWA;

	private EntryOfClusterInfoWindowAdapter EOCIWA;

	DatabaseHelper db = new DatabaseHelper(this);

	private Context context;

	private ClusterManager<MyItem> mClusterManager;

	private MyItem clickedClusterItem;
	private Cluster<MyItem> clickedCluster;

	private Dialog dialog;
	private ImageButton Sync;
	public String USER_NAME;

	private ImageButton nextclusterimage, previousclusterimage, clusterdialogclose;

	private int currentIndex = 0;

	private boolean zoomswitcher = true;
	private boolean zoomswitcher2 = true;

	private TextView clusterdialogtitle;

	private final double degreesPerRadian = 180.0 / Math.PI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent intentGet = getIntent();
		USER_NAME = intentGet.getStringExtra("username");

		context = this;
		DrawerList = (ListView) findViewById(R.id.navList);

		DrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

				switch (position) {
				case 0:
					// Statements
					Intent togosetting = new Intent(context, SetPasswordActivity.class);
					togosetting.putExtra("username", USER_NAME);
					startActivity(togosetting);
					break; // optional
				case 1:
					// Statements
					addALLLines();
					break; // optional
				case 2:

					googleMap.clear();

					LatLng current = googleMap.getCameraPosition().target;
					float currentzoom = googleMap.getCameraPosition().zoom;
					if (zoomswitcher2) {
						currentzoom = (float) (currentzoom - 0.01);
						zoomswitcher2 = false;
					} else {
						currentzoom = (float) (currentzoom + 0.01);
						zoomswitcher2 = true;
					}
					googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, currentzoom));
					// Statements
					break; // optional
				case 3:
					// Statements
					break; // optional
				case 4:
					// Statements

					Intent togoaboutus = new Intent(context, AboutUsActivity.class);
					togoaboutus.putExtra("username", USER_NAME);
					startActivity(togoaboutus);
					break; // optional
				// You can have any number of case statements.
				default: // Optional
					// Statements
				}

			}
		});

		Sync = (ImageButton) findViewById(R.id.main_sync);
		Sync.getBackground().setAlpha(255);
		Sync.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(context, "test", Toast.LENGTH_LONG).show();
				// googleMap.clear();
				db.onUpgrade(db.getWritableDatabase(), 1, 2);
				addDrawerItems();
				getAllPicPath();

				mClusterManager.clearItems();
				LatLng current = googleMap.getCameraPosition().target;
				float currentzoom = googleMap.getCameraPosition().zoom;
				if (zoomswitcher) {
					currentzoom = (float) (currentzoom - 0.01);
					zoomswitcher = false;
				} else {
					currentzoom = (float) (currentzoom + 0.01);
					zoomswitcher = true;
				}

				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, currentzoom));
				addItems();
			}
		});

		addDrawerItems();
		getAllPicPath();

		IIWA = new ImageInfoWindowAdapter(this, clickedClusterItem);

		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * function to load map. If map is not created it will create it for you
	 */
	private void initilizeMap() {

		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
			}

			mClusterManager = new ClusterManager<MyItem>(this, googleMap);

			googleMap.setMyLocationEnabled(true);
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

			// Point the map's listeners at the listeners implemented by the

			googleMap.setOnCameraChangeListener(mClusterManager);
			googleMap.setOnMarkerClickListener(mClusterManager);

			googleMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

			mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
				@Override
				public boolean onClusterItemClick(MyItem item) {
					clickedClusterItem = item;
					IIWA.setClickedItem(clickedClusterItem);
					return false;
				}
			});

			mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {

				@Override
				public boolean onClusterClick(Cluster<MyItem> cluster) {
					// TODO Auto-generated method stub
					clickedCluster = cluster;

					dialog = new Dialog(context);

					dialog.setContentView(R.layout.clusterimagedialog);
					dialog.setTitle(cluster.getSize() + " pictures in this area:");

					final ImageSwitcher imageSwitcher = (ImageSwitcher) dialog
							.findViewById(R.id.clusterimagediaog_imageSwitcher);
					nextclusterimage = (ImageButton) dialog.findViewById(R.id.clusterimagediaog_next);
					previousclusterimage = (ImageButton) dialog.findViewById(R.id.clusterimagediaog_previous);
					clusterdialogclose = (ImageButton) dialog.findViewById(R.id.clusterimagediaog_close);
					clusterdialogtitle = (TextView) dialog.findViewById(R.id.clusterimagediaog_title);

					Collection<MyItem> clusterItemstemp = clickedCluster.getItems();
					ArrayList<MyItem> clusterItems = new ArrayList<MyItem>(clusterItemstemp);

					final ArrayList<String> clusterPaths = new ArrayList<String>();
					for (MyItem i : clusterItems) {
						clusterPaths.add(i.getUri());
					}

					currentIndex = 0;
					final int messageCount = clusterPaths.size();

					imageSwitcher.setFactory(new ViewFactory() {

						public View makeView() {
							// TODO Auto-generated method stub

							// Create a new ImageView set it's properties
							ImageView imageView = new ImageView(getApplicationContext());
							imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
							imageView.setLayoutParams(
									new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
							return imageView;
						}
					});

					// Declare the animations and initialize them
					Animation in = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
					Animation out = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);

					// set the animation type to imageSwitcher
					imageSwitcher.setInAnimation(in);
					imageSwitcher.setOutAnimation(out);

					clusterdialogtitle.setText("Image " + (currentIndex + 1));

					Bitmap bitmap = BitmapFactory.decodeFile(clusterPaths.get(currentIndex));
					int[] wh = getWidthHeight(bitmap);

					Drawable d = new BitmapDrawable(context.getResources(),
							Bitmap.createScaledBitmap(bitmap, wh[0], wh[1], true));

					imageSwitcher.setImageDrawable(d);

					nextclusterimage.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {
							// TODO Auto-generated method stub
							currentIndex++;
							// If index reaches maximum reset it
							if (currentIndex == messageCount)
								currentIndex = 0;
							clusterdialogtitle.setText("Image " + (currentIndex + 1));
							Bitmap bitmap = BitmapFactory.decodeFile(clusterPaths.get(currentIndex));
							int[] wh = getWidthHeight(bitmap);
							Drawable d = new BitmapDrawable(context.getResources(),
									Bitmap.createScaledBitmap(bitmap, wh[0], wh[1], true));

							imageSwitcher.setImageDrawable(d);
						}
					});

					previousclusterimage.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							currentIndex--;
							// If index reaches maximum reset it
							if (currentIndex == -1)
								currentIndex = messageCount - 1;
							clusterdialogtitle.setText("Image " + (currentIndex + 1));
							Bitmap bitmap = BitmapFactory.decodeFile(clusterPaths.get(currentIndex));
							int[] wh = getWidthHeight(bitmap);
							Drawable d = new BitmapDrawable(context.getResources(),
									Bitmap.createScaledBitmap(bitmap, wh[0], wh[1], true));

							imageSwitcher.setImageDrawable(d);
						}
					});

					clusterdialogclose.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					dialog.show();
					return false;
				}
			});

			mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(IIWA);

			addItems();

			// Add cluster items (markers) to the cluster manager.

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void addLines(LatLng temp1, LatLng temp2, int width) {

		googleMap.addPolyline((new PolylineOptions()).add(temp1, temp2).width(5).color(Color.YELLOW).geodesic(true));

	}

	private void addALLLines() {
		ArrayList<DiaryPhoto> allphotos = db.getAllPhotos();
		int size = allphotos.size();

		ArrayList<LatLng> allLatLng = new ArrayList<LatLng>();
		for (DiaryPhoto i : allphotos) {
			double lat = i.getLatitude();
			double lng = i.getLongitude();
			LatLng temp = new LatLng(lat, lng);
			allLatLng.add(temp);
		}

		for (int i = 0; i < size - 1; i++) {

			addLines(allLatLng.get(i), allLatLng.get(i + 1), i + 1);

		}

	}

	private void addDrawerItems() {
		String[] DrawerItems = { "User", "Footprint", "Hide Footprint", "Settings", "About" };
		DrawerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DrawerItems);
		DrawerList.setAdapter(DrawerAdapter);
	}

	private void getAllPicPath() {
		File sdCard = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");

		File[] pics = sdCard.listFiles();

		for (File f : pics) {

			try {
				float[] output = new float[2];
				ExifInterface x = new ExifInterface(f.getAbsolutePath());
				String date = x.getAttribute(ExifInterface.TAG_DATETIME);
				boolean flag = x.getLatLong(output);
				String title = f.getName();

				if (flag) {

					DiaryPhoto dp = new DiaryPhoto(title, output[1], output[0], date, f.getAbsolutePath(), 0);
					db.createPhoto(dp);
				}
			}

			catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void addItems() {
		ArrayList<DiaryPhoto> allphotos = db.getAllPhotos();
		int size = allphotos.size();
		for (int i = 0; i < size; i++) {
			MyItem marker = new MyItem(allphotos.get(i).getLatitude(), allphotos.get(i).getLongitude(),
					allphotos.get(i).getUri(), allphotos.get(i).getTime());

			mClusterManager.addItem(marker);
		}
	}

	private int[] getWidthHeight(Bitmap bitmap) {

		int[] widthHeight = new int[2];

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
			width = (int) (dw / 1.5);
			height = (int) (width / wh);
		} else {
			height = (int) (dh / 2.5);
			width = (int) (height * wh);
		}

		widthHeight[0] = width;
		widthHeight[1] = height;

		return widthHeight;

	}

}
