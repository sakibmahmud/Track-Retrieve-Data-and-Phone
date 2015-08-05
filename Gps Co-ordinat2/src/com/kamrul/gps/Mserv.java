package com.kamrul.gps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class Mserv extends Service {
	
	
	int bv = Build.VERSION.SDK_INT;

	boolean turnOnDataConnection(boolean ON,Context context)
	 {

	     try{
	      if(bv == Build.VERSION_CODES.FROYO)

	         {
	          Method dataConnSwitchmethod;
	            Class<?> telephonyManagerClass;
	            Object ITelephonyStub;
	            Class<?> ITelephonyClass;

	            TelephonyManager telephonyManager = (TelephonyManager) context
	                    .getSystemService(Context.TELEPHONY_SERVICE);

	            telephonyManagerClass = Class.forName(telephonyManager.getClass().getName());
	            Method getITelephonyMethod = telephonyManagerClass.getDeclaredMethod("getITelephony");
	            getITelephonyMethod.setAccessible(true);
	            ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
	            ITelephonyClass = Class.forName(ITelephonyStub.getClass().getName());

	            if (ON) {
	                dataConnSwitchmethod = ITelephonyClass
	                        .getDeclaredMethod("enableDataConnectivity");
	            } else {
	                dataConnSwitchmethod = ITelephonyClass
	                        .getDeclaredMethod("disableDataConnectivity");   
	            }
	            dataConnSwitchmethod.setAccessible(true);
	            dataConnSwitchmethod.invoke(ITelephonyStub);

	           }
	         else
	           {
	             //log.i("App running on Ginger bread+");
	        	 final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        	   final Class<?> conmanClass = Class.forName(conman.getClass().getName());
	        	   final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
	        	   iConnectivityManagerField.setAccessible(true);
	        	   final Object iConnectivityManager = iConnectivityManagerField.get(conman);
	        	   final Class<?> iConnectivityManagerClass =  Class.forName(iConnectivityManager.getClass().getName());
	        	   final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	        	   setMobileDataEnabledMethod.setAccessible(true);

	        	   setMobileDataEnabledMethod.invoke(iConnectivityManager, ON);

	           }


	         return true;
	     }catch(Exception e){
	                       // Log.e(TAG,"error turning on/off data");

	                        return false;
	                        }

	 }

	
	String SENT = "SMS_SENT";
	String DELIVERED = "SMS_DELIVERED";
	PendingIntent sentPI, deliveredPI;
	BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
	IntentFilter intentFilter;
	
	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
		
	@Override
	public void onReceive(Context context, Intent intent) {
		
		boolean ON = true;
		turnOnDataConnection(ON, context);
		
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		
		Mail m;
		
		m = new Mail(settings.getString("uvalue", ""), settings.getString("pvalue", ""));
	
		String[] toArr = {settings.getString("fuvalue", "")};
		
		m.setTo(toArr); // load array to setTo function
		m.setFrom(settings.getString("uvalue", "")); // who is sending the email 
		m.setSubject("Remote Phone Controller"); 
		m.setBody(intent.getExtras().getString("kmrl")); 
		
		try { 
			//m.addAttachment("/sdcard/myPicture.jpg");  // path to file you want to attach
				if(m.send()) {
					
					// success
					//Toast.makeText(context, "Email was sent successfully.", Toast.LENGTH_LONG).show(); 
				} else { 
					// failure
					//Toast.makeText(context, "Email was not sent.", Toast.LENGTH_LONG).show(); 
			} 
			} catch(Exception e) { 
				// some other problem
				//Toast.makeText(context, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
			}
		

		//}
	}
	};
	
	
@Override
public IBinder onBind(Intent arg0) {
return null;
}

@Override
public int onStartCommand(Intent intent, int flags, int startId) {
// We want this service to continue running until it is explicitly
// stopped, so return sticky.
Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

boolean ON = true;
turnOnDataConnection(ON, Mserv.this);

sentPI = PendingIntent.getBroadcast(this, 0,
		new Intent(SENT), 0);
		deliveredPI = PendingIntent.getBroadcast(this, 0,
		new Intent(DELIVERED), 0);
		//---intent to filter for SMS messages received---
		intentFilter = new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");
		
		registerReceiver(intentReceiver, intentFilter);

return START_STICKY;
}

@Override
public void onDestroy() {
super.onDestroy();
Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

}
}