package com.example.photodiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BoolRes;
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
				User user = db.readUser(uname);
				Toast.makeText(getApplicationContext(), " resuklt",
						   Toast.LENGTH_LONG).show();
				/*
				if (pwd.equals(re_pwd) && user.getUsername().equals("")){
					Toast.makeText(getApplicationContext(),uname+ "\n" + pwd + "\n"+ re_pwd +"\n"+ email,
						   Toast.LENGTH_LONG).show();
				}
				*/
				//Intent goToStartup = new Intent(RegisterActivity.this,StartupActivity.class);
				//startActivity(goToStartup);	
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
