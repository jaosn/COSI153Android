package com.example.photodiary;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	DatabaseHelper db = new DatabaseHelper(this);
	Boolean status = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		Button btn_register = (Button) findViewById(R.id.btn_register_register);
		Button btn_cancel = (Button) findViewById(R.id.btn_register_cancel);
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent goToStartup = new Intent(RegisterActivity.this,StartupActivity.class);
				startActivity(goToStartup);
			}
		});
		btn_register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText et_username = (EditText)findViewById(R.id.editText_register_username);
				EditText et_password = (EditText)findViewById(R.id.editText_register_password);
				EditText et_re_password = (EditText)findViewById(R.id.editText_register_re_password);
				EditText et_email = (EditText)findViewById(R.id.editText_register_email);
				
				TextView warning = (TextView)findViewById(R.id.textView_register_warning);
				
				String uname = et_username.getText().toString();
				String pwd = et_password.getText().toString();
				String re_pwd = et_re_password.getText().toString();
				String email = et_email.getText().toString();
				
				User userToSave = new User(uname,pwd,email);
				
				if (pwd.equals(re_pwd)){
					if (uname.length() >= 6){
						List<String> usernames = db.getAllItems();
						if (!usernames.contains(uname)){
							db.createUser(userToSave);
							Intent goToStartup = new Intent(RegisterActivity.this,StartupActivity.class);
							startActivity(goToStartup);
						}
						else
							warning.setText("Username already exists!");
					}
					else
						warning.setText("Lenght of username should be >= 6!");
				}
				else{
					warning.setText("Please re-enter you password!");
				}
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
