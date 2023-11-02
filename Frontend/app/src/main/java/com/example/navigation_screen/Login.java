package com.example.navigation_screen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigation_screen.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity{

    EditText login_username, login_password;
    Button loginbtn;

    private ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                login();

                binding = ActivityMainBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                BottomNavigationView navView = findViewById(R.id.nav_view);
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.courses, R.id.chat, R.id.profile, R.id.schedule)
                        .build();
                NavController navController = Navigation.findNavController(Login.this, R.id.nav_host_fragment_activity_main);
                NavigationUI.setupActionBarWithNavController(Login.this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(binding.navView, navController);

            }
        });
    }
    private void login() {
        // URL endpoint for adding courses
        String url = "http://coms-309-030.class.las.iastate.edu:8080/login";

        String username = login_username.getText().toString();
        String password = login_password.getText().toString();

        // JSON object that will contain the payload of the POST request
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("password", password);
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
                        String jsonResponse = response.toString();
                        System.out.println(jsonResponse);
                        Log.d("DEBUG", jsonResponse);

                        if(jsonResponse == null){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error here
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        //Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

    }

}