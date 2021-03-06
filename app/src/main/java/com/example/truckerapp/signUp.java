package com.example.truckerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signUp extends AppCompatActivity {

    EditText Email, Password, Name, Phone ;
    Button SignUp;
    String NameHolder, EmailHolder, PasswordHolder, PhoneHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    databaseHelper dbHelp;
    Cursor cursor;
    String F_Result = "Not_Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignUp = (Button)findViewById(R.id.btnSignUp);

        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        Name = (EditText)findViewById(R.id.name);
        Phone = (EditText)findViewById(R.id.phone);

        dbHelp = new databaseHelper(this);

        // Adding click listener to register button.
        SignUp.setOnClickListener(view -> {
            SQLiteDataBaseBuild(); // Creating SQLite database if dose n't exists
            SQLiteTableBuild();  // Creating SQLite table if dose n't exists.
            CheckEditTextStatus();// Checking EditText is empty or Not.
            CheckingEmailAlreadyExistsOrNot();// Method to check Email is already exists or not.
            EmptyEditTextAfterDataInsert();   // Empty EditText After done inserting process.
        });

    }

    // SQLite database build method.
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(databaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + databaseHelper.TABLE_NAME + "(" + databaseHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + databaseHelper.Table_Column_1_Name + " VARCHAR, " + databaseHelper.Table_Column_2_Email + " VARCHAR, " + databaseHelper.Table_Column_3_Password + " VARCHAR, " + databaseHelper.Table_Column_4_Phone + " VARCHAR);");
    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){

        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {
            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+databaseHelper.TABLE_NAME+" (name,email,password) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"' , '"+PhoneHolder+"');";
            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(signUp.this," Registration Successfully", Toast.LENGTH_LONG).show();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(signUp.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }
    }


    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){

        Name.getText().clear();
        Email.getText().clear();
        Password.getText().clear();

    }



    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        NameHolder = Name.getText().toString() ;
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }
    }



    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        sqLiteDatabaseObj = dbHelp.getWritableDatabase();// Opening SQLite database write permission.

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(databaseHelper.TABLE_NAME, null, " " + databaseHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                F_Result = "Email Found"; // If Email is already exists then Result variable value set as Email Found.

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();
    }


    // Checking result
    public void CheckFinalResult(){

        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            // If email is exists then toast msg will display.
            Toast.makeText(signUp.this,"Email Already Exists",Toast.LENGTH_LONG).show();
        }
        else {
            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found" ;

    }

}