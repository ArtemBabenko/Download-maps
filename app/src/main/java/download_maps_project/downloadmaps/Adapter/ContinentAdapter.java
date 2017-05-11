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

import download_maps_project.downloadmaps.MainCountries;
import download_maps_project.downloadmaps.Models.Continent;
import download_maps_project.downloadmaps.Models.Country;
import download_maps_project.downloadmaps.R;

public class ContinentAdapter extends RecyclerView.Adapter<ContinentAdapter.ViewHolder> {

    private Context context;

    private List<Continent> continent_list;

    public ContinentAdapter(List<Continent> continent_list, Context context) {

        this.continent_list = continent_list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.region, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Continent continent = continent_list.get(position);
        holder.continent_name.setText(continent.getName());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String name_continent = continent.getName();
                ArrayList<Country> countries_list = continent.getArrayCountry();

                if (continent.getArrayCountry().size() == 0) {
                    Toast.makeText(context, "The list is empty", Toast.LENGTH_SHORT).show();
                } else {
                    continentNameCheck(holder, v, name_continent, countries_list);
                }
            }

        });

    }


    @Override
    public int getItemCount() {
        return continent_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView globe;
        TextView continent_name;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            continent_name = (TextView) itemView.findViewById(R.id.name_continent);
            globe = (ImageView) itemView.findViewById(R.id.globe);
            cv = (CardView) itemView.findViewById(R.id.continent_cv);
        }
    }

    private void continentNameCheck(ViewHolder holder, View v, String name_continent, ArrayList<Country> countries_list) {
        Intent intent = new Intent(v.getContext(), MainCountries.class);
        intent.putExtra("name_continent", name_continent);
        intent.putExtra("countries_list", countries_list);
        v.getContext().startActivity(intent);
    }
}


