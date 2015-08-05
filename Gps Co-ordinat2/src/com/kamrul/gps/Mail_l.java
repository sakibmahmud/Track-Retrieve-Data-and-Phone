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
import android.widget.Toast;

public class Mail_l extends Activity {
	
	private EditText et1;
	private EditText et2;
	private EditText et3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mail_l);
		
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		
		et1.setText(settings.getString("uvalue", ""));
		et2.setText(settings.getString("pvalue", ""));
		et3.setText(settings.getString("fuvalue", ""));
		
b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(et1.getText().toString().length() != 0 && et2.getText().toString().length() != 0 && et3.getText().toString().length() != 0){
				
					SharedPreferences.Editor editor = settings.edit();
				editor.putString("uvalue", et1.getText().toString());
				editor.putString("pvalue", et2.getText().toString());
				editor.putString("fuvalue", et3.getText().toString());
				editor.commit();
				Toast.makeText(Mail_l.this, "Done", Toast.LENGTH_SHORT).show();
				
			}
			
				else
				{
					SharedPreferences.Editor editor = settings.edit();
					if(settings.contains("uvalue")){
					editor.remove("uvalue");
					editor.remove("pvalue");
					editor.remove("fuvalue");
					editor.commit();}
					Toast.makeText(Mail_l.this, "Done", Toast.LENGTH_SHORT).show();
				}
			
			}
		});
		
		
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent iii = new Intent(Mail_l.this, Mail_info.class);
				
				startActivity(iii);
				
				
			}
		});
		
		
		
	}


}