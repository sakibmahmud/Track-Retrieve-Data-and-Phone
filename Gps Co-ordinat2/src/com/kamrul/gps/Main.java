
package com.kamrul.gps;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Main extends Activity {
	

	
	private EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		et = (EditText) findViewById(R.id.editText1);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//getSharedPreferences("MYPREFS", 0);
		TextView tv = (TextView) findViewById(R.id.textView1);
		
		
		if(settings.contains("tvalue")){
			
			tv.setText("Enter Your Password");
		}
		
		else
		{
			tv.setText("Set New Password");
		}
		
	
		Button b = (Button) findViewById(R.id.button1);
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String s = et.getText().toString();
				
				SharedPreferences settings  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//getSharedPreferences("MYPREFS", 0);
				
				if(settings.contains("tvalue")){
				
					if(settings.getString("tvalue", "nothing").contentEquals(s))
					{
					
					Intent intent = new Intent(Main.this, Second.class);
					
					startActivity(intent);
					
					finish();
					
					}
					else
					{
						Toast.makeText(Main.this, "Wrong Password", Toast.LENGTH_LONG).show();
					}
				}
				
				else
				{
					if(et.getText().toString().length() != 0){
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("tvalue", et.getText().toString());
					editor.commit();
					Toast.makeText(Main.this, "Password Successfully Set", Toast.LENGTH_LONG).show();
				
					Intent intent = new Intent(Main.this, Second.class);
					
					startActivity(intent);
					
					finish();
				}}
				
			}
		});
		
		Button b2 = (Button) findViewById(R.id.button2);
		
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent2 = new Intent(Main.this, Info.class);
				startActivity(intent2);
				
			}
		});
				
	}
	
	
}