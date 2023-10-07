package com.example.navigation_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class SignUp extends AppCompatActivity{

    Button signupbtn;
    Spinner dropdown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);
        //get the spinner from the xml.
        dropdown = (Spinner) findViewById(R.id.reg_userrole);
        signupbtn = findViewById(R.id.signupbtn);

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.reg_userrole));
        //set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(SignUp.this, Login.class);
                startActivity(myintent);
                //setContentView(R.layout.login);
            }
        });
    }
}
