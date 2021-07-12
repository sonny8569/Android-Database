package com.example.project3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class MessageListAd extends BaseAdapter {
   userinfo user;
   private Context context;
   private List<UserMessage> list;
   int check ;
   String myid;

   public MessageListAd(Context context , List<UserMessage>listm,userinfo userinfo,int readc,String id){
       this.context = context;
       list = listm;
       user=userinfo;
       check = readc;
       myid= id;

   }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view  = View.inflate(context,R.layout.sendmessage,null);
        ImageView image = view.findViewById(R.id.showstate);

        TextView textView = view.findViewById(R.id.sendtitle);
        textView.setText(list.get(position).GetTitle());
        TextView showTime = view.findViewById(R.id.sendTime);
        showTime.setText(list.get(position).GetTime());
        TextView content = view .findViewById(R.id.sendcontent);
        content.setText(list.get(position).getContent());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = message.activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, new
                        SeeTheMessage(user ,list.get(position)));
                fragmentTransaction.commit();
            }
        });
       return view;
    }
}
