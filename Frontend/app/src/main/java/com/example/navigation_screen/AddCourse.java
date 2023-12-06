package com.example.navigation_screen;
//

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigation_screen.databinding.AddCourseBinding;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity for rating a course in the application.
 * Allows users to submit ratings for a course including
 * difficulty, time commitment, and additional comments.
 */
public class AddCourse extends AppCompatActivity {

    // UI elements for submitting ratings and navigating back.
    Button submitbtn;
    Button backbtn;

    // EditText fields for additional comments and time commitment.
    EditText courseid, name, description, department;

    // Binding for the layout of this activity.
    AddCourseBinding binding;



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
        setContentView(R.layout.add_course);
        binding = AddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        courseid = findViewById(R.id.course_id);
        name = findViewById(R.id.course_name);
        description = findViewById(R.id.course_description);
        department = findViewById(R.id.course_department);





        submitbtn = findViewById(R.id.submitbtn);
        backbtn = findViewById(R.id.backbtn);


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //regData();

                submitCourse();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish(); // This will close the current activity and go back to the previous activity
                Intent myintent = new Intent(AddCourse.this, HomeAdmin.class);
                startActivity(myintent);
            }
        });
    }

    /**
     * Submits the course rating based on user inputs.
     * Sends a POST request with the rating details to the server.
     */
    private void submitCourse() {
        //int userId = PreferencesUtil.getUserId(this);
        //Intent intent = getIntent();
        //int courseId = intent.getIntExtra("COURSE_ID", -1);
        //Log.d("AddCourse", "Received Course ID: " + courseId);
        try {
            String url = "http://coms-309-030.class.las.iastate.edu:8080/course";

            JSONObject postData = new JSONObject();
            //postData.put("courseid", Integer.valueOf(courseid.getText().toString()));
            postData.put("name", name.getText().toString());
            postData.put("description", description.getText().toString());
            postData.put("department", department.getText().toString());
            //postData.put("reviews", "[]");
            //postData.put("schedules", "[]");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle response
                            Toast.makeText(AddCourse.this, "Course added successfully!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                            Toast.makeText(AddCourse.this, "Failed to add course: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            // Add the request to the RequestQueue.
            Volley.newRequestQueue(this).add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to build JSON for course submission.", Toast.LENGTH_LONG).show();
        }
    }
}

