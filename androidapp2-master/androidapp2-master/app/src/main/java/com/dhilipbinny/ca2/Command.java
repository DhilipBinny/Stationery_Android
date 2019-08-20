package com.dhilipbinny.ca2;

import org.json.JSONObject;

public class Command {

    protected Async.IserverResponse callback;
    protected  String context;
    protected String endpt;
    protected JSONObject data;

    Command(Async.IserverResponse callback, String context,String endpt,JSONObject data){
        this.callback=callback;
        this.context=context;
        this.data=data;
        this.endpt=endpt;
    }
}
