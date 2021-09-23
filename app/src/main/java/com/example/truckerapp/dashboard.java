package com.example.truckerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.view.View;

public class dashboard extends AppCompatActivity implements View.OnClickListener{

    public CardView fuel,transaction,youractivity,messaging;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fuel = (CardView) findViewById(R.id.fE);
        transaction = (CardView) findViewById(R.id.tran);
        youractivity = (CardView) findViewById(R.id.yourAct);
        messaging = (CardView) findViewById(R.id.message);

        fuel.setOnClickListener(this);
        transaction.setOnClickListener(this);
        youractivity.setOnClickListener(this);
        messaging.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent i;

        switch (v.getId()) {
            case  R.id.fE :
                i = new Intent(this,fuelEfficiency.class);
                startActivity(i);
                break;
            case  R.id.tran:
                i = new Intent(this,Transaction.class);
                startActivity(i);
                break;
            case  R.id.showAct:
                i = new Intent(this,showActivity.class);
                startActivity(i);
                break;
            case  R.id.message:
                i = new Intent(this,messaging.class);
                startActivity(i);
                break;
            default:
                break;
        }

    }
}