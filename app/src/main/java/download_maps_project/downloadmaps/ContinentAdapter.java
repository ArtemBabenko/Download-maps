package download_maps_project.downloadmaps;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.R.id.message;
import static android.media.CamcorderProfile.get;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Continent continent = continent_list.get(position);
        holder.continent_name.setText(continent.getName());
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
}


