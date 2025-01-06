package id.ac.binus.caffind.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.activities.fragments.FragmentAddShop;
import id.ac.binus.caffind.activities.fragments.FragmentCoffeeShopList;
import id.ac.binus.caffind.activities.fragments.FragmentProfile;
import id.ac.binus.caffind.activities.fragments.FragmentUnauthenticated;
import id.ac.binus.caffind.models.UserModel;

public class FragmentHub extends AppCompatActivity {

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

        if (savedInstanceState == null) {
            // set default fragment to be shown (Fragment: CoffeeShopList)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new FragmentCoffeeShopList())
                    .commit();
        }

        // Get current logged in user
        UserModel currUser = getUserFromPreferences();

        // Initialize views and listener
        FrameLayout frameLayout = findViewById(R.id.fragmentContainer);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = new FragmentCoffeeShopList();
                switch (tab.getPosition()){
                    case 0:
                        fragment = new FragmentCoffeeShopList();
                        break;
                    case 1:
                        // Check if the user has logged in or not
                        if(currUser == null){
                            fragment = new FragmentUnauthenticated("Oops! Only registered users can add a new coffee spot.");
                        } else{
                            fragment = new FragmentAddShop();
                        }
                        break;
                    case 2:
                        // Check if the user has logged in or not
                        if(currUser == null){
                            fragment = new FragmentUnauthenticated("Profile access is restricted to registered users only.");
                        } else{
                            fragment = new FragmentProfile(currUser);
                        }
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

    public UserModel getUserFromPreferences() {
        SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = preferences.getString("user", null); // Fetch user JSON data from SharedPreferences

        if (json != null) {
            return gson.fromJson(json, UserModel.class); // Convert user JSON data
        }

        return null; // Return null if the data is not found
    }
}