package id.ac.binus.caffind.utils;

import android.annotation.SuppressLint;
import android.content.Context;
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
        holder.img.setImageResource(coffeeShopList.get(position).getImage());
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

//    public ContentAdapter(Context ctx, List<CoffeeShopModel> coffeeSpotList) {
//        this.ctx = ctx;
//        this.coffeeSpotList = coffeeSpotList;
//        inflater = LayoutInflater.from(ctx);
//    }
//
//    @Override
//    public int getCount() {
//        return coffeeSpotList.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @SuppressLint("ViewHolder")
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        view = inflater.inflate(R.layout.coffee_shop_item, null);
//        ImageView image = view.findViewById(R.id.imgData);
//        TextView name = view.findViewById(R.id.txtName);
//        TextView location = view.findViewById(R.id.txtLocation);
//        TextView priceRange = view.findViewById(R.id.txtPrice);
//
//        image.setImageResource(coffeeSpotList.get(i).getImage());
//        name.setText(coffeeSpotList.get(i).getName());
//        location.setText(coffeeSpotList.get(i).getLocation());
//        priceRange.setText(coffeeSpotList.get(i).getPriceRange());
//
//        return view;
//    }
}
