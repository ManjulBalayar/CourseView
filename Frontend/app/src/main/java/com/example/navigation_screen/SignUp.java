package com.example.navigation_screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity for handling user registration in the application.
 * This class manages the user interface for sign up and processes the registration details.
 */
public class SignUp extends AppCompatActivity {

    // UI elements for registration inputs and sign up button.
    Button signupbtn;
    Spinner dropdown;
    EditText reg_username, reg_password, reg_email, reg_firstname, reg_lastname;
    //List<String> userList = new ArrayList<>();

    /**
     * Called when the activity is starting.
     * Initializes the activity, sets up UI components, and handles
     * the sign up button click events.
     *
     * @param savedInstanceState
     */
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
        reg_firstname = findViewById(R.id.reg_firstname);
        reg_lastname = findViewById(R.id.reg_lastname);

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
                Toast.makeText(SignUp.this, "User Successfully Created!", Toast.LENGTH_SHORT).show();

                Intent myintent = new Intent(SignUp.this, Login.class);
                startActivity(myintent);
                //setContentView(R.layout.login);
            }
        });
    }

    /**
     * Processes the registration data by sending a POST request
     * with the user details to the server.
     */
        private void regData() {
            // URL endpoint for adding courses
            String url = "http://coms-309-030.class.las.iastate.edu:8080/users/post";

            String username = reg_username.getText().toString();
            String password = reg_password.getText().toString();
            String userrole = dropdown.getSelectedItem().toString();
            String email = reg_email.getText().toString();
            String firstname = reg_firstname.getText().toString();
            String lastname = reg_lastname.getText().toString();

            // JSON object that will contain the payload of the POST request
            JSONObject postData = new JSONObject();
            try {

                postData.put("username", username);
                //postData.put("firstname", firstname);
                //postData.put("lastname", lastname);
                postData.put("password", password);
                postData.put("email", email);
                postData.put("role", userrole);
            } catch (JSONException e) {
                // Print stack trace for any JSON exception while populating postData
                e.printStackTrace();
            }


            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    postData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // handle the response here
                            System.out.println("It works");
                            System.out.println(response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Log error responses with tag "VolleyError"
                            Log.e("VolleyError", error.toString());
                        }
                    }
            );

            requestQueue.add(jsonObjectRequest);
            //Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

        }
}

