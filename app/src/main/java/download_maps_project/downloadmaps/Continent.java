package download_maps_project.downloadmaps;

import java.util.ArrayList;

public class Continent {
    private String name;
    private ArrayList<Country> arrayCountry;

    public Continent(String name, ArrayList<Country> arrayCountry) {
        this.name = name;
        this.arrayCountry = arrayCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Country> getArrayCountry() {
        return arrayCountry;
    }

    public void setArrayCountry(ArrayList<Country> arrayCountry) {
        this.arrayCountry = arrayCountry;
    }
}
