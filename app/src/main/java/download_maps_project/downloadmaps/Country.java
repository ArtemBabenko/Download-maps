package download_maps_project.downloadmaps;

import java.util.ArrayList;

public class Country {
    private String name;
    private ArrayList<City> arrayCity;

    public Country(String name, ArrayList<City> arrayCity) {
        this.name = name;
        this.arrayCity = arrayCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<City> getArrayCity() {
        return arrayCity;
    }

    public void setArrayCity(ArrayList<City> arrayCity) {
        this.arrayCity = arrayCity;
    }
}
