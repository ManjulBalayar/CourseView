package com.example.navigation_screen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigation_screen.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;


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
                //Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                //Toast.makeText(Login.this, "Login Failed. Try again.!", Toast.LENGTH_SHORT).show();
                //if(outcome == "True") {
//                    binding = ActivityMainBinding.inflate(getLayoutInflater());
//                    setContentView(binding.getRoot());
//
//                    BottomNavigationView navView = findViewById(R.id.nav_view);
//                    // Passing each menu ID as a set of Ids because each
//                    // menu should be considered as top level destinations.
//                    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                            R.id.courses, R.id.chat, R.id.profile, R.id.schedule)
//                            .build();
//                    NavController navController = Navigation.findNavController(Login.this, R.id.nav_host_fragment_activity_main);
//                    NavigationUI.setupActionBarWithNavController(Login.this, navController, appBarConfiguration);
//                    NavigationUI.setupWithNavController(binding.navView, navController);
                //}

            }
        });
    }
    private void login() {

        String username = login_username.getText().toString();
        String password = login_password.getText().toString();

        // URL endpoint for adding courses
        String url = "http://coms-309-030.class.las.iastate.edu:8080/users/" + username;


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                // Listener for successful responses
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            String jsonResponse = response.toString();
                            System.out.println(jsonResponse);

                            if(jsonResponse != null && jsonResponse.length() > 1){
                                handleLoginSuccessful();
                            }else{
                                handleLoginFail();
                            }
                    }
                },
                // Listener for error responses
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log error responses with tag "VolleyError"
                        Log.e("VolleyError", error.toString());
                    }
                });
//

        requestQueue.add(jsonArrayRequest);
        //Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

    }

    //Login is successful! Navigate to next screen.
    private void handleLoginSuccessful(){
        Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
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

    //Login is invalid. Doesn't move screen forward.
    private void handleLoginFail(){
        Toast.makeText(Login.this, "Login Failed. Please try again.", Toast.LENGTH_SHORT).show();
    }

}