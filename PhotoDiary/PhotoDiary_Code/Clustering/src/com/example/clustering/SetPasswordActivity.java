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
import android.widget.Toast;

public class SetPasswordActivity extends Activity {

	DatabaseHelper db = new DatabaseHelper(this);
	public String USER_NAME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setpassword);

		Intent intentGet = getIntent();
		USER_NAME = intentGet.getStringExtra("username");
		// Toast.makeText(getApplicationContext(), USER_NAME,
		// Toast.LENGTH_SHORT).show();

		Button btn_setpwd = (Button) findViewById(R.id.btn_setpwd_confirm);
		Button btn_cancel = (Button) findViewById(R.id.btn_setpwd_cancel);

		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent goToMain = new Intent(SetPasswordActivity.this, MainActivity.class);
				startActivity(goToMain);
			}
		});

		btn_setpwd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// EditText et_username =
				// (EditText)findViewById(R.id.editText_setpwd_username);
				EditText et_old_pwd = (EditText) findViewById(R.id.editText_setpwd_oldpassword);
				EditText et_new_pwd = (EditText) findViewById(R.id.editText_setpwd_newpassword);
				EditText et_confirm_pwd = (EditText) findViewById(R.id.editText_setpwd_re_newpassword);

				TextView warning = (TextView) findViewById(R.id.textView_setpwd_warning);

				// String username = et_username.getText().toString();
				String oldPwd = et_old_pwd.getText().toString();
				String newPwd = et_new_pwd.getText().toString();
				String confirmPwd = et_confirm_pwd.getText().toString();

				List<String> usernames = db.getAllItems();
				if (usernames.contains(USER_NAME)) {

					User user = db.readUser(USER_NAME);
					String userPassword = user.getPassword();
					// Toast.makeText(getApplicationContext(), userPassword,
					// Toast.LENGTH_SHORT).show();
					if (oldPwd.equals(userPassword)) {
						if (newPwd.equals(confirmPwd) && !newPwd.equals("")) {
							db.deleteUser(USER_NAME);
							user.setPassword(newPwd);
							db.createUser(user);
							// Toast.makeText(getApplicationContext(),
							// user.getPassword(), Toast.LENGTH_SHORT).show();
							// db.updateUser(user);
							Intent goToMain = new Intent(SetPasswordActivity.this, MainActivity.class);
							goToMain.putExtra("username", user.getUsername());
							startActivity(goToMain);

						} else {
							warning.setText("New Password doesn't match!");
						}
					} else {
						warning.setText("Incorrect password!");
					}
				} else {
					warning.setText("Username does note exists!");
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_password, menu);
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
