package com.example.navigation_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.github.anastr.speedviewlib.SpeedView;

public class WorkloadGauge extends AppCompatActivity {

    SpeedView speedView;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workload_gauge);

        speedView = findViewById(R.id.speedView);
        backBtn = findViewById(R.id.back_button);

        // Get the workload difficulty from the intent
        int workloadDifficulty = getIntent().getIntExtra("WORKLOAD_DIFFICULTY", 0);

        // Configure your SpeedView
        speedView.setMaxSpeed(5);
        speedView.setMinSpeed(0);
        speedView.setUnit(""); // Remove km/h
        speedView.setWithTremble(false); // Don't tremble after setting
        speedView.speedTo(workloadDifficulty);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override//
            public void onClick(View view) {
                finish(); // This will close the current activity and go back to the previous activity
            }
        });
    }
}
