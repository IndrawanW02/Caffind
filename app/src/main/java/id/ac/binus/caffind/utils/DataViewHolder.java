package id.ac.binus.caffind.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.binus.caffind.R;

public class DataViewHolder extends RecyclerView.ViewHolder {
    ImageView img;
    TextView name, location, priceRange;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        this.img = itemView.findViewById(R.id.imgData);
        this.name = itemView.findViewById(R.id.txtName);
        this.location = itemView.findViewById(R.id.txtLocation);
        this.priceRange = itemView.findViewById(R.id.txtPrice);
    }
}
