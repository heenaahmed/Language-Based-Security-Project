package com.example.contactstealerblock;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
 
public aspect IRM {
	HttpResponse hres = null;
	Activity mainActivity;
	boolean hasContactAccess = false;
	
	pointcut func2(Activity ma):execution(* *.onCreate(..)) && this(ma) ;
	pointcut func(HttpGet httpGet):call(* *.execute(..))&&args(httpGet);
	
	HttpResponse around(final HttpGet httpGet):func(httpGet){
		Log.d("AOP MSG", "SENDING DATA TO SERVER");
		URI uri = httpGet.getURI();
		String query = uri.getQuery();
		Log.d("AOP MSG",query);
		
		Toast toast = Toast.makeText(mainActivity, "The app is trying to send data to"+uri.getPath(), Toast.LENGTH_LONG);
		toast.show();
		
		//builder alert = new AlertDialog(mainActivity);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Send reqquest to : "+uri.getPath());
        builder.setMessage("!!!Possible malicious activity.Do want to continue to "+uri.getHost());
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				hres = proceed(httpGet);
			}
		});
        
		builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				hres = null;
			}
			
		});
		AlertDialog alert = builder.create();
        alert.show();
    	return hres;
	}
	
	before(Activity ma):func2(ma){
		Log.d("AOP MSG", "onCreate()");
		
		mainActivity = ma;
		Log.d("AOP MSG", "closing onCreate()");
	}
	
}
