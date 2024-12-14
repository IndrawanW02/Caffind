package id.ac.binus.caffind.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.models.CoffeeShopModel;
import id.ac.binus.caffind.utils.DatabaseHelper;

public class CoffeeShopDetail extends AppCompatActivity {
    CoffeeShopModel choosenCoffeeShop;

    ImageView coffeeShopImg;
    TextView coffeeShopName, coffeeShopLocation, coffeeShopOperationHours, coffeeShopPriceRange, coffeeShopDescription;

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

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        choosenCoffeeShop = databaseHelper.getCoffeeShopById(getIntent().getIntExtra("id", 0));

        coffeeShopImg = findViewById(R.id.imgData);
        coffeeShopName = findViewById(R.id.nameData);
        coffeeShopLocation = findViewById(R.id.locationData);
        coffeeShopOperationHours = findViewById(R.id.operationHoursData);
        coffeeShopPriceRange = findViewById(R.id.priceRangeData);
        coffeeShopDescription = findViewById(R.id.descriptionData);

        coffeeShopImg.setImageResource(choosenCoffeeShop.getImage());
        coffeeShopName.setText(choosenCoffeeShop.getName());
        coffeeShopOperationHours.setText(choosenCoffeeShop.getOperationHours());
        coffeeShopLocation.setText(choosenCoffeeShop.getLocation());
        coffeeShopPriceRange.setText(choosenCoffeeShop.getPriceRange());
        coffeeShopDescription.setText(choosenCoffeeShop.getDescription());
    }
}