package id.ac.binus.caffind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.binus.caffind.models.CoffeeShopModel;
import id.ac.binus.caffind.R;
import id.ac.binus.caffind.utils.ContentAdapter;
import id.ac.binus.caffind.utils.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    Window window;
    List<CoffeeShopModel> coffeeSpotData;
//    ListView coffeeSpotList;
    RecyclerView coffeeSpotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        window.setNavigationBarColor(this.getResources().getColor(R.color.white));

//        getApplicationContext().deleteDatabase("Caffind");
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
//        databaseHelper.registerNewAccount("Indra", "indra@binus.ac.id", "123");

//        Intent registerPage = new Intent(MainActivity.this, Register.class);
//        startActivity(registerPage);

        coffeeSpotData = new ArrayList<>();
        coffeeSpotData = databaseHelper.getCoffeeShopList();
//
//        ContentAdapter adapter = new ContentAdapter(getApplicationContext(), coffeeSpotData);
//
        coffeeSpotList = findViewById(R.id.coffeeSpotList);
        coffeeSpotList.setLayoutManager(new LinearLayoutManager(this));

        ContentAdapter adapter = new ContentAdapter(getApplicationContext(), coffeeSpotData, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent coffeeSpotDetailPage = new Intent(MainActivity.this, CoffeeShopDetail.class);

                coffeeSpotDetailPage.putExtra("id", coffeeSpotData.get(i).getId());
                startActivity(coffeeSpotDetailPage);
            }
        });

        coffeeSpotList.setAdapter(adapter);
//        coffeeSpotList.setAdapter(adapter);
//
//        coffeeSpotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent coffeeSpotDetailPage = new Intent(MainActivity.this, CoffeeShopDetail.class);
//
//                coffeeSpotDetailPage.putExtra("id", coffeeSpotData.get(i).getId());
//                startActivity(coffeeSpotDetailPage);
//            }
//        });
    }

}