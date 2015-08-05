package com.kamrul.gps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Mail_info extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mail_info);
		
		String mi = "Here you have to put your gmail account name and password. " +
				"Don't afraid, these data will not be sent outside, it will be only used to " +
				"send email to your desired address. Only Google Gmail account is acceptable for user," +
				" but Friend Email address can be any, like yahoo,live,hotmail etc. " +
				"Mail will be used to send large data, like contact numbers, callLog, Messages etc."
				+"\nYour Internet Data Connection Should be Tunred ON";
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(mi);
		
	}

}
