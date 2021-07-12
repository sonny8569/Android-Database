package com.example.project3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class SearchUser extends Fragment {

    userinfo user;
    EditText mEdit;
    int backactivity;
    //backactivity 0 받은 1보낸
    Spinner Major;
    String strDepartment;
    Button buttonSearch;
    String userID;
    String slMajor;
    String Time ;
    String SendId;
    String title;
    SearchUser(String id){
        userID = id;
    }
    List<userinfo> user_List;
    UserListAdapter adapter;
    ListView listview;
    MessageWrite ms;



    @Nullable
    @Override//그게 이거임
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.userserach, container, false);
        message activity = ((message) getActivity());
        activity.getSupportActionBar().setTitle("수신자 선택");
        Major = (Spinner)rootView.findViewById(R.id.departmentselect);
        buttonSearch = rootView.findViewById(R.id.buttonSearch);
        user_List = new ArrayList<>();
        adapter = new UserListAdapter(this.getContext(),user_List,user,userID);
        listview = rootView.findViewById(R.id.listView);
        listview .setAdapter(adapter);

        Major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              slMajor = Major.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new findTheUser().execute();
            }
        });
        return rootView;
    }

    class findTheUser extends AsyncTask<Void, Void, String> {
        String traget;

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(traget);
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
                traget ="http://sonny8569.dothome.co.kr/DBAdmin/SearchMajor.php?DEPARTMENT = "+ URLEncoder.encode(slMajor,"UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                user_List.clear();
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                String ID, NAME;
                int State = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    //ID , SendID, TITLE, CONTENT, TIME, 메세지상태인 STATE를 적어준다.
                    JSONObject row = jsonArray.getJSONObject(i);
                    ID = row.getString("ID");
                    NAME = row.getString("NAME");
                    userinfo user = new userinfo(ID,"",NAME,slMajor );
                    user_List.add(user);
                }
                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
