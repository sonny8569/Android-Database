package com.example.project3;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChangUserInfoDb extends StringRequest {
    final static private String URL = "http://sonny8569.dothome.co.kr/DBAdmin/changeInfo.php";
    private Map<String , String> parameters;

    public ChangUserInfoDb(String userID, String userPassword, String userMajor, String userName,String userImage,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RegisterRequest", error.getMessage());
            }
        });
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userMajor",userMajor);
        parameters.put("userName", userName);
        parameters.put("userImage",userImage);


    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
