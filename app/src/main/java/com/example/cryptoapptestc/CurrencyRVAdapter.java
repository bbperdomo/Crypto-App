package com.example.cryptoapptestc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// on below line we are creating our adapter class
// in this class we are passing our array list
// and our View Holder class which we have created.
public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.CurrencyViewholder> {
    //members
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private ArrayList<CurrencyModal> currencyModals;    //holds listings, filtered arraylist
    private Context context;


    //constructor method - creates instance of currencyRVadapter object when called
    public CurrencyRVAdapter(ArrayList<CurrencyModal> currencyModals, Context context) {
        this.currencyModals = currencyModals;
        this.context = context;
    }



    // below is the method to filter our list.
    //filterllist is actually the filteredlist arraylist from the filter() method in our main activity.
    public void filterList(ArrayList<CurrencyModal> filterllist) {
        // adding filtered list to our
        // array list and notifying data set changed
        currencyModals = filterllist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //inflates the view
    public CurrencyRVAdapter.CurrencyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item, parent, false);
        return new CurrencyRVAdapter.CurrencyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyRVAdapter.CurrencyViewholder holder, int position) {
        // on below line we are setting data to our item of
        // recycler view and all its views.
        CurrencyModal modal = currencyModals.get(position);
        holder.nameTV.setText(modal.getName());
        holder.rateTV.setText("$ " + df2.format(modal.getPrice()));
        holder.symbolTV.setText(modal.getSymbol());

        //attempt at onclick listener idk

//        holder.setOnClickListener((view) -> {
//
//            Intent intent =  new Intent(context, Favorites.class);
//            intent.putExtra("mylist", currencyModals.get(position));
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        // on below line we are returning
        // the size of our array list.
        return currencyModals.size();
    }

    // on below line we are creating our view holder class
    // which will be used to initialize each view of our layout file.
    public class CurrencyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView symbolTV, rateTV, nameTV;
        Button favbtn;

        //RelativeLayout parentLayout; //idk

        public CurrencyViewholder(@NonNull View itemView) {
            super(itemView);
            // on below line we are initializing all
            // our text views along with its ids.
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVRate);
            nameTV = itemView.findViewById(R.id.idTVName);

            favbtn = itemView.findViewById(R.id.button2);
            //parentLayout = itemView.findViewById((R.id.idCVCurrency));
            itemView.setOnClickListener(this);      //necessary for onClick below to work

            itemView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    View iv = itemView.findViewById(R.id.textView);
//                    System.out.println(itemView);

//                    Intent intent =  new Intent(context, Favorites.class);
//                    intent.putExtra("mylist", currencyModals.get(position));
//                    context.startActivity(intent);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(currencyModals);
                    Intent intent =  new Intent(context, Favorites.class);
                    intent.putExtra("KEY",jsonString);
                    context.startActivity(intent);

                }
            });

        }


        //item click that takes you to another activity that displays a specific currency.
        //ideally this would jump to a whole new activity, not favorites.
        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Toast.makeText(context, "position" + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Favorites.class);
            intent.putExtra("mylist", currencyModals.get(position));
            intent.putExtra("mylist", currencyModals);
            context.startActivity(intent);

        }
    }


}
