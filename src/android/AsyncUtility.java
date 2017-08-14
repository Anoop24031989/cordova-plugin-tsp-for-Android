
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.os.AsyncTask;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.entity.StringEntity;
import sample.test;

public class AsyncUtility extends AsyncTask<String,String, String> {

	 @Override
	    public String doInBackground(String... uri) {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        HttpPost post = new HttpPost(uri[0]);
	        try {
	        	post.setEntity(new StringEntity(uri[1], "UTF8"));
	        	post.setHeader("Content-type", "application/json");
	            response = httpclient.execute(post);
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	                ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                responseString = out.toString();
	                out.close();
	            } else{
	                //Closes the connection.
	            	responseString=Integer.toString(statusLine.getStatusCode());
	                response.getEntity().getContent().close();
	                
	                throw new IOException(statusLine.getReasonPhrase());
	                
	            }
	        } catch (ClientProtocolException e) {
	            //TODO Handle problems..
	        } catch (IOException e) {
	            //TODO Handle problems..
	        }
	        return responseString;
	    }


}
