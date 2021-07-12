package com.example.project3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class UserListAdapter extends BaseAdapter{
    userinfo user;
    private Context context;
    private List<userinfo> list;
    String userid;
    public UserListAdapter(Context context, List<userinfo> user_list, userinfo user,String myid) {
        this.context = context;
        this.list = user_list;
        this.user= user;
        userid = myid;
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
        View view = View.inflate(context,R.layout.item_notice,null);
        TextView id= view.findViewById(R.id.textViewTitle);
        Button sendButton = view.findViewById(R.id.selectTheSend);


        id.setText(list.get(position).getId()+"("+list.get(position).getName());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = message.activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, new
                        MessageWrite(user ,list.get(position).getId(),userid));
                fragmentTransaction.commit();


            }
        });
        return view;
    }
}
