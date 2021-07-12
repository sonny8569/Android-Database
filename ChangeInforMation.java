package com.example.project3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;

import java.io.ByteArrayOutputStream;

public class ChangeInforMation extends Fragment {
    userinfo user;
    TextView id;
    EditText pw ,name;
    private ArrayAdapter adapter;
    private Spinner spinner;
    String newMajor;
    Button changeButton;
    String userid;
    Bitmap picture;
    ImageButton imageButton;
    public static ChangeInforMation ChangeInforMation_Inst;
    String newPicture;
    ChangeInforMation(String userId,Bitmap picture){
        this.userid = userId;
        this.picture = picture;
        ChangeInforMation_Inst=null;

    }


    public ChangeInforMation(String id, String passwrod, String name, String major, Response.Listener<String> responseListener) {
    }

    public ImageButton GetButton()
    {
        return imageButton;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fixmyinfo,container,false);
        message nav = (message) getActivity();

        id =rootView.findViewById(R.id.ID);
        pw = rootView.findViewById(R.id.PasswordEdit);
        name = rootView.findViewById(R.id.nameEdit);
        spinner = (Spinner)rootView.findViewById(R.id.departmentoption);
        changeButton = rootView.findViewById(R.id.btnChange);
        imageButton = rootView.findViewById(R.id.btnImg);

        imageButton.setImageBitmap(picture);
        id.setText(userid);

        if(ChangeInforMation_Inst==null)
            ChangeInforMation_Inst=this;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newMajor = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ((message) getActivity()).addPhotoClick(v);

            }
        });
        changeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

               nav.FixInforMation(id.getText().toString(),pw.getText().toString(),name.getText().toString(),newMajor);
            }
        });
        return rootView;
    }


}
