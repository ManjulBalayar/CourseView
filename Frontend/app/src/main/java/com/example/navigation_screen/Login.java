package com.example.navigation_screen;

import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigation_screen.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity for handling user login in the application.
 * This class manages the user interface for login and processes the login credentials.
 */
public class Login extends AppCompatActivity{

    // UI elements for username and password input and login button.
    EditText login_username, login_password;
    Button loginbtn;

    // Static variable to hold the user ID after successful login.
    public static int userid = 0;

    // Binding for the main activity layout.
    private ActivityMainBinding binding;

    /**
     * Called when the activity is starting.
     * Initializes the activity, sets up UI components, and handles login button click events.
     *
     * @param savedInstanceState
     */
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
                getID();
                System.out.println(userid);

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

    /**
     * Processes the login by sending a POST request with the username and password to the server.
     * If the login is successful, the user ID is stored and used for subsequent requests.
     */
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
                        try {
                            PreferencesUtil.saveUserId(getApplicationContext(), response.getInt("userid"));
                            userid = response.getInt("userid");
                            Log.d("userid: ", ""+ userid);
                        }
                        catch (JSONException e) {
                            // Print stack trace for JSONException
                            e.printStackTrace();
                        }
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

    /**
     * Retrieves the user ID based on the username by making a GET request to the server.
     * Updates the static userid variable with the retrieved user ID.
     */
    private void getID() {
        String username = login_username.getText().toString();
        String url = "http://coms-309-030.class.las.iastate.edu:8080/userprofiles/" + username;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                // Listener for successful responses
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                        try {
                            // Get the first (and only) object from the array
                            JSONObject firstObject = response.getJSONObject(0);

                            // Access the "user ID" value
                            userid = firstObject.getInt("userid");

                            // Now, userId contains the user ID value
                            System.out.println("userid: " + userid);
                        } catch (JSONException e) {
                            e.printStackTrace();
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

        // Add the created request to the Volley request queue
        requestQueue.add(jsonArrayRequest);
        //Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }

    /**
     * Saves the user ID in shared preferences for later use.
     *
     * @param userId The user ID to be saved.
     */
    public void saveUserId(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("userid", userId);
        myEdit.apply();
    }


}