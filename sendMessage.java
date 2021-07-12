package com.example.project3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

public class sendMessage extends Fragment {
    userinfo userinfo;
    List<UserMessage> userMessageList;
    MessageListAd ad;
    String id ;

    sendMessage(userinfo userinfo,String ID) {
        this.userinfo = userinfo;
        id  = ID;

    }

    public sendMessage(String getTime, String getSendId, String getTitle) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.showsendmessage, container, false);
        message activity = ((message) getActivity());
        activity.getSupportActionBar().setTitle("보낸 메세지 함");
        final ListView listView = (ListView) rootView.findViewById(R.id.showsendmessagelistView);

        userMessageList = new ArrayList<>();
        ad = new MessageListAd(this.getContext(), userMessageList,userinfo,1,id);
        listView.setAdapter(ad);

        new BackgroundTask().execute();
        return rootView;
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

            try {
                target = "http://sonny8569.dothome.co.kr/DBAdmin/findTheMessage.php?ID="+ URLEncoder.encode(id," UTF - 8 ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                userMessageList.clear();
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                String ID, SendID, Title, Content, Time;
                int ReadState = 0, WriteState = 0;
                for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject row = jsonArray.getJSONObject(i);
                    ID = row.getString("ID");
                    SendID = row.getString("SENDID");
                    Title = row.getString("TITLE");
                    Content = row.getString("CONTENT");
                    Time = row.getString("TIME");
                    ReadState = row.getInt("READSTATE");
                    WriteState = row.getInt("WRITESTATE");
                    UserMessage usermessage = new UserMessage (ID, SendID,Title, Content, Time, ReadState, WriteState);
                    userMessageList.add(usermessage);
                }
                ad.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}