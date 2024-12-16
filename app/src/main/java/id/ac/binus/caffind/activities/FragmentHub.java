package id.ac.binus.caffind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.activities.fragments.FragmentAddShop;
import id.ac.binus.caffind.activities.fragments.FragmentCoffeeShopList;
import id.ac.binus.caffind.models.CoffeeShopModel;
import id.ac.binus.caffind.utils.ContentAdapter;
import id.ac.binus.caffind.utils.DatabaseHelper;

public class FragmentHub extends AppCompatActivity {
    private Window window;

    private FrameLayout frameLayout;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment_hub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        window.setNavigationBarColor(this.getResources().getColor(R.color.white));

        frameLayout = findViewById(R.id.fragmentContainer);
        tabLayout = findViewById(R.id.tabLayout);

        // set default fragment to be shown (Fragment: CoffeeShopList)
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentCoffeeShopList()).addToBackStack(null).commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()){
                    case 0:
                        fragment = new FragmentCoffeeShopList();
                        break;
                    case 1:
                        fragment = new FragmentAddShop();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}