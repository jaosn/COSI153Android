package com.example.clustering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
	private String USER_NAME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);

		Intent intentGet = getIntent();
		USER_NAME = intentGet.getStringExtra("username");

		Button btn_goback = (Button) findViewById(R.id.btn_aboutUs);
		btn_goback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent goToMain = new Intent(AboutUsActivity.this, MainActivity.class);
				goToMain.putExtra("username", USER_NAME);
				startActivity(goToMain);
			}
		});

		TextView thanks = (TextView) findViewById(R.id.textView_aboutUs_user);
		String words = "Hi, " + USER_NAME + "\n" + "Thank you so much for using this APP.";
		thanks.setText(words);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_us, menu);
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
}
