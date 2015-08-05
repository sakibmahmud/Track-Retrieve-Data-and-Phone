package com.kamrul.gps;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class StartupBroadcastReceiver extends BroadcastReceiver {

 @Override
 public void onReceive(Context context, Intent intent) {
	 
	 TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	 
	 String sid = tm.getSimSerialNumber() + tm.getNetworkOperator() + tm.getNetworkCountryIso();
	 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
	 
	 if(settings.contains("svalue")){
		 
		 
		 if(!sid.contentEquals(settings.getString("svalue", ""))){
			 
			 String friend = settings.getString("nvalue", "no");
			 String message = 
			    " Your Sim Card was Replaced!" +
				//" May be your phone is lost or stolen!" +
			 	//" This message was sent from the new sim card. Please note the mobile number from" +
			 	//"where this message came to" +
			 	//"send message command to control your phone. " +
			 	" Write \"loc\" for Location. " +
				"\"cnt\" for Contact numbers. " +
				"\"cl\" for CallLog " +
				"\"ml\" for messages" +
				"\"dd\" to delete data.";
			 	
			 	SmsManager sms = SmsManager.getDefault();
			 	
			 	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 	
				sms.sendTextMessage(friend, null, message, null, null); 
			 
			 
		 }
		 
		 
	 }
	 


//	 Intent startupIntent = new Intent(context, Main.class);
//	 startupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	 context.startActivity(startupIntent);
 
 }

}
