package download_maps_project.downloadmaps;

import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import download_maps_project.downloadmaps.Adapter.ContinentAdapter;
import download_maps_project.downloadmaps.Models.City;
import download_maps_project.downloadmaps.Models.Continent;
import download_maps_project.downloadmaps.Models.Country;

import static download_maps_project.downloadmaps.R.string.app_name;


public class MainContinent extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    private Toolbar toolbar;

    private ProgressBar progressBar;

    private TextView freeSize;
    private String tmp = " ";
    private String name_Continent;
    private String name_Country;
    private String name_City;
    private ArrayList<Continent> arrayContinent = new ArrayList<>();
    private ArrayList<Country> arrayCountry;
    private ArrayList<City> arrayCity;
    private Continent continent;
    private Country country;
    private City city;

    private RecyclerView recycler;
    private ContinentAdapter continentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.pb_horizontal);
        freeSize = (TextView) findViewById(R.id.devise_text_freeSize);
        recycler = (RecyclerView) findViewById(R.id.continent_recycler);
        initToolbar();
        takeMemory();
        takeFreeMemory();
        postProggres();
        prepareXpp();
        parser();
        createRecycler();
    }

    private long takeMemory() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesMemory = (long) stat.getBlockSize() * (long) stat.getBlockCount();
        long megMemory = bytesMemory / 1048576;
        return megMemory;
    }


    private long takeFreeMemory() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesFreeMemory = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        long megFreeMemory = bytesFreeMemory / 1048576;
        freeSize.setText("Free " + customFormat("###,###.###", megFreeMemory) + " GB");
        return megFreeMemory;
    }

    private void postProggres() {
        progressBar.setMax((int) takeMemory());
        if ((int) takeFreeMemory() == 0) {
            progressBar.setSecondaryProgress(0);
        } else {
            progressBar.setSecondaryProgress((int) takeMemory() - (int) takeFreeMemory());
        }
    }

    private String customFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        String arr[] = output.split(",");
        output = arr[0] + "." + arr[1];
        System.out.println(value + "  " + pattern + "  " + output);
        return output;
    }

    public void parser() {
        try {
            XmlPullParser xpp = prepareXpp();
            int depth = 0;

            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    // start document
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // start tag
                    case XmlPullParser.START_TAG:
                        tmp = "";
                        depth++;

                        Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                                + ", depth = " + xpp.getDepth() + ", attrCount = "
                                + xpp.getAttributeCount());
                        for (int i = 0; i < xpp.getAttributeCount(); i++) {
                            tmp = tmp + xpp.getAttributeName(i) + " = " + xpp.getAttributeValue(i) + ", ";
                            if (xpp.getAttributeName(i).equals("name") && depth == 2) {
                                name_Continent = xpp.getAttributeValue(i);
                            } else if (xpp.getAttributeName(i).equals("name") && depth == 3) {
                                name_Country = xpp.getAttributeValue(i);
                            } else if (xpp.getAttributeName(i).equals("name") && depth == 4) {
                                name_City = xpp.getAttributeValue(i);
                            }
                        }

                        if (depth == 1) {
                            System.out.println("Open ContList");
                        } else if (depth == 2) {
                            System.out.println("Open Continent");
                            arrayCountry = new ArrayList<>();
                            arrayCountry.clear();
                        } else if (depth == 3) {
                            System.out.println("Open country");
                            arrayCity = new ArrayList<>();
                            arrayCity.clear();
                        } else if (depth == 4) {
                            System.out.println("Open City");
                        }

                        break;
                    // end tag
                    case XmlPullParser.END_TAG:

                        if (depth == 1) {
                            System.out.println("Close ContList");
                        } else if (depth == 2) {
                            System.out.println("Close Continent");
                            System.out.println("Size arrayCountry in finish Continent " + arrayCountry.size());
                            continent = new Continent(name_Continent, arrayCountry);
                            arrayContinent.add(continent);
                        } else if (depth == 3) {
                            System.out.println("Close Country");
                            country = new Country(name_Country, arrayCity);
                            arrayCountry.add(country);
                        } else if (depth == 4) {
                            System.out.println("Close City");
                            city = new City(name_City, "yes");
                            arrayCity.add(city);
                        }

                        depth--;
                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                        break;
                    // content tag
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + xpp.getText());
                        break;

                    default:
                        break;
                }
                // next element
                xpp.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    XmlPullParser prepareXpp() {
        return getResources().getXml(R.xml.region_data);
    }

    private void createRecycler() {
        recycler = (RecyclerView) findViewById(R.id.continent_recycler);
        RecyclerView.LayoutManager layoutMenager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutMenager);
        recycler.setHasFixedSize(true);
        continentAdapter = new ContinentAdapter(arrayContinent, this);
        recycler.setAdapter(continentAdapter);
        continentAdapter.notifyDataSetChanged();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(app_name);
    }

}

