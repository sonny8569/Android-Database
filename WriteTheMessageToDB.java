package com.example.project3;

import android.util.Log;

import androidx.annotation.RequiresPermission;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriteTheMessageToDB extends StringRequest {
    final static private String URL = "http://sonny8569.dothome.co.kr/DBAdmin/writeMessage.php";
    private Map<String, String> parameters;


    public WriteTheMessageToDB (String ID, String SendID, String Title, String
            Content,String Time, Response.Listener<String> listener)  {
        super(Method.POST, URL, listener, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RegisterRequest", error.getMessage());
            }
        });
        parameters = new HashMap<>();
        parameters.put("ID", ID);
        parameters.put("SENDID", SendID);
        parameters.put("TITLE", Title);
        parameters.put("CONTENT", Content);
        parameters.put("TIME", Time);
        parameters.put("READSTATE", "0");
        parameters.put("WRITESTATE", "0");
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
