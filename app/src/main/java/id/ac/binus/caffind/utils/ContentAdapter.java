package id.ac.binus.caffind.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.models.CoffeeShopModel;

public class ContentAdapter extends BaseAdapter {
    Context ctx;
    List<CoffeeShopModel> coffeeSpotList;
    LayoutInflater inflater;

    public ContentAdapter(Context ctx, List<CoffeeShopModel> coffeeSpotList) {
        this.ctx = ctx;
        this.coffeeSpotList = coffeeSpotList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return coffeeSpotList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.coffee_shop_item, null);
        ImageView image = view.findViewById(R.id.imgData);
        TextView name = view.findViewById(R.id.txtName);
        TextView location = view.findViewById(R.id.txtLocation);
        TextView priceRange = view.findViewById(R.id.txtPrice);

        image.setImageResource(coffeeSpotList.get(i).getImage());
        name.setText(coffeeSpotList.get(i).getName());
        location.setText(coffeeSpotList.get(i).getLocation());
        priceRange.setText(coffeeSpotList.get(i).getPriceRange());

        return view;
    }
}
