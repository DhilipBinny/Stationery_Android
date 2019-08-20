package com.dhilipbinny.ca2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Async extends AsyncTask<Command, Void, JSONObject> {


        IserverResponse callback;

        public  JSONObject response = null;

        @Override
        protected  JSONObject  doInBackground(Command... cmds) {
            System.out.println("*********going to call..");
            Command cmd = cmds[0];
            this.callback=cmd.callback;
//            response = new Utils().loadJSONFromAPI(cmd.endpt);

            JSONObject json = new JSONObject();
            try {
                URLConnection connection = new URL(cmd.endpt).openConnection();
                InputStream inStream = connection.getInputStream();
                String htmlText = IOUtils.toString(inStream, connection.getContentEncoding());
                System.out.println( ">>?? = " + htmlText);
                JSONObject obj = new JSONObject(htmlText);
                json = obj;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("*********ended call..");
            return json;
        }

        @Override
        protected void onPostExecute( JSONObject result) {
            if(result!=null){
                this.callback.onServerResponse(result);
            }
        }

        public interface IserverResponse{
            void onServerResponse(JSONObject json);
        }


}