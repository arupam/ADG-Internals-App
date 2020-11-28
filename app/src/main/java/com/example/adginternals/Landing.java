package com.example.adginternals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Button loginBtn = findViewById(R.id.loginBtn);
        ImageView landingImg = findViewById(R.id.landingimg);
        Animation buttonAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.buttonup);
        Animation fadeAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        landingImg.setAnimation(fadeAnim);
        loginBtn.setAnimation(buttonAnim);
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()!=null){
            Intent myIntent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(myIntent);
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(myIntent);
            }
        });
    }
}