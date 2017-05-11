package download_maps_project.downloadmaps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import download_maps_project.downloadmaps.MainCity;
import download_maps_project.downloadmaps.Models.City;
import download_maps_project.downloadmaps.Models.Country;
import download_maps_project.downloadmaps.R;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private Context context;

    private List<Country> country_list;

    public CountryAdapter(List<Country> country_list, Context context) {

        this.country_list = country_list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Country country = country_list.get(position);
        holder.country_name.setText(country.getName());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String name_country = country.getName();
                ArrayList<City> city_list = country.getArrayCity();

                if (country.getArrayCity().size() == 0) {
                    Toast.makeText(context, "The list is empty", Toast.LENGTH_SHORT).show();
                } else {
                    countryNameCheck(holder, v, name_country, city_list);
                }
            }

        });
    }


    @Override
    public int getItemCount() {
        return country_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardIcon;
        TextView country_name;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            country_name = (TextView) itemView.findViewById(R.id.name_country);
            cardIcon = (ImageView) itemView.findViewById(R.id.map);
            cv = (CardView) itemView.findViewById(R.id.country_cv);
        }
    }

    private void countryNameCheck(ViewHolder holder, View v, String name_country, ArrayList<City> city_list) {
        Intent intent = new Intent(v.getContext(), MainCity.class);
        intent.putExtra("name_country", name_country);
        intent.putExtra("city_list", city_list);
        v.getContext().startActivity(intent);
    }
}
