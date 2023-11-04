package com.example.navigation_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.navigation_screen.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class RateCourse extends AppCompatActivity {


    Button submitbtn;
    Spinner rate_professor, rate_difficulty;
    EditText comments, professor, time;

    private ActivityMainBinding binding;
    //List<String> userList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rate_course);
        //get the spinner from the xml.
        rate_professor = (Spinner) findViewById(R.id.professor_rating);
        rate_difficulty = (Spinner) findViewById(R.id.rating);
        submitbtn = findViewById(R.id.submitbtn);
        comments = findViewById(R.id.comments);
      //  professor = findViewById(R.id.professor);
        time = findViewById(R.id.time);


        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> professor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.professor_rating));
        //set the spinners adapter to the previously created one.
        professor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rate_professor.setAdapter(professor);

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> difficulty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.rating));
        //set the spinners adapter to the previously created one.
        difficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rate_difficulty.setAdapter(difficulty);


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //regData();

                binding = ActivityMainBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                BottomNavigationView navView = findViewById(R.id.nav_view);
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.courses, R.id.chat, R.id.profile, R.id.schedule)
                        .build();
                NavController navController = Navigation.findNavController(RateCourse.this, R.id.nav_host_fragment_activity_main);
                NavigationUI.setupActionBarWithNavController(RateCourse.this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(binding.navView, navController);
            }
        });
    }

    //username, password, email, userrole
    /*private void regData() {
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
            postData.put("firstname", firstname);
            postData.put("lastname", lastname);
            postData.put("email", email);
            postData.put("password", password);
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

    }*/
}

