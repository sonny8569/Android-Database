package com.example.project3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class SeeTheMessage extends Fragment {
    userinfo userinfo;
    UserMessage message1;
    int postion;
    String sender;
    String time;
    TextView Send , recived ,title, timetext , content;
    Button  delete , resend , back ;
    SeeTheMessage(userinfo userinfo , UserMessage msg  ){
        this.userinfo = userinfo;
        this.message1= msg;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.seethemessage, container, false);
        message activity=((message)getActivity());
        activity.getSupportActionBar().setTitle("메세지 읽기");

        Send=rootView.findViewById(R.id.send_id);
        recived=rootView.findViewById(R.id.receive_id);
        title=rootView.findViewById(R.id.title_message);
        timetext=rootView.findViewById(R.id.date_time);
        content=rootView.findViewById(R.id.content_str);
        back=rootView.findViewById(R.id.gotoback);
        delete=rootView.findViewById(R.id.deletebutton);
        resend=rootView.findViewById(R.id.resend);
        String ID=message1.GetRecivedId();
        String m_SendID=message1.GetSendId();
        String Title = message1.GetTitle();
        String Contents = message1.getContent();
        String Times = message1.GetTime();
        recived.setText(ID);
        Send.setText(m_SendID);
        title.setText(Title);
        timetext.setText(Times);
        content.setText(Contents);
        back.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {

        }});
        delete.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean isSuccess = jsonResponse.getBoolean("success");
                        if (isSuccess) {

                        } else {
                            Toast.makeText(getActivity(), "삭제오류",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            MessageDelete deleteRequest = new MessageDelete(message1.GetRecivedId(),message1.GetSendId(),message1.GetTime(),
                    responseListener);
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(deleteRequest);
        }});
        resend.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            message.activity.SendFragment( userinfo,message1.GetSendId().toString(),message1.GetTitle().toString(),message1.GetTime().toString());
        }});
        return rootView;
       // MessageWrite(userinfo user, String SendID, String SendTitle, String SendTime)
    }
}