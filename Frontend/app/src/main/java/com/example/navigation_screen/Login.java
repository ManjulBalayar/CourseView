package com.example.navigation_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.navigation_screen.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import androidx.appcompat.app.AppCompatActivity;




public class Login extends AppCompatActivity{

    EditText username, password;
    Button loginbtn;

    private ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding = ActivityMainBinding.inflate(getLayoutInflater());
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

            }
        });




    }
}