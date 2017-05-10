package download_maps_project.downloadmaps;

import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    private ProgressBar progressBar;
    private TextView freeSize;
    private String tmp = " ";
    private String name_Split;
    private String name_Continent;
    private String name_Country;
    private String name_City;
    private ArrayList<Continent> arrayContinent = new ArrayList<>();
    private ArrayList<Country> arrayCountry = new ArrayList<>();
    private ArrayList<City> arrayCity = new ArrayList<>();
    private Continent continent;
    private Country country;
    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.pb_horizontal);
        freeSize = (TextView) findViewById(R.id.devise_text_freeSize);
        takeMemory();
        takeFreeMemory();
        postProggres();
        prepareXpp();

        parser();
        System.out.println(arrayContinent.size());
        System.out.println(arrayCountry.size());
        for (Continent continent : arrayContinent) {
            System.out.println(continent.getName() + " : " + continent.getArrayCountry().size());

        }
//        inputMap();
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
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
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
                            }
                            if (xpp.getAttributeName(i).equals("name") && depth == 3) {
                                name_Country = xpp.getAttributeValue(i);
                            }
                        }

                        if (depth == 1) {
                            System.out.println("Open ContList");
                        } else if (depth == 2) {
                            System.out.println("Open Continent");
                            arrayCountry.clear();
                        } else if (depth == 3) {
                            System.out.println("Open country");
                            System.out.println("Size arrayCountry in start Continent " + arrayCountry.size());
                        }

                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        if (depth == 1) {
                            System.out.println("Close ContList");
                        } else if (depth == 2) {
                            System.out.println("Close Continent");
                            System.out.println("Size arrayCountry in finish Continent " + arrayCountry.size());
                            /*
                            Wtf! Когда проверяю на размер списка, то он есть. Добавляю в объект и вытаскиваю, то он равен 0.
                             */
                            arrayContinent.add(new Continent(name_Continent, arrayCountry));
                        } else if (depth == 3) {
                            System.out.println("Close Country");
                            country = new Country(name_Country, arrayCity);
                            arrayCountry.add(country);
                        }
                        depth--;
                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + xpp.getText());
                        break;

                    default:
                        break;
                }
                // следующий элемент
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

}

