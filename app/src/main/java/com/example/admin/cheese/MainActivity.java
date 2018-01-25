package com.example.admin.cheese;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {


    ArrayList<Cheese> cheesesList;
    cheeseFragment cheeseFragment;
    cheeseFragment.CheeseListAdapter adapter;
    RecyclerView recyclerView;

    int textlength = 0;
    EditText etSearch;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) findViewById(R.id.etSearch);


        cheesesList = new ArrayList<>();

        /*  retrieving data Method call */
        getJsonDatat();



        /* sort collection data ascending order*/

        Collections.sort(cheesesList, new Comparator<Cheese>() {
            @Override
            public int compare(Cheese item, Cheese t1) {
                String s1 = item.getName();
                String s2 = t1.getName();
                return s1.compareToIgnoreCase(s2);
            }

        });


        recyclerView = (RecyclerView) findViewById(R.id.rvCheese);

        cheeseFragment = new cheeseFragment();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new cheeseFragment.CheeseListAdapter(this, cheesesList);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);





        /* Text  Changed    Listener  on Edit Text view */

        etSearch.addTextChangedListener(new TextWatcher() {

            ArrayList<Cheese> cheesesList_sort = new ArrayList<>();


            public void afterTextChanged(Editable s) {

                cheesesList_sort.clear();
                String str = etSearch.getText().toString().toLowerCase().trim();

                if (str == "") {
                    cheesesList_sort = cheesesList;

                } else {


                    for (int i = 0; i < cheesesList.size(); i++) {
                        if (cheesesList.get(i).getName().toLowerCase().trim().startsWith(str)) {
                            cheesesList_sort.add(cheesesList.get(i));
                        }
                    }

                }


                adapter = new cheeseFragment.CheeseListAdapter(MainActivity.this, cheesesList_sort);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
        });
       /*  Text  Changed    Listener End line*/

    }

    /**
     * retrieving data Method
     */
    private void getJsonDatat() {


        Cheese cheese;

        String json = null;
        try {
            InputStream inputStream = getAssets().open("cheese.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        String data = "";
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(json);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("cheeses");

            //Iterate the jsonArray and print the info of JSONObjects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                String name = jsonObject.optString("Name").toString();

                cheese = new Cheese(name);

                cheesesList.add(cheese);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
