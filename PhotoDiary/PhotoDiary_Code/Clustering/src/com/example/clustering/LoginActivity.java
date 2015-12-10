package com.example.clustering;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	DatabaseHelper db = new DatabaseHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getActionBar().hide();

		Button btn_login = (Button) findViewById(R.id.btn_login_login);
		Button btn_cancel = (Button) findViewById(R.id.btn_login_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent goToStartup = new Intent(LoginActivity.this, StartupActivity.class);
				startActivity(goToStartup);
			}
		});
		btn_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText et_username = (EditText) findViewById(R.id.editText_login_username);
				EditText et_password = (EditText) findViewById(R.id.editText_login_password);
				TextView warning = (TextView) findViewById(R.id.textView_login_warning);
				String username = et_username.getText().toString();
				String password = et_password.getText().toString();
				if (username.length() >= 6) {
					List<String> usernames = db.getAllItems();
					if (usernames.contains(username)) {
						User user = db.readUser(username);
						String userPassword = user.getPassword();
						if (password.equals(userPassword)) {
							Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);

							goToMain.putExtra("username", user.getUsername());

							startActivity(goToMain);
						} else {
							warning.setText("Incorrect password!");
						}
					} else {
						warning.setText("Username does note exists!");
					}

				} else {
					warning.setText("The length of username should be >= 6!");
				}
			}
		});
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