package com.dhilipbinny.ca2.data;

import com.dhilipbinny.ca2.Utils;
import com.dhilipbinny.ca2.data.model.LoggedInUser;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    static String error="";
    JSONObject data;
    String status="";
    String user_id=null;
    String user_name=null;

    public Result<LoggedInUser> login(String username, String password) {

        try {

            System.out.println("******************** going to call login");
            Utils u = new Utils();
            JSONObject obj = u.loadJSONFromAPI(u.baseurl+ "/login/login_android?Username="+username+"&Password="+password);

            data = obj.getJSONObject("data");
            status = data.getString("status");

            if(status.equals("ok")){
                user_id = data.getString("user_id");
                user_name = data.getString("user_name");
                LoggedInUser user = new LoggedInUser(user_id+"/"+user_name, user_id+"/"+user_name);
                return new Result.Success<>(user);
            }
            else {
                error = status;
                return new Result.Error(new IOException(status));
            }

        } catch (Exception e) {
            return new Result.Error(new IOException(status, e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public static String geterror(){
        return error;
    }
}
