package com.example.targettempo;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.targettempo.databinding.ActivitySignupBinding;
import com.google.android.material.textfield.TextInputEditText;

public class Signup extends AppCompatActivity {
    /**Here I am instantiating my variables. I could easily instantiate them as something else.
     * however I have decided to instantiate them as longer more complex variables that are very
     * obvious.
     */
    TextInputEditText textInputEditTextFullname, textInputEditTextEmail, textInputEditTextUsername, textInputEditTextPassword;
    TextView textView4;
    Button chapo;
    private AppBarConfiguration appBarConfiguration;
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        /** Here we know what our variables are. Using "findViewByID" we can locate what our
         * variables are supposed to represent.
         */
        textInputEditTextFullname = findViewById(R.id.Fullname);
        textInputEditTextEmail = findViewById(R.id.Email);
        textInputEditTextUsername = findViewById(R.id.Username);
        textInputEditTextPassword = findViewById(R.id.Password);
        // textView4 = findViewById(R.id.textView4);
        chapo = findViewById(R.id.chapo);
        chapo.setOnClickListener(new View.OnClickListener() {
            /**
             * This is where the button's functionality goes. Here we are able to make sure that
             * @param v
             */
            @Override
            public void onClick(View v) {
                String fullname, email, username, password;
                fullname = String.valueOf(textInputEditTextFullname.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                if(!fullname.equals("") && !email.equals("") && !username.equals("") && !password.equals("")) {


                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            /**I put my data into two string arrays.
                             *
                             */

                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "email";
                            field[2] = "username";
                            field[3] = "password";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = email;
                            data[3] = password;
                            // PutData putData = new PutData("https://projects.vishnusivadas.com/AdvancedHttpURLConnection/putDataTest.php", "POST", field, data);
                            PutData putData = new PutData("http://10.0.0.170/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All Fields Must Be Filled Out", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}