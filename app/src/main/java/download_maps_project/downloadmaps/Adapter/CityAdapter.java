package download_maps_project.downloadmaps.Adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import download_maps_project.downloadmaps.Models.City;
import download_maps_project.downloadmaps.R;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private Context context;

    private List<City> city_list;

    public CityAdapter(List<City> city_list, Context context) {

        this.city_list = city_list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final City city = city_list.get(position);
        holder.city_name.setText(city.getName());
    }


    @Override
    public int getItemCount() {
        return city_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardIcon;
        TextView city_name;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            city_name = (TextView) itemView.findViewById(R.id.name_city);
            cardIcon = (ImageView) itemView.findViewById(R.id.map_city);
            cv = (CardView) itemView.findViewById(R.id.city_cv);
        }
    }
}
