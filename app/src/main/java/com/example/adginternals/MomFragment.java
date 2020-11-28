package com.example.adginternals;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MomFragment extends Fragment {
    RecyclerView recyclerView;//String t[] , d[] ;

    ArrayList<momItem> momItems = new ArrayList<>();
    EditText momSearchBar;
    MyAdapter myAdapter;
    DatabaseReference myref;
    String team;
    ArrayList<String> mylist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mom, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.mom_recycler);

        Resources res = getResources();
        //fetch from firebase and add here
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        myref = db.getReference("MOMS");

        SharedPreferences pref = view.getContext().getSharedPreferences("com.adgvit.com.userdata", Context.MODE_PRIVATE);
        team = pref.getString("teams","");
        String team1 = team.replace("[", "");
        String team2 = team1.replace("]", "");
        mylist= new ArrayList<>(Arrays.asList(team2.split(", ")));
        Log.i("team", String.valueOf(mylist));
        addData();
        adapter();



        momSearchBar = (EditText) view.findViewById(R.id.momSearch);
        momSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    filter(s.toString());
            }
        });

         return  view;
    }

    private void filter(String text) {
        ArrayList<momItem> filteredList = new ArrayList<>();

        for(momItem item : momItems){
            if(item.getDate().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        myAdapter.filterList(filteredList);

    }
    public void addData(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                momItems.clear();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    getMomdetails momdetails = ds.getValue(getMomdetails.class);
                    String header = momdetails.getHeader();
                    String time = unixconvert(momdetails.getTime().toString());
                    String title = momdetails.getTitle();
                    String team = momdetails.getTeam();
                    if (mylist.contains(team)) {
                        momItems.add(new momItem(time, title, header, new String[]{"Everyone has to get atleast 5 participants from their end.",
                                "Desk duties will be alloted and everyone is asked to report on time.",
                                "Valid reason has to be provided for not attending the meeting in the ADG Internals app."}));
                    }
                }
                adapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void adapter(){
        myAdapter = new MyAdapter(getContext(),momItems);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    public String unixconvert(String time){
        long dv = Long.valueOf(time)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("dd MMM yyyy").format(df);
        return vv;
    }
}

