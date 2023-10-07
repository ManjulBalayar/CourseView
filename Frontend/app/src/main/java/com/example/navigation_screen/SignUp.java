package com.example.navigation_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class SignUp extends AppCompatActivity {

    Button signupbtn;
    Spinner dropdown;
    EditText reg_username, reg_password, reg_email, reg_userid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);
        //get the spinner from the xml.
        dropdown = (Spinner) findViewById(R.id.reg_userrole);
        signupbtn = findViewById(R.id.signupbtn);
        reg_username = findViewById(R.id.reg_username);
        reg_password = findViewById(R.id.reg_password);
        reg_email = findViewById(R.id.reg_email);
        reg_userid = findViewById(R.id.reg_userid);

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.reg_userrole));
        //set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regData();

                Intent myintent = new Intent(SignUp.this, Login.class);
                startActivity(myintent);
                //setContentView(R.layout.login);
            }
        });
    }

        //username, password, email, userrole
        private void regData() {
            // URL endpoint for adding courses
            String url = "http://coms-309-030.class.las.iastate.edu:8080/users/post";

            String username = reg_username.getText().toString();
            String password = reg_password.getText().toString();
            String userrole = dropdown.getSelectedItem().toString();
            String email = reg_email.getText().toString();

            // JSON object that will contain the payload of the POST request
            JSONObject postData = new JSONObject();
            try {

                postData.put("username", username);
                postData.put("password", password);
                postData.put("email", email);
                postData.put("role", userrole);
            } catch (JSONException e) {
                // Print stack trace for any JSON exception while populating postData
                e.printStackTrace();
            }
        }

    }

