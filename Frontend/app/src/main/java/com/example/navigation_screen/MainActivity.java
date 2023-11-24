package com.example.navigation_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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

        //Welcome to CourseView! animation
        TextView textView = findViewById(R.id.choice);
        YoYo.with(Techniques.BounceIn)
                .duration(2000)
                .playOn(textView);

        //Cy background GIF
        ImageView gifImageView = findViewById(R.id.gifImageView);
        Glide.with(this)
                .asGif()
                .load(R.drawable.isu_2)
                .into(gifImageView);

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

//        private void createNotificationChannel() {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                CharSequence name = getString(R.string.channel_name);
//                String description = getString(R.string.channel_description);
//                int importance = NotificationManager.IMPORTANCE_DEFAULT;
//                NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
//                channel.setDescription(description);
//                // Register the channel with the system
//                NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                notificationManager.createNotificationChannel(channel);
//            }
//        }




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