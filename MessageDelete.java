package com.example.project3;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MessageDelete extends StringRequest {
    final static private String URL =  "http://sonny8569.dothome.co.kr/DBAdmin/messagedelete.php";
    private Map<String, String> parameters;
    public MessageDelete(String ID, String SendId, String Time,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RegisterRequest", error.getMessage());
            }
        });
        parameters = new HashMap<>();
        parameters.put("ID", ID);
        parameters.put("SENDID", SendId);
        parameters.put("TIME", Time);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
