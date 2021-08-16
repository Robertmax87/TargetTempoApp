package com.example.targettempo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.targettempo.databinding.ActivitySignupBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Signup extends AppCompatActivity {
    /**Here I am instantiating my variables. I could easily instantiate them as something else.
     * however I have decided to instantiate them as longer more complex variables that are very
     * obvious.
     */
    TextInputEditText Fullname, Email, Username, Password;
    TextView textView4;
    Button Register;
    FirebaseAuth fAuth;
    ProgressBar Loading;
    private AppBarConfiguration appBarConfiguration;
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        /** Here we know what our variables are. Using "findViewByID" we can locate what our
         * variables are supposed to represent.
         */
        Loading = findViewById(R.id.loadingBar);
        Fullname = findViewById(R.id.Fullname);
        Email = findViewById(R.id.Email);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        // textView4 = findViewById(R.id.textView4);
        Register = findViewById(R.id.chapo);
        fAuth =  FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),ScheduleInput.class));
            finish();
        }
        Register.setOnClickListener(new View.OnClickListener() {
            /**
             * This is where the button's functionality goes. Here we are able to make sure that
             * @param v
             */
            @Override
            public void onClick(View v) {
                String fullname, email, username, password;
                fullname = String.valueOf(Fullname.getText());
                email = String.valueOf(Email.getText());
                username = String.valueOf(Username.getText());
                password = String.valueOf(Password.getText());
                if(username.isEmpty() || username.length()<6){
                    Username.setError("Username must be filled and Longer than 6 characters");
                }
                if(password.isEmpty() || password.length() < 6){
                    Password.setError("Password must be filled out with a length longer than 6 characters");
                }
                Loading.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signup.this,"User Added",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ScheduleInput.class));
                        }
                        else{
                            Toast.makeText(Signup.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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