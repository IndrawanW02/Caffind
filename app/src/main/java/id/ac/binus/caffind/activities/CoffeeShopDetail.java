package id.ac.binus.caffind.activities;

import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.models.CoffeeShopModel;
import id.ac.binus.caffind.utils.DatabaseHelper;

public class CoffeeShopDetail extends AppCompatActivity implements OnMapReadyCallback {
    CoffeeShopModel choosenCoffeeShop;

    CardView imgContainer, mapContainer;
    Button viewMapBtn;
    FrameLayout mapTemplate;
    ImageView coffeeShopImg;
    TextView coffeeShopName, coffeeShopLocation, coffeeShopOperationHours, coffeeShopPriceRange, coffeeShopDescription;

    GoogleMap map;
    Double locaitionLatitude, locationLongtitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_coffee_shop_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN);

//      Set coffeeShop image container size to be 65% height of the device height
//        imgContainer = findViewById(R.id.imgCard);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int screenHeight = displayMetrics.heightPixels;
//
//        ViewGroup.LayoutParams params = imgContainer.getLayoutParams();
//        params.height = (int) (screenHeight * 0.65);
//        imgContainer.setLayoutParams(params);

//      Set map initialization to when user click on the map
        mapContainer = findViewById(R.id.mapContainer);
        viewMapBtn = findViewById(R.id.viewMapBtn);
        mapTemplate = findViewById(R.id.map);

        viewMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSupportFragmentManager().findFragmentById(R.id.map) == null){
                    SupportMapFragment mapFragment = SupportMapFragment.newInstance();

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.map, mapFragment);
                    transaction.commit();

                    mapFragment.getMapAsync(CoffeeShopDetail.this);
                    viewMapBtn.setVisibility(View.GONE);
                }
            }
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        choosenCoffeeShop = databaseHelper.getCoffeeShopById(getIntent().getIntExtra("id", 0));

        coffeeShopImg = findViewById(R.id.imgData);
        coffeeShopName = findViewById(R.id.nameData);
        coffeeShopLocation = findViewById(R.id.locationData);
        coffeeShopOperationHours = findViewById(R.id.operationHoursData);
        coffeeShopPriceRange = findViewById(R.id.priceRangeData);
        coffeeShopDescription = findViewById(R.id.descriptionData);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapLocation);
//        mapFragment.getMapAsync(this);

        byte[] imgBuffer = choosenCoffeeShop.getImage();
        coffeeShopImg.setImageBitmap(BitmapFactory.decodeByteArray(imgBuffer, 0, imgBuffer.length));
        coffeeShopName.setText(choosenCoffeeShop.getName());
        coffeeShopOperationHours.setText(choosenCoffeeShop.getOperationHours());
        coffeeShopLocation.setText(choosenCoffeeShop.getLocation());
        coffeeShopPriceRange.setText(choosenCoffeeShop.getPriceRange());
        coffeeShopDescription.setText(choosenCoffeeShop.getDescription());

//      Convert address to latitude and longtitude coordinate for map pin location
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(choosenCoffeeShop.getLocation(), 1);

            if(addressList != null){
                Address address = addressList.get(0);

                locaitionLatitude = address.getLatitude();
                Log.d("LongituteCheck", String.valueOf(address.getLongitude()));

                locationLongtitude = address.getLongitude();
                Log.d("LatitudeCheck", String.valueOf(address.getLatitude()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng location = new LatLng(locaitionLatitude, locationLongtitude);
        googleMap.addMarker(new MarkerOptions().position(location).title(choosenCoffeeShop.getName()));
        googleMap.setPadding(0, 200, 0, 0);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

    }
}