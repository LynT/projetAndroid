package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    //this is the authentication part
    FirebaseAuth firebaseAuth;
    TextView mProfileTv ;
    BottomNavigationView nav;
    FrameLayout content;
    ArrayList<Model> models = new ArrayList<Model>();
    RecyclerView rvTechSolPoint;
    RvAdapter rvAdapter;
    TextView tvAdd, tvUpdate;
    EditText etEnterName;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        mProfileTv=findViewById(R.id.profileTV);

        //nav = findViewById(R.id.nav);


        firebaseAuth = FirebaseAuth.getInstance();
        rvTechSolPoint = findViewById(R.id.rv_list_item);
        tvAdd = findViewById(R.id.tv_add);
        etEnterName = findViewById(R.id.et_enter_name);
        tvUpdate = findViewById(R.id.tv_update);
        rvTechSolPoint.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        rvTechSolPoint.setLayoutManager(layoutManager);
        rvAdapter = new RvAdapter(getApplicationContext(), models,
                new RvAdapter.Onclick() {
                    @Override
                    public void onEvent(Model model, int pos) {
                        position = pos;
                        tvUpdate.setVisibility(View.VISIBLE);
                        etEnterName.setText(model.getName());
                    }
                });
        rvTechSolPoint.setAdapter(rvAdapter);
        tvAdd.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);}
        @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add: {
                insertMethod(etEnterName.getText().toString());

            }
            break;
            case R.id.tv_update: {
                models.get(position).setName(etEnterName.getText().toString());
                rvAdapter.notifyDataSetChanged();
                tvUpdate.setVisibility(View.GONE);
            }
            break;
        }
    }
    private void insertMethod(String name) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            Model model = gson.fromJson(String.valueOf(jsonObject), Model.class);
            models.add(model);
            rvAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            mProfileTv.setText(user.getEmail());



        }
        else{
            startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected  void onStart(){
        checkUserStatus();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
       if(id == R.id.action_logout){
         firebaseAuth.signOut();
         checkUserStatus();
       }
       return super.onOptionsItemSelected(item);
    }


    //private void  setFragment(Fragment fragment){

      //  FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       // fragmentTransaction.replace(R.id.content, fragment);
       // fragmentTransaction.commit();
    //}
}