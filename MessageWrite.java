package com.example.project3;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import kotlinx.coroutines.channels.Send;

public class MessageWrite extends Fragment {
      String SendID="";
    String SendTitle="";
    String SendTime="";
    EditText Title;
    EditText Content;
    EditText ReceiveID;
    Button buttonSend;
    Button buttonCancel;
    Button buttonUserSearch;
    String userid;
    userinfo user1;
    MessageWrite(userinfo user, String SendID)
    {
        this.user1=user;
        this.userid=SendID;
    }
    MessageWrite(userinfo user, String SendID, String SendTitle, String SendTime)
    {
        this.user1=user;
        this.SendID=SendID;
        this.SendTitle=SendTitle;
        this.SendTime=SendTime;
        this.userid = SendID;
    }

    public MessageWrite(userinfo user, String id, String userid) {
        user1 = user;
        SendID = id;
        this.userid = userid;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.messagewrite,container,false);
        message activity = ((message)getActivity());

        activity.getSupportActionBar().setTitle("메세지작성");

        ReceiveID = rootView.findViewById(R.id.ReceiveID);
        Title = rootView.findViewById(R.id.message_title);
        Content = rootView.findViewById(R.id.message_content);
        buttonSend = (Button)rootView.findViewById(R.id.btnMessageSend);
        buttonUserSearch = (Button)rootView.findViewById(R.id.btnUserSearch);
        if(SendID.length()>0){
            ReceiveID.setText(SendID);
            if(SendTitle.length()>0){
                Title.setText("답장");
            }
        }

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                long lNow = System.currentTimeMillis();
                Date dt = new Date(lNow);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean isSuccess = jsonResponse.getBoolean("success");
                            if (isSuccess) { //답장완료
                                if(SendTitle.length()>0) {
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean isSuccess =
                                                        jsonResponse.getBoolean("success");
                                                if (isSuccess) {

                                                } else {
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                     }
                                else{

                                }

                            } else {
                                Toast.makeText(getActivity(), "알수없는 이유로 생성이 되지않았습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                WriteTheMessageToDB registerRequest = new
                        WriteTheMessageToDB(ReceiveID.getText().toString(),userid , Title.getText().toString(),
                        Content.getText().toString(), sdf.format(dt), responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(registerRequest);
            }});
        buttonUserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((message)getActivity()).SendFragment();
            }
        });
        return  rootView;
    }
}
