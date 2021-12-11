package com.example.cryptoapptestc;

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
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // creating variable for recycler view,
    // adapter, array list, progress bar
    private RecyclerView currencyRV;
    private EditText searchEdt;
    private ArrayList<CurrencyModal> currencyModalArrayList; //"declaring"/ creating an arraylist that takes data only from modal class/object
    private CurrencyRVAdapter currencyRVAdapter;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEdt = findViewById(R.id.idEdtCurrency);   //search bar

        // initializing all our variables and array list.
        loadingPB = findViewById(R.id.idPBLoading);
        currencyRV = findViewById(R.id.idRVcurrency);
        currencyModalArrayList = new ArrayList<>();     //init empty modal array list

        // initializing our adapter class.
        //
        currencyRVAdapter = new CurrencyRVAdapter(currencyModalArrayList, this);    //instantiating adapter object to use in main activity, also passing empty modal list

        // setting layout manager to recycler view.
        currencyRV.setLayoutManager(new LinearLayoutManager(this));

        // setting adapter to recycler view.
        //passes RVAdapter object we just created through SetAdapter()
        currencyRV.setAdapter(currencyRVAdapter);

        // calling get data method to get data from API.\
        //At this point, currencyModalArrayList contains all currencies from CMC api listings
        getData();


        // on below line we are adding text watcher for our
        // edit text to check the data entered in edittext.
        //this is a listener that awaits a user to enter test into our search bar
        //The TextWatcher interface handles the function of detecting a change in text in our search bar for us.
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // on below line calling a
                // method to filter our array list
                filter(s.toString());
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent mainActivity = new Intent(getApplicationContext(), Login.class);
                        startActivity(mainActivity);
                        break;
                    case R.id.action_search:
                        Intent results = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(results);
                        break;

                }
                return true;
            }
        });

    }

    //String filter is the "char" s from the TextWatcher listener
    private void filter(String filter) {

        // on below line we are creating a new array list
        // for storing our filtered data.
        ArrayList<CurrencyModal> filteredlist = new ArrayList<>();

        // running a for loop to search the data from our array list.
        //I assume 'item' is an iterator
        for (CurrencyModal item : currencyModalArrayList) {

            // getname() grabs current currency name being iterated through in ModelArrayList
            //the string is converted to lowercase, then our 'char' or 'string' called "filter" (user typed in)
            //is then checked to see if it exists in a item name.
            //if it is, its added to our new filtered list.
            if (item.getName().toLowerCase().contains(filter.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // on below line we are checking
        // weather the list is empty or not.
        if (filteredlist.isEmpty()) {
            // if list is empty we are displaying a toast message.
            Toast.makeText(this, "No currency found..", Toast.LENGTH_SHORT).show();
        } else {
            // on below line we are calling a filter
            // list method to filter our list.
            currencyRVAdapter.filterList(filteredlist);
        }
    }

    private void getData() {
        // creating a variable for storing our string.
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        // creating a variable for request queue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // making a json object request to fetch data from API.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside on response method extracting data
                // from response and passing it to array list
                // on below line we are making our progress
                // bar visibility to gone.
                loadingPB.setVisibility(View.GONE);
                try {
                    // extracting data from json.
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String symbol = dataObj.getString("symbol");
                        String name = dataObj.getString("name");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        // adding all data to our array list.
                        currencyModalArrayList.add(new CurrencyModal(name, symbol, price));
                    }
                    // notifying adapter on data change.
                    currencyRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    // handling json exception.
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // displaying error response when received any error.
                Toast.makeText(MainActivity.this, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                // in this method passing headers as
                // key along with value as API keys.
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "6bc29366-b780-4ab3-aa44-a9c58cf30209");
                // at last returning headers
                return headers;
            }
        };
        // calling a method to add our
        // json object request to our queue.
        queue.add(jsonObjectRequest);
    }



}
