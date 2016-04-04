package samples.exoguru.materialtabs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.models.CategoryResult;
import samples.exoguru.materialtabs.models.SongStyleModel;
import samples.exoguru.materialtabs.servies.ItemClickListener;

/**
 * Created by Windows on 31/03/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private CategoryResult categoryResults;
    private int rowLayout;
    private Context mContext;

    public RecyclerViewAdapter(CategoryResult categoryResults, int rowLayout, Context context) {
        this.categoryResults = categoryResults;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.textViewTrackName.setText(categoryResults.getResults().get(i).getTrackName().toString());
        viewHolder.textViewArtistName.setText(categoryResults.getResults().get(i).getArtistName().toString());
        //NEED TO CHECK IS DATA EXIST IN THE SERVER!!! Otherwise app might crash!!!
        if(categoryResults.getResults().get(i).getCollectionPrice() == null) {
            viewHolder.textViewCollectionPrice.setText("null");
        }
        else{viewHolder.textViewCollectionPrice.setText(categoryResults.getResults().get(i).getCollectionPrice().toString() + " $");}


        //viewHolder.countryImage.setImageResource(categoryResult.getImage());
        Picasso.with(mContext)
                .load(categoryResults.getResults().get(i).getArtworkUrl100())
                .into(viewHolder.imageView);
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(mContext, "#" + position + " - " + categoryResults.getResults().get(position).getTrackName() + " (Long click)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "#" + position + " - " + categoryResults.getResults().get(position).getTrackName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryResults == null ? 0 : categoryResults.getResultCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView textViewTrackName;
        public TextView textViewArtistName;
        public TextView textViewCollectionPrice;
        public ImageView imageView;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewTrackName = (TextView) itemView.findViewById(R.id.trackName);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            textViewArtistName = (TextView) itemView.findViewById(R.id.artistName);
            textViewCollectionPrice = (TextView) itemView.findViewById(R.id.collectionPrice);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}