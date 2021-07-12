package com.example.project3;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://sonny8569.dothome.co.kr/DBAdmin/project3_register.php";
    private Map<String , String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userDepartment, String userImage,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RegisterRequest", error.getMessage());
            }
        });
        parameters = new HashMap<>();
        parameters.put("userID", userID);
       parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
       parameters.put("userMajor", userDepartment);
        parameters.put("userImage",userImage);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
