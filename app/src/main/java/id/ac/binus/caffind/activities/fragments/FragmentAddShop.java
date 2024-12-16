package id.ac.binus.caffind.activities.fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.utils.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddShop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddShop extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseHelper db;

    private int openHour, openMinute, closeHour, closeMinute;
    private byte[] selectedImage;

    private Button selectImageBtn;
    private ImageView imagePreview;
    private EditText shopNameInput, shopDescriptionInput, shopAddressInput, openingTimeInput, closingTimeInput, minPriceInput, maxPriceInput;
    private Button submitBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int REQUEST_CODE_GALLERY = 1000;

    public FragmentAddShop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAddShop.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAddShop newInstance(String param1, String param2) {
        FragmentAddShop fragment = new FragmentAddShop();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_shop, container, false);

        imagePreview = view.findViewById(R.id.imageView);
        selectImageBtn = view.findViewById(R.id.selectImageButton);
        // set default selected image
        selectedImage = getDefaultImageAsByteArray(getContext());

        // selected image preview
        ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        // set the chosen image to image view and save it to a temporary variable
                        imagePreview.setImageURI(selectedImageUri);

                        selectedImage = getImageByteArray(selectedImageUri);
                    }
                }
        );

        // select image to upload
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                galleryLauncher.launch(intent);
            }
        });

        // opening hour input
        openingTimeInput = view.findViewById(R.id.edtOpenTime);
        openingTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                openHour = i;
                                openMinute = i1;

                                String time = openHour + ":" + openMinute;

                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                    openingTimeInput.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, 12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(openHour, openMinute);
                timePickerDialog.show();
            }
        });

        // closing hour input
        closingTimeInput = view.findViewById(R.id.edtCloseTime);
        closingTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                closeHour = i;
                                closeMinute = i1;

                                String time = closeHour + ":" + closeMinute;

                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                    closingTimeInput.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, 12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(closeHour, closeMinute);
                timePickerDialog.show();
            }
        });

        shopNameInput = view.findViewById(R.id.edtName);
        shopDescriptionInput = view.findViewById(R.id.edtDesc);
        shopAddressInput = view.findViewById(R.id.edtAddress);
        minPriceInput = view.findViewById(R.id.edtMinPrice);
        maxPriceInput = view.findViewById(R.id.edtMaxPrice);

        submitBtn = view.findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = shopNameInput.getText().toString().trim();
                String description = shopDescriptionInput.getText().toString().trim();
                String address = shopAddressInput.getText().toString().trim();
                String openHour = openingTimeInput.getText().toString();
                String closeHour = closingTimeInput.getText().toString();
                String minPrice = minPriceInput.getText().toString().trim();
                String maxPrice = maxPriceInput.getText().toString().trim();

                if(validateInput(name, description, address, openHour, closeHour, minPrice, maxPrice)){
                    db = new DatabaseHelper(getContext());

                    // format operational Hours
                    String operationHours = openHour + " - " + closeHour;

                    // format price range
                    NumberFormat formatter = NumberFormat.getInstance(new Locale("id", "ID"));
                    String menuPriceRange = "Rp" + formatter.format(Integer.parseInt(minPrice)) + " - " + "Rp" + formatter.format(Integer.parseInt(maxPrice));

                    db.addNewCoffeeSpot(name, description, address, operationHours, menuPriceRange, selectedImage);

                    resetFormInput();
                    Toast.makeText(getContext(), "New Coffee Spot Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean validateInput(String name, String description, String address, String openHour, String closeHour, String minPrice, String maxPrice){
        String validationResult = validateName(name) + validateDescription(description) + validateAddress(address) + validateOperationalHours(openHour, closeHour) + validatePriceRange(minPrice, maxPrice);

        return !validationResult.contains("REJECTED");
    }

    private String validateName(String name){
        String inputValidation = "ACCEPTED";

        if(TextUtils.isEmpty(name)) {
            shopNameInput.setError("Name cannot be empty");
            shopNameInput.requestFocus();
            inputValidation = "REJECTED";
        } else if(name.length() > 45){
            shopNameInput.setError("Name length must be within 45 characters");
            shopNameInput.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }

    private String validateDescription(String description){
        String inputValidation = "ACCEPTED";

        if(description.length() > 300){
            shopDescriptionInput.setError("Description length must be within 300 characters");
            shopDescriptionInput.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }

    private String validateAddress(String address){
        String inputValidation = "ACCEPTED";

        if(TextUtils.isEmpty(address)){
            shopAddressInput.setError("Address cannot be empty");
            shopAddressInput.requestFocus();
            inputValidation = "REJECTED";
        }
        else if(address.length() > 150){
            shopAddressInput.setError("Address length must be within 150 characters");
            shopAddressInput.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }

    private String validateOperationalHours(String openHour, String closeHour){
        String inputValidation = "ACCEPTED";

        if(TextUtils.isEmpty(openHour)){
            openingTimeInput.setError("Opening time cannot be empty");
            openingTimeInput.requestFocus();
            inputValidation = "REJECTED";
        } else {
            openingTimeInput.setError(null);
        }

        if(TextUtils.isEmpty(closeHour)){
            closingTimeInput.setError("Closing time cannot be empty");
            closingTimeInput.requestFocus();
            inputValidation = "REJECTED";
        } else {
            closingTimeInput.setError(null);
        }
        return inputValidation;
    }

    private String validatePriceRange(String minPrice, String maxPrice){
        String inputValidation = "ACCEPTED";

        if(TextUtils.isEmpty(minPrice)){
            minPriceInput.setError("Minimum price menu cannot be empty");
            minPriceInput.requestFocus();
            inputValidation = "REJECTED";
        }

        if(TextUtils.isEmpty(maxPrice)){
            maxPriceInput.setError("Maximum price menu cannot be empty");
            maxPriceInput.requestFocus();
            inputValidation = "REJECTED";
        } else if (Integer.parseInt(maxPrice) < Integer.parseInt(minPrice)){
            maxPriceInput.setError("Maximum price menu cannot be lower than the minimum price menu");
            maxPriceInput.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }

    public void resetFormInput(){
        imagePreview.setImageBitmap(null);
        shopNameInput.setText("");
        shopDescriptionInput.setText("");
        shopAddressInput.setText("");
        openingTimeInput.setText("");
        closingTimeInput.setText("");
        minPriceInput.setText("");
        maxPriceInput.setText("");
    }

    public byte[] getImageByteArray(Uri imageUri) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            inputStream = getActivity().getContentResolver().openInputStream(imageUri);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public byte[] getDefaultImageAsByteArray(Context context) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.default_background);

        // Convert drawable to bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        // Convert bitmap to byte[]
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}