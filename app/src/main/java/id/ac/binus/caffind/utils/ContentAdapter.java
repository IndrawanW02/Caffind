package id.ac.binus.caffind.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.models.CoffeeShopModel;

public class ContentAdapter extends RecyclerView.Adapter<DataViewHolder> {
    Context ctx;
    List<CoffeeShopModel> coffeeShopList;
    AdapterView.OnItemClickListener listener;

    public ContentAdapter(Context ctx, List<CoffeeShopModel> coffeeShopList, AdapterView.OnItemClickListener listener) {
        this.ctx = ctx;
        this.coffeeShopList = coffeeShopList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(LayoutInflater.from(ctx).inflate(R.layout.coffee_shop_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        byte[] imgBuffer = coffeeShopList.get(position).getImage();

        holder.img.setImageBitmap(BitmapFactory.decodeByteArray(imgBuffer, 0, imgBuffer.length));
        holder.name.setText(coffeeShopList.get(position).getName());
        holder.location.setText(coffeeShopList.get(position).getLocation());
        holder.priceRange.setText(coffeeShopList.get(position).getPriceRange());

        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onItemClick(null, v, position, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coffeeShopList.size();
    }
}
