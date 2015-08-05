package com.kamrul.gps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		
		String s = "1.\nFirst set your password to access tha app. Each time you open the " +
				"app, you need to give the password, otherwise you cannot set the \"friend " +
				"number\". If you forget your password, you have to reinstall the app.\n" +
				"2.\nThen you have to set a Friend number. From this number you have to send" +
				" message to this phone to control your phone.\n" +
				"3.\nThe method of sending SMS is like this, You goto your message option and\n\n" +
				
				"Write \"loc\" to get the Location by SMS\n" +
				"Write \"cnt\" to get the Contact numbers by SMS\n" +
				"Write \"cl\" to get the Calllog/Call History by SMS\n" +
				"Write \"ml\" to get the messages from inbox and sentbox by SMS\n" +
				"Write \"loce\" to get the Location by EMAIL\n" +
				"Write \"cnte\" to get the Contact numbers by EMAIL\n" +
				"Write \"cle\" to get the Calllog/Call History by EMAIL\n" +
				"Write \"mle\" to get the messages from inbox and sentbox by EMAIL\n" +
				"Write \"dd\" to delete the data selected by you.\n\n" +
				"Please dont put Quotation mark. It's just for emphasize.\n" +
				"These SMS have to send from the Friend number only. \n" +
				"Email will be sent to the \"Friend Email Account\" given by you.\n" +
				"The message you send will not be seen by the user." +
				" You will be sent back an SMS(or EMAIL) " +
				"from your lost mobile with your information." +
				" To get information by Email the Internet Data Connection Should be turned ON.\n" +
				
				"4.\nIn case of Location, you will be sent back an SMS" +
				" containing the Latitude and Longitude of the current place of your " +
				"mobile. This may take several minutes depending upon the GPS sensor. " +
				"Once you get the Latitude and Longitude, you login to www.maps.google.com. " +
				"There, in the search box type the Latitude and Longitude, seperated by a space." +
				"Then click search to see the location of your mobile. Use the sattelite view" +
				" to see the real photo of the place." +
				" Your phone must have GPS sensor to get Location.\n\n" +
				
				"5.\nYou will be notified by SMS if your current sim card is replaced/changed\n\n"
				+
				"Thank You!" +
				"\n\nMade by\nSakib Mahmud\n" +
				"Chittagong University of Engineering & Technology\nBangladesh\n" +
				
				
		
				
		tv.setText(s);
	}

}
