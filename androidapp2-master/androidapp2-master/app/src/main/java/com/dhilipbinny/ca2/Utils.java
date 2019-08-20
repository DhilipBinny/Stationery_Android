package com.dhilipbinny.ca2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.StrictMode;
import android.util.JsonReader;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.*;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;


public class Utils {
    public String baseurl = "https://adstationery.azurewebsites.net";

//    https://androidadstationery20190812072758.azurewebsites.net
//    https://adstationery.azurewebsites.net
//    http://10.0.2.2/stationeryapp

    public String loadJSONFromAsset(Activity activity, String jsonfilename ) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(jsonfilename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public JSONObject loadJSONFromAPI( String URI ) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = new JSONObject();
        try {
            URLConnection connection = new URL(URI).openConnection();
            InputStream inStream = connection.getInputStream();
            String htmlText = IOUtils.toString(inStream, connection.getContentEncoding());
            System.out.println( ">>?? = " + htmlText);
            JSONObject obj = new JSONObject(htmlText);
            json = obj;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }
    @TargetApi(19)
    public void  UpdateFormDetails(Activity activity, String URI, String comments, String form_id, String status,String approved_by){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
//            String urlParameters  = "name="+name+"&age="+pass;
            String urlParameters  = "form_id="+form_id+"&comments="+comments+"&status="+status+"&approved_by="+approved_by;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = URI;

            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setRequestProperty("Accept", "application/json");
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write(postData);
                System.out.println(postData.toString());
                System.out.println(conn.getResponseCode());

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(19)
    public void  UpdateDeptDetails(Activity activity, String URI, String rep, String collectionpt,String hod){
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String urlParameters  = "rep="+rep+"&collectionpt="+collectionpt+"&hod_id="+hod;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = URI;

            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setRequestProperty("Accept", "application/json");
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write(postData);
//                conn.connect();
                System.out.println("______________________________________________");
                System.out.println(postData.toString());
                System.out.println(conn.getResponseCode());

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(19)
    public void UpdateDelegateDetails(Activity activity,String URI,String emp_id, String from , String to,String hod_id){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String urlParameters  = "emp_id="+emp_id+"&from="+from+"&hod_id="+hod_id+"&to="+to;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = URI;

            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setRequestProperty("Accept", "application/json");
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write(postData);
                System.out.println(postData.toString());
                System.out.println(conn.getResponseCode());

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @TargetApi(19)
    public void RemoveDelegateDetails(Activity activity,String URI,String hod_id){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String urlParameters  = "&hod_id="+hod_id;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = URI;

            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setRequestProperty("Accept", "application/json");
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write(postData);
                System.out.println(postData.toString());
                System.out.println(conn.getResponseCode());

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String SaveFromAPI(Activity activity, String URI ) {
        String json = null;
        return json;
    }
}
