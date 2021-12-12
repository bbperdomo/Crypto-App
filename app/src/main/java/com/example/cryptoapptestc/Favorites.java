package com.example.cryptoapptestc;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.speech.RecognizerIntent;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Favorites extends AppCompatActivity implements Serializable{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_rv_item);

        Bundle bundle = getIntent().getExtras();
        String jsonString = bundle.getString("KEY");
        Gson gson = new Gson();
        Type listOfCurrencyType = new TypeToken<List<CurrencyModal>>() {}.getType();
        ArrayList<CurrencyModal> modals = gson.fromJson(jsonString, listOfCurrencyType);
        System.out.println(modals);

        //getIncomingIntent();
    }

    private void getIncomingIntent(){


        Intent intent = getIntent();
        //ArrayList<CurrencyModal> mylist = (ArrayList<CurrencyModal>)intent.getSerializableExtra("mylist");

        //setString(mylist);


    }

    private void setString(ArrayList mylist){

        TextView name = findViewById(R.id.textView);
        //name.setText((CharSequence) mylist);

        //Button name = findViewById(R.id.button2);
        //name.setText((CharSequence) mylist);
    }


}
