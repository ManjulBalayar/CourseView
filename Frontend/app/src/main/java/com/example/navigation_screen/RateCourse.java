package com.example.navigation_screen;
//
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigation_screen.databinding.RateCourseBinding;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity for rating a course in the application.
 * Allows users to submit ratings for a course including
 * difficulty, time commitment, and additional comments.
 */
public class RateCourse extends AppCompatActivity {

    // UI elements for submitting ratings and navigating back.
    Button submitbtn;
    Button backBtn;

    // Spinners for selecting course rating and difficulty.
    Spinner rate_course, rate_difficulty;

    // EditText fields for additional comments and time commitment.
    EditText comments,time;

    // Binding for the layout of this activity.
    RateCourseBinding binding;



    //List<String> userList = new ArrayList<>();

    /**
     * Called when the activity is starting.
     * Initializes the activity, sets up UI components, and handles
     * click events for submission and navigation.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_course);
        binding = RateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        comments = findViewById(R.id.comments);
        rate_course = findViewById(R.id.course_rating);
        rate_difficulty = findViewById(R.id.rating);
        time = findViewById(R.id.time);




        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.rating));
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rate_difficulty.setAdapter(difficultyAdapter);

        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.rating));
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rate_course.setAdapter(courseAdapter);




        submitbtn = findViewById(R.id.submitbtn);
        backBtn = findViewById(R.id.back_button);


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //regData();

                submitCourseRating();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // This will close the current activity and go back to the previous activity
            }
        });
    }

    /**
     * Submits the course rating based on user inputs.
     * Sends a POST request with the rating details to the server.
     */
    private void submitCourseRating() {
        int userId = PreferencesUtil.getUserId(this);
        Intent intent = getIntent();
        int courseId = intent.getIntExtra("COURSE_ID", -1);
        Log.d("RateCourse", "Received Course ID: " + courseId);
        try {
            String url = "http://coms-309-030.class.las.iastate.edu:8080/review";

            JSONObject postData = new JSONObject();
            postData.put("comment", comments.getText().toString());
            postData.put("rating", Integer.valueOf(rate_course.getSelectedItem().toString()));
            postData.put("difficulty", Integer.valueOf(rate_course.getSelectedItem().toString()));
            postData.put("time_commitment", Integer.valueOf(time.getText().toString()));
            postData.put("userId", userId); // Retrieved from session
            postData.put("courseId", courseId); // Retrieved from session

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle response
                            Toast.makeText(RateCourse.this, "Rating submitted successfully!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                            Toast.makeText(RateCourse.this, "Failed to submit rating: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            // Add the request to the RequestQueue.
            Volley.newRequestQueue(this).add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to build JSON for rating submission.", Toast.LENGTH_LONG).show();
        }
    }
}

