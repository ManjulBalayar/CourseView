package com.example.navigation_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main activity class that serves as the entry point for the application.
 * This class provides the user with the choice to either log in or sign up.
 */
public class MainActivity extends AppCompatActivity {

    // Buttons for the user to choose between login and signup.
    Button loginchoice, signupchoice;

    /**
     * Called when the activity is starting.
     * Initializes the activity, sets the content view, and sets up the UI components.
     * It also handles click events for login and signup choices.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        setContentView(R.layout.choice);

        loginchoice = findViewById(R.id.loginchoice);
        signupchoice = findViewById(R.id.signupchoice);

        loginchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this, Login.class);
                startActivity(myintent);
                //setContentView(R.layout.login);
            }
        });

        signupchoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this, SignUp.class);
                startActivity(myintent);
                //setContentView(R.layout.signup);
            }
        });
        /*binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.courses, R.id.chat, R.id.profile, R.id.schedule)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        */
    }


}