package com.example.truckerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.google.android.material.textfield.TextInputLayout;

public class loginScreen extends AppCompatActivity {

    Button SignUp;
    TextView forgot;
    TextInputLayout Email, Password;
    Button login_btn;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    databaseHelper dbHelp;
    Cursor cursor;
    String TempPassword = "NOT_FOUND";
    public static final String UserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        //Hooks
        SignUp = findViewById(R.id.btnSignUp);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        forgot = findViewById(R.id.btnforgot);


        dbHelp = new databaseHelper(this);

        //Adding click listener to log in button.
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextStatus(); // Calling EditText is empty or no method.

                // Calling login method.
                LoginFunction();

            }
        });


        // Adding click listener to register button.
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(loginScreen.this, signUp.class);
                startActivity(intent);

            }
        });

    }


    // Login function starts from here.
    private void LoginFunction() {
        //If any of login EditText empty then this block will be executed.
        if (EditTextEmptyHolder) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = dbHelp.getWritableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(databaseHelper.TABLE_NAME, null, " " + databaseHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(databaseHelper.Table_Column_3_Password));

                    cursor.close();
                }
            }
            CheckFinalResult();

        } else {
            //  Toast.makeText(databaseHelper.this, "Please Enter UserName or Password.", Toast.LENGTH_LONG).show();
            Toast.makeText(loginScreen.this, "Please Enter Email or Password", Toast.LENGTH_SHORT).show();
        }

    }


    // Checking EditText is empty or not.
    public void CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getEditText().toString();
        PasswordHolder = Password.getEditText().toString();

        // Checking EditText is empty or no using TextUtils.
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            EditTextEmptyHolder = false;

        } else {

            EditTextEmptyHolder = true;
        }
    }



        // Checking entered password from SQLite database email associated password.
    private void CheckFinalResult() {

                if (TempPassword.equalsIgnoreCase(PasswordHolder)) {

                    Toast.makeText(loginScreen.this, "Login Successful", Toast.LENGTH_LONG).show();

                    // Going to Dashboard activity after login success message.
                    Intent intent = new Intent(loginScreen.this, dashboard.class);

                    // Sending Email to Dashboard Activity using intent.
                    intent.putExtra(UserEmail, EmailHolder);

                    startActivity(intent);


                } else {

                    Toast.makeText(loginScreen.this, "UserName or Password is Wrong, Please Try Again.", Toast.LENGTH_LONG).show();

                }
                TempPassword = "NOT_FOUND";

            }
}


