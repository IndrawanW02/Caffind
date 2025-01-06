package id.ac.binus.caffind.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.activities.CoffeeShopDetail;
import id.ac.binus.caffind.models.CoffeeShopModel;
import id.ac.binus.caffind.utils.ContentAdapter;
import id.ac.binus.caffind.utils.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCoffeeShopList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCoffeeShopList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<CoffeeShopModel> coffeeSpotData;
    private RecyclerView coffeeSpotList;
    private DatabaseHelper databaseHelper;
    private ContentAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCoffeeShopList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCoffeeShopList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCoffeeShopList newInstance(String param1, String param2) {
        FragmentCoffeeShopList fragment = new FragmentCoffeeShopList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coffee_shop_list, container, false);

        //  Initialize Database Helper
        databaseHelper = new DatabaseHelper(getContext());

        // Get Coffee Spot Data
        coffeeSpotData = new ArrayList<>();
        coffeeSpotData = databaseHelper.getCoffeeShopList();

        // Initialize RecyclerView
        coffeeSpotList = view.findViewById(R.id.coffeeSpotList);
        coffeeSpotList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the Adapter
        adapter = new ContentAdapter(getContext(), coffeeSpotData, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Intent to CoffeeShopDetail page
                Intent coffeeSpotDetailPage = new Intent(getContext(), CoffeeShopDetail.class);
                coffeeSpotDetailPage.putExtra("id", coffeeSpotData.get(position).getId());
                startActivity(coffeeSpotDetailPage);
            }
        });

        // Set the adapter to the RecyclerView
        coffeeSpotList.setAdapter(adapter);

        return view;
    }
}