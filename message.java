package com.example.project3;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.os.Build.ID;
import static android.os.Build.TIME;
import static com.example.project3.MainActivity.REQCODE_ACTEDIT;

public class message extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    LinearLayout firstContainer, fragmentContainer;
    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle barDrawerToggle;
    public static message activity;
    userinfo user;
    String ID;
    String picture;
    final static int REQCODE_ACTEDIT=1001;
    Bitmap selectIamge;
    final static int ACT_ADD_PHOTO = 0;
    private static int PICK_IMAGE_REQUEST = 1;
    ImageButton button;
    private AlertDialog dialog;
    private String sendId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        activity=this;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( this);
        Intent intent =getIntent();
        ID = intent.getStringExtra("UserIDOut");
        Thread uThread = new Thread() {
            @Override
            public void run(){
                try{
                    URL url = new
                            URL("http://sonny8569.dothome.co.kr//sonny8569/AccountManagement/file/"+ID
                            +".jpg");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true); //Server 통신에서 입력 가능한 상태로듦
                    conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야제 통신 가능함)
                    InputStream is = conn.getInputStream(); //inputStream 값가져오기
                    selectIamge = BitmapFactory.decodeStream(is); // Bitmap으로 반환
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        uThread.start(); // 작업 Thread 실행


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_message_write, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_option)
                .setDrawerLayout(drawer)
                .build();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void FixInforMation(String id, String passwrod, String Name, String Major){
        {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean isSuccess = jsonResponse.getBoolean("success");
                        if (isSuccess) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(message.this);
                            dialog = builder.setMessage("Created.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).create();
                            dialog.show();

                        } else {
                            Toast.makeText(activity, "잘못 입력하였습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            String newPicture  = MakeImage(button.getDrawable());


            ChangUserInfoDb registerRequest = new ChangUserInfoDb(id ,passwrod , Name,
                    Major,newPicture,  responseListener);
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(registerRequest);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_option){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, new
                    ChangeInforMation(ID,selectIamge));
            fragmentTransaction.commit();
        }
        if(id == R.id.nav_message_write){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, new
                    MessageWrite(user,ID));
            fragmentTransaction.commit();

        }
        if(id == R.id.nav_message_Send){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, new
                    sendMessage(user,ID));
            fragmentTransaction.commit();
        }
        if(id==R.id.nav_message_Receive){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, new
                    MessageRecived(user,ID));
            fragmentTransaction.commit();
        }
        if(id==R.id.nav_menu_logout){
            System.exit(0);
        }
        return true;
    }

    public void SendFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, new
                SearchUser(ID));
        fragmentTransaction.commit();
    }

    public void addPhotoClick(View view) {
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

                if(ChangeInforMation.ChangeInforMation_Inst!=null)
                {
                    button= ChangeInforMation.ChangeInforMation_Inst.GetButton();
                    button.setImageBitmap(scaled);


                }

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


    public void SendFragment(userinfo user ,String getSendId, String getTitle, String getTime) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, new
                MessageWrite(user,getSendId, getTime,getTitle));
        fragmentTransaction.commit();
    }
}