package com.example.project3;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class SignUp extends AppCompatActivity {
    private ArrayAdapter adapter;
    private Spinner spinner;
    EditText id ,name ,password;
    private AlertDialog dialog;
    Button checkButton ;
    ImageButton ImageButton;
    String Major;
    private boolean validate = false;
    Uri selectImageUrl;
    private static int PICK_IMAGE_REQUEST = 1;
    final static int REQCODE_ACTEDIT=1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        spinner = findViewById(R.id.selectMajor);
        adapter = ArrayAdapter.createFromResource(this,R.array.item, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        id = findViewById(R.id.Id);
        password = findViewById(R.id.password);
        name = findViewById(R.id.Name);
        checkButton = findViewById(R.id.checkTheId);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Major = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void CheckTheId(View view) {
        if(id.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
            dialog = builder.setMessage("ID is empty.").setNegativeButton("Retry", null).create();
            dialog.show();
            return;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    if (isSuccess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                        dialog = builder.setMessage("Good ID").setPositiveButton("OK", null).create();
                        dialog.show();
                        id.setEnabled(false);
                        checkButton.setEnabled(false);
                        validate = true;
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                        dialog = builder.setMessage("ID is already used").setNegativeButton("Retry", null).
                                create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ValidateRequest validateRequest = new ValidateRequest(id.getText().toString(),responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
        requestQueue.add(validateRequest);
    }

    public void signpButton(View view) {

        if(!validate){
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
            dialog = builder.setMessage("Press Check").setNegativeButton("Retry", null).create();
            dialog.show();
            return;
        }
        String userId= id.getText().toString();
        String userPassword = password.getText().toString();
        String UserName = name.getText().toString();
        String MyProfilePicture = MakeImage(ImageButton.getDrawable());
        if (userId.equals("") | userPassword.equals("") | Major.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
            dialog = builder.setMessage("Field is empty.").setNegativeButton("Retry", null).create();
            dialog.show();
            return;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    if (isSuccess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                        dialog = builder.setMessage("Created.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent();
                                intent.putExtra("UserInfoOut", id.getText().toString());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }).create();
                        dialog.show();
                    } else {
                        Log.e("RegisterActivity", "register failed");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RegisterRequest registerRequest = new RegisterRequest(userId, userPassword,UserName,Major,MyProfilePicture, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
        requestQueue.add(registerRequest);


    }
    @Override
    protected void onStop() {
       super.onStop();
       if(dialog !=null){
           dialog.dismiss();
           dialog = null;
       }
    }


    public void SaveImage(View view) {
       Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
       intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (200.0/ bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 400, nh, true);

                ImageButton = findViewById(R.id.addImage);
                ImageButton.setImageBitmap(scaled);

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public String MakeImage(Drawable picture){
            Bitmap bitmap = ((BitmapDrawable)picture).getBitmap();
             ByteArrayOutputStream stream = new ByteArrayOutputStream();
           bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
             byte[] image = stream.toByteArray();
             String MyProfilePicture = Base64.encodeToString(image,Base64.DEFAULT);
             return  MyProfilePicture;
    }



}
