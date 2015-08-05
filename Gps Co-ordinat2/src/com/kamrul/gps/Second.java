package com.kamrul.gps;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Second extends Activity {
	

	private EditText et1;
	private EditText et2;
	private EditText et3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);

		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//getSharedPreferences("MYPREFS", 0);
		et2.setText(settings.getString("nvalue", ""));
		et3.setText(settings.getString("fvalue", ""));
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);
		Button b4 = (Button) findViewById(R.id.button4);
		Button b5 = (Button) findViewById(R.id.button5);
		
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(et1.getText().toString().length() != 0){
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("tvalue", et1.getText().toString());
				editor.commit();
				Toast.makeText(Second.this, "Done", Toast.LENGTH_SHORT).show();
				
			}
							
				
			}
		});
		
		
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(et2.getText().toString().length() != 0){
					
				SharedPreferences.Editor editor = settings.edit();
				
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				String siminfo = "";
				siminfo = tm.getSimSerialNumber()+tm.getNetworkOperator()+tm.getNetworkCountryIso();
				
				editor.putString("nvalue", et2.getText().toString());
				editor.putString("svalue", siminfo);
				
				editor.commit();
				Toast.makeText(Second.this, "Done", Toast.LENGTH_SHORT).show();
				
				
				
			}
				else
				{
					SharedPreferences.Editor editor = settings.edit();
					if(settings.contains("nvalue")){
					editor.remove("nvalue");				
					editor.commit();}
					Toast.makeText(Second.this, "Done", Toast.LENGTH_SHORT).show();
					
					
				}	
			}
		});
		
		
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(et3.getText().toString().length() != 0){
					
					File dir = new File("/" + Environment.getExternalStorageDirectory().getPath() + "/" + et3.getText().toString());
					
					
					if(dir.exists())
					{
						
					
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("fvalue", et3.getText().toString());
										
					editor.commit();
					Toast.makeText(Second.this, "Successfully Set", Toast.LENGTH_SHORT).show();
				
				}
					else
					{
						
						SharedPreferences.Editor editor = settings.edit();
						if(settings.contains("fvalue")){
						editor.remove("fvalue");				
						editor.commit();}
						Toast.makeText(Second.this, "Error! Path does not exists!", Toast.LENGTH_LONG).show();
					}
				
				}
				else
				{
					SharedPreferences.Editor editor = settings.edit();
					if(settings.contains("fvalue")){
					editor.remove("fvalue");				
					editor.commit();}
					Toast.makeText(Second.this, "Error! Path does not exists!", Toast.LENGTH_LONG).show();
				}
				
				
			}
		});
		
		
		b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent dii = new Intent(Second.this, Delinfo.class);
				startActivity(dii);
				
			}
		});
		
b5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent dii2 = new Intent(Second.this, Mail_l.class);
				startActivity(dii2);
				
			}
		});
		
	}
	
	public void startService(View view) {

		startService(new Intent(getBaseContext(), Mserv.class));
	}

	public void stopService(View view) {
		
		stopService(new Intent(getBaseContext(), Mserv.class));
	}

	
}
