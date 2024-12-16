package id.ac.binus.caffind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.utils.DatabaseHelper;

public class Register extends AppCompatActivity {

    private EditText edtUsername, edtEmail, edtPassword, edtConfPassword;
    private Button registerBtn;
    private DatabaseHelper db;
    private TextView loginhyperlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfPassword = findViewById(R.id.edtConfPassword);
        registerBtn = findViewById(R.id.registerBtn);
        loginhyperlink = findViewById(R.id.loginHyperlink);

        db = new DatabaseHelper(this);

        // Register button click listener
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        loginhyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();
        String confPassword = edtConfPassword.getText().toString();

        if (validateInput(username, email, password, confPassword)) {
            if (db.registerNewAccount(username, email, password)) {
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            }

            // Navigate to the main/home activity
            Intent intent = new Intent(Register.this, CoffeeShopDetail.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validateInput(String username, String email, String password, String confPassword) {
        String validationResult = validateUsername(username) + validateEmail(email) + validatePassword(password) + validateConfirmationPassword(password, confPassword);

        return !validationResult.contains("REJECTED");
    }

    private String validateUsername(String username){
        String inputValidation = "ACCEPTED";

        if(TextUtils.isEmpty(username)) {
            edtUsername.setError("Username cannot be empty");
            edtUsername.requestFocus();
            inputValidation = "REJECTED";
        } else if(!db.isUsernameUnique(username)){
            edtUsername.setError("Username already exists");
            edtUsername.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }

    private String validateEmail(String email){
        String inputValidation = "ACCEPTED";

        if(TextUtils.isEmpty(email)){
            edtEmail.setError("Email cannot be empty");
            edtEmail.requestFocus();
            inputValidation = "REJECTED";
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Invalid email address");
            edtEmail.requestFocus();
            inputValidation = "REJECTED";
        } else if(!db.isEmailUnique(email)){
            edtEmail.setError("Email already exists");
            edtEmail.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }

    private String validatePassword(String password){
        String inputValidation = "ACCEPTED";

        if(TextUtils.isEmpty(password)){
            edtPassword.setError("Password cannot be empty");
            edtPassword.requestFocus();
            inputValidation = "REJECTED";
        } else if (password.length() < 8){
            edtPassword.setError("Password length must be at least 8 characters");
            edtPassword.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }

    private String validateConfirmationPassword(String password, String confPassword){
        String inputValidation = "ACCEPTED";

        if(TextUtils.isEmpty(password)){
            edtConfPassword.setError("Password cannot be empty");
            edtConfPassword.requestFocus();
            inputValidation = "REJECTED";
        } else if (!password.equals(confPassword)){
            edtConfPassword.setError("Passwords do not match");
            edtConfPassword.requestFocus();
            inputValidation = "REJECTED";
        }
        return inputValidation;
    }
}