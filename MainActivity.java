package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText editTextId,editTextPasswrod;
    String UserId, UserPassword;
    private AlertDialog dialog;
    final static int REQCODE_ACTEDIT=1001;
    final static int REQCODE_SIGNACTIVITY = 1001;
    String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextId = findViewById(R.id.idForLogin);
        editTextPasswrod = findViewById(R.id.passwordForLogin);
    }

    public void ClickCheckBox(View view) {
    }

    public void LoginButton(View view) {
        UserId = editTextId.getText().toString();
        UserPassword = editTextPasswrod.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    if (isSuccess) {
                        Intent intent = new Intent(MainActivity.this,message.class);

                        intent.putExtra("UserIDOut",UserId);
                        startActivityForResult(intent,REQCODE_ACTEDIT);
                        finish();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        dialog = builder.setMessage("Login failed").setNegativeButton("Retry", null).create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(UserId, UserPassword, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(loginRequest);


    }

    public void SignUpButton(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);

    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQCODE_SIGNACTIVITY:
                if (resultCode == RESULT_OK) {
                    String User1 = data.getStringExtra("UserInfoOut");
                    User = User1;
                    editTextId.setText(User1);
                }
        }
    }

        @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}