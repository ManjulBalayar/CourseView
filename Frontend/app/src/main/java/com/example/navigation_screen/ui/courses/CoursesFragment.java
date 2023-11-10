package com.example.navigation_screen.ui.courses;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigation_screen.Login;
import com.example.navigation_screen.PreferencesUtil;
import com.example.navigation_screen.R;
import com.example.navigation_screen.RateCourse;
import com.example.navigation_screen.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Fragment representing the course selection screen in the application.
 * Users can select courses from a spinner, view their descriptions,
 * and add selected courses to their profile
 */
public class CoursesFragment extends Fragment  {

    // Binding object for the FragmentHome layout
    FragmentHomeBinding binding;

    // Spinner widget for selecting courses
    private Spinner spinnerCourses;

    // List of course names to be displayed in the spinner
    private List<String> courseNames = new ArrayList<>();

    // TextView widget for displaying the selected course's description
    private TextView textViewCourseDescription;

    // List of course descriptions, corresponding to the course names in courseNames
    private List<String> courseDescriptions;

    // ArrayAdapter for populating the spinner with course names
    private ArrayAdapter<String> adapter;

    // Button widget for adding a course
    private Button buttonAddCourse;
    // Button widget for rating a course
    private Button buttonRateCourse;

    // List of course IDs, corresponding to the course names in courseNames
    private List<Integer> courseIds = new ArrayList<>();

    private Integer selectedCourseId;

    int userid = Login.userid;


    /**
     * Called to have the fragment instantiate its user interface view.
     * This fragment inflates a layout file and sets up the UI components.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Instantiate the CourseViewModel, which may hold data needed for UI logic.
        CourseViewModel courseViewModel =
                new ViewModelProvider(this).get(CourseViewModel.class);

        // Inflate the layout for this fragment and obtain a binding instance.
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        // Get the root view from the inflated layout.
        View root = binding.getRoot();

        // Find and assign the Spinner widget from the layout to a variable.
        spinnerCourses = root.findViewById(R.id.spinner_courses);
        // Find and assign the TextView for course description from the layout.
        textViewCourseDescription = root.findViewById(R.id.textView_course_description);

        // Initialize ArrayLists to store course names and descriptions.
        courseNames = new ArrayList<>();
        courseDescriptions = new ArrayList<>();
        // Find and assign the Add Course button from the layout.
        buttonAddCourse = root.findViewById(R.id.button_add_course);
        // Find and assign the Rate Course button from the layout.
        buttonRateCourse = root.findViewById(R.id.button_rate_course);
        // Find and assign TextView for selected course display.
        TextView textViewSelectedCourse = root.findViewById(R.id.textView_selected_course);

        // Create an ArrayAdapter to populate the Spinner widget, and set the layout for dropdown items.
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, courseNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter for the Spinner widget.
        spinnerCourses.setAdapter(adapter);
        // Call loadCourses method to populate course names, descriptions, and IDs from the server.
        loadCourses();

        // Set a listener to handle item selection in the Spinner.
        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve and display selected course and its description.
                String selectedCourse = (String) parent.getItemAtPosition(position);
                textViewSelectedCourse.setText("Selected Course: " + selectedCourse);
                textViewCourseDescription.setText("Course Description: " + courseDescriptions.get(position));

                // Check if a valid course is selected and adjust the visibility of the Add Course button and Rate Course button accordingly.
                if(selectedCourse != null && !selectedCourse.isEmpty()) {
                    buttonAddCourse.setVisibility(View.VISIBLE);
                } else {
                    buttonAddCourse.setVisibility(View.GONE);
                }

                if(selectedCourse != null && !selectedCourse.isEmpty()) {
                    buttonRateCourse.setVisibility(View.VISIBLE);
                } else {
                    buttonRateCourse.setVisibility(View.GONE);
                }

                if(position >= 0 && position < courseIds.size()) {
                    selectedCourseId = courseIds.get(position);
                } else {
                    selectedCourseId = null;
                }
            }

            // Handle the case where nothing is selected in the Spinner.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Update UI elements to reflect the lack of a selected course.
                textViewSelectedCourse.setText("No Course Selected");
                buttonAddCourse.setVisibility(View.GONE);
                buttonRateCourse.setVisibility(View.GONE);
                textViewCourseDescription.setText("Course Description: None");
            }
        });

        // Set a click listener for the Add Course button.
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve selected position in the Spinner.
                int position = spinnerCourses.getSelectedItemPosition();


                // Log.d("DEBUG", "Selected Position: " + position);

                // Validate selected position and ensure it corresponds to a valid course, then add the course.
                if (position != AdapterView.INVALID_POSITION && position < courseIds.size()) {
                    int selectedCourseId = courseIds.get(position);
                    System.out.println(selectedCourseId);
                    addCourse(selectedCourseId);
                } else {
                    // Show a toast message if an invalid course is selected.
                    Toast.makeText(getContext(), "Please select a valid course", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set a click listener for the Add Course button.
        buttonRateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve selected position in the Spinner.
                int position = spinnerCourses.getSelectedItemPosition();

                // Log.d("DEBUG", "Selected Position: " + position);

                // Validate selected position and ensure it corresponds to a valid course, then add the course.
                if (selectedCourseId != null) {
                    Intent myintent = new Intent(getActivity(), RateCourse.class);
                    myintent.putExtra("COURSE_ID", selectedCourseId);
                    startActivity(myintent);
                } else {
                    // Show a toast message if an invalid course is selected.
                    Toast.makeText(getContext(), "Please select a valid course", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Return the root view of the fragment.
        return root;
    }
//text comment////

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // This method loads courses from a specified URL and populates course-related variables.
    private void loadCourses() {
        // URL pointing to the courses resource
        String url = "http://coms-309-030.class.las.iastate.edu:8080/courses";

        // Create a new JSON array request to receive a JSON array from the given URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                // Listener for successful responses
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Clear existing course data
                            courseNames.clear();
                            courseDescriptions.clear();
                            courseIds.clear();

                            // Iterate through the JSON array response
                            for (int i = 0; i < response.length(); i++) {
                                // Get each course as a JSON object
                                JSONObject course = response.getJSONObject(i);
                                // Extract and store relevant information from each course object
                                int id = course.getInt("courseid");
                                String name = course.getString("name");
                                String description = course.getString("description");
                                courseNames.add(name);
                                courseDescriptions.add(description);
                                courseIds.add(id);
                            }

                            // Notify adapter of data changes
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            // Print stack trace for JSONException
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
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }

    // This method sends a POST request to add a course with the specified courseId for a student
    private void addCourse(int courseId) {
        // URL endpoint for adding courses
        userid = PreferencesUtil.getUserId(getContext());
        String url = "http://coms-309-030.class.las.iastate.edu:8080/addCourse";
        Log.d("userid: ", "" + userid);
        Log.d("courseId: ", "" + courseId);
        // JSON object that will contain the payload of the POST request
        JSONObject postData = new JSONObject();
        try {
            // Populating postData with student_id and course_id
            postData.put("user_id", userid);
            postData.put("course_id", courseId);

        } catch (JSONException e) {
            // Print stack trace for any JSON exception while populating postData
            e.printStackTrace();
        }

        // Create a new JSON object request to send a JSON object to the server
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, postData,
                // Listener for successful responses
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Log.d("DEBUG", "Received response: " + response.toString());

                        // Create a dialog builder for user feedback
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        // Check if response contains student_id and course_id indicating success
                        if (response.has("user_id") && response.has("course_id")) {
                            // Show success message to the user
                            builder.setTitle("Success");
                            builder.setMessage("Course successfully added!");
                        } else {
                            // Handle unknown errors and inform the user
                            builder.setTitle("Error");
                            builder.setMessage("Unknown Error");
                        }

                        // Create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                },
                // Listener for error responses
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Create a dialog builder for displaying error messages to the user
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Network Error");

                        // Log the details of the network error
                        Log.e("VolleyError", "Error: " + error.toString());

                        // Check if there is a network response and display appropriate error message
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String responseBody = new String(error.networkResponse.data);
                            builder.setMessage("Response error: " + responseBody);
                        } else {
                            builder.setMessage("Network error: " + error.toString());
                        }

                        // Create and show the alert dialog with the error message
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
        );

        // Add the created request to the Volley request queue
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }







}
