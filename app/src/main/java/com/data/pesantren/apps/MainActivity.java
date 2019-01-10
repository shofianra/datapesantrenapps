package com.data.pesantren.apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String URL="https://azisabdulb.000webhostapp.com/apiponpes/public/ponpes/";
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerViewl;
    private ArrayList<Data> listdata;

    SearchView searchView;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewl=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewl.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(this,2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewl.setLayoutManager(layoutManager);

        listdata=new ArrayList <Data>();
        AmbilData();
        recyclerAdapter=new RecyclerAdapter(this, listdata);
        recyclerViewl.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();

    }

    public void AmbilData(){
        JsonArrayRequest arrayRequest=new JsonArrayRequest("https://azisabdulb.000webhostapp.com/apiponpes/public/ponpes/", new Response.Listener <JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length();i++) {
                        try {
                            JSONObject data = response.getJSONObject(i);
                            Data item = new Data();
                            item.setId(data.getString("id"));
                            item.setNama_institusi(data.getString("nama_institusi"));
                            item.setAlamat(data.getString("alamat"));
                            item.setLatitude(data.getString("latitude"));
                            item.setLongitude(data.getString("longitude"));
                            item.setJenis(data.getString("jenis"));
                            listdata.add(item);
                            recyclerAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                        }
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

        };
        Volley.newRequestQueue(this).add(arrayRequest);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_search, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();

        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                recyclerAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }
}
