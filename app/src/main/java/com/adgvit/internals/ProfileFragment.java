package com.adgvit.internals;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button logoutbtn;
    Button logoutlogoutbtn,logoutReset;
    Button logoutcancelbtn,cancelReset;
    TextView initials;
    String initials1="";
    Button resetPw;
    Button Team;
    FirebaseAuth mauth;
    String name,regNo,email,phone,team,domain;
    TextView profileName,regNoText,userEmail,userContact,textDomain;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    List<String> teamlist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initials1="";

        logoutbtn = (Button) view.findViewById(R.id.logoutBtn);
        logoutbtn.setOnClickListener((View.OnClickListener) this);
        initials = view.findViewById(R.id.initialsText);

        resetPw = (Button) view.findViewById(R.id.resetpwBtn);
        resetPw.setOnClickListener((View.OnClickListener) this);

        Team = (Button) view.findViewById(R.id.knowMoreBtn);
        Team.setOnClickListener((View.OnClickListener) this);


        profileName=view.findViewById(R.id.profileName);
        regNoText = view.findViewById(R.id.regNo);
        userEmail = view.findViewById(R.id.userEmail);
        userContact = view.findViewById(R.id.userContact);
        textDomain = view.findViewById(R.id.domain);

        mauth=FirebaseAuth.getInstance();

        pref = view.getContext().getSharedPreferences("com.adgvit.com.userdata", Context.MODE_PRIVATE);
        editor = pref.edit();
        name = pref.getString("name","");
        regNo = pref.getString("regNo","");
        email = pref.getString("emailid","");
        phone = pref.getString("phone","");
        team = pref.getString("teams","");

        String name1 = name;
        name1 = name1.trim();
        String words[] = name1.split(" ");
        for(String word : words) {
            try {
                //Log.i("Initial",Character.toUpperCase(word.charAt(0)) + " ");
                initials1 = initials1 +Character.toUpperCase(word.charAt(0));
            }
            catch (StringIndexOutOfBoundsException e){
                //Log.i("Exception","String Index Out of bounds");
            }

        }
        initials.setText(initials1);




        String team1 = team.replace("[", "");
        String team2 = team1.replace("]", "");
        teamlist= new ArrayList<>(Arrays.asList(team2.split(", ")));
        domain = teamlist.get(0);
        //Log.i("Domain",domain);
        profileName.setText(name);
        regNoText.setText(regNo);
        userEmail.setText(email);
        userContact.setText(phone);
        if(domain.equals("0")){
            textDomain.setText("iOS Development");
        }
        else if(domain.equals("1")){
            textDomain.setText("Web Development");
        }
        else if(domain.equals("2")){
            textDomain.setText("Android Development");
        }
        else if(domain.contains("3")){
            textDomain.setText("Machine Learning ");
        }
        else if(domain.contains("4")){
            textDomain.setText("Logistics");
        }
        else if(domain.contains("5")){
            textDomain.setText("Sponsorship");
        }
        else if(domain.contains("6")){
            textDomain.setText("Marketing");
        }
        else if(domain.contains("7")){
            textDomain.setText("Design");
        }
        else if(domain.contains("8")){
            textDomain.setText("Video Editing");
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.resetpwBtn) {
            Dialog resetDialog = new Dialog(getContext(),R.style.Theme_Dialog);
            resetDialog.setContentView(R.layout.reset_password_dialog);
            // add functions for button
            logoutReset = resetDialog.findViewById(R.id.logoutResetBtn);
            cancelReset = resetDialog.findViewById(R.id.cancelResetBtn);

            resetDialog.show();

            logoutReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   SharedPreferences pref = v.getContext().getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
                   String emailID = pref.getString("emailid","");
                   mauth.sendPasswordResetEmail(emailID).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(v.getContext(), "Check email", Toast.LENGTH_LONG).show();
                                resetDialog.dismiss();
                                //startActivity(new Intent(v.getContext(),LoginActivity.class));
                            }
                            else{
                                Toast.makeText(v.getContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                                resetDialog.dismiss();

                            }
                        }
                    });

                }
            });

            cancelReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
                    resetDialog.dismiss();
                }
            });


        }

        if (v.getId() == R.id.knowMoreBtn) {
            Fragment aboutus1 = new aboutus();
            FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,aboutus1);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        if (v.getId() == R.id.logoutBtn) {
            Dialog logoutDialog = new Dialog(getContext(),R.style.Theme_Dialog);
            logoutDialog.setContentView(R.layout.logout_dialog);
            // add functions for button
            logoutlogoutbtn = logoutDialog.findViewById(R.id.logoutLogoutBtn);
            logoutcancelbtn = logoutDialog.findViewById(R.id.cancelLogoutBtn);

            logoutDialog.show();

            logoutlogoutbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mauth.signOut();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    clearData();
                    Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    logoutDialog.dismiss();

                }
            });

            logoutcancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
                    logoutDialog.dismiss();
                }
            });
        }
    }
    public void clearData(){
        SharedPreferences userdata = getContext().getSharedPreferences("com.adgvit.com.userdata",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser = userdata.edit();
        editoruser.clear();
        editoruser.apply();
        SharedPreferences userdata1 = getContext().getSharedPreferences("com.adgvit.com.alert",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser1 = userdata1.edit();
        editoruser1.clear();
        editoruser1.apply();
        SharedPreferences userdata2 = getContext().getSharedPreferences("com.adgvit.com.mid",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser2 = userdata2.edit();
        editoruser2.clear();
        editoruser2.apply();
        SharedPreferences preferences = getContext().getSharedPreferences("com.adgvit.com.mom", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = preferences.edit();
        editor3.clear();
        editor3.apply();
    }
}