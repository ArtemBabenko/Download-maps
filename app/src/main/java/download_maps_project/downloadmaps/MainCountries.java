package download_maps_project.downloadmaps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import download_maps_project.downloadmaps.Adapter.CountryAdapter;
import download_maps_project.downloadmaps.Models.Country;

public class MainCountries extends AppCompatActivity {
    private String name;
    private ArrayList<Country> arrayCountry;
    private Toolbar toolbar;

    private RecyclerView recycler;
    private CountryAdapter countryAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countries_main);
        loadData();
        initToolbar();
        createRecycler();

    }

    private void loadData() {
        name = getIntent().getStringExtra("name_continent");
        arrayCountry = (ArrayList<Country>) getIntent().getSerializableExtra("countries_list");
    }

    private void createRecycler() {
        recycler = (RecyclerView) findViewById(R.id.countries_recycler);
        RecyclerView.LayoutManager layoutMenager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutMenager);
        recycler.setHasFixedSize(true);
        countryAdapter = new CountryAdapter(arrayCountry, this);
        recycler.setAdapter(countryAdapter);
        countryAdapter.notifyDataSetChanged();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}



