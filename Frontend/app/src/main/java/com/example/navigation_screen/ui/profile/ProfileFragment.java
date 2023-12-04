package com.example.navigation_screen.ui.profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.example.navigation_screen.R;
import com.example.navigation_screen.databinding.FragmentProfileBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment representing the user profile in the application.
 * It handles displaying user profile information and user reviews.
 * This fragment is part of the application's navigation.
 */
public class ProfileFragment extends Fragment {

    // Binding object for interacting with the view elements in this fragment.
    private FragmentProfileBinding binding;

    // TextViews for displaying the username and email.
    TextView textUsername, textEmail;

    // Button to view user reviews.
    Button viewReviewsBtn;

    // Lists to hold course IDs and descriptions.
    private List<String> courseIds = new ArrayList<>();
    private List<String> courseDescriptions = new ArrayList<>();
    private List<String> courseNames = new ArrayList<>();

    // User ID of the currently logged-in user.
    int userid = Login.userid;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getProfile();

        textUsername = root.findViewById(R.id.textUsername);
        textEmail = root.findViewById(R.id.textEmail);
        viewReviewsBtn = root.findViewById(R.id.viewReviewsBtn);

        viewReviewsBtn.setOnClickListener(new View.OnClickListener() {

            /**
             * Upon clicking call getReviews() method
             * @param view
             */
            @Override

            public void onClick(View view) {
                getReviews();
            }
        });

        return root;
    }

    /**
     * Called when the view hierarchy associated with the fragment is being removed.
     * Used to clean up resources associated with this fragment.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Retrieves the profile information of the user and populates the UI elements.
     * It fetches data via HTTP GET request such as username and email and updates the views.
     */
    private void getProfile() {
        // URL pointing to the courses resource
        String url = "http://coms-309-030.class.las.iastate.edu:8080/userprofile/" + userid;

        // Create a new JSON array request to receive a JSON array from the given URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                // Listener for successful responses
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response.toString());
                            String username = response.getString("username");
                            String email = response.getString("email");
                            System.out.println(username);
                            System.out.println(email);
                            String currentText = textUsername.getText().toString();
                            textUsername.setText(currentText + username);
                            textEmail.setText(email);
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
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }

    /**
     * Fetches and displays reviews associated with the user.
     * It makes a HTTP GET request to retrieve user reviews and updates the UI to show these reviews.
     */
    private void getReviews() {
        // URL pointing to the courses resource
        String url = "http://coms-309-030.class.las.iastate.edu:8080/reviews/byUser/" + userid;

        // Create a new JSON array request to receive a JSON array from the given URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                // Listener for successful responses
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                        try {

                            courseIds.clear();

                            // Iterate through the JSON array response
                            for (int i = 0; i < response.length(); i++) {
                                // Get each course as a JSON object
                                JSONObject course = response.getJSONObject(i);
                                // Extract and store relevant information from each course object
                                String id = course.getString("courseId");
                                System.out.println(id);
                                convertToName(id);
                                courseIds.add(id);
                            }

                            System.out.println(response.toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Courses Reviewed:");
                            builder.setMessage(courseIds.toString());
                            builder.setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
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

    /**
     *   Converts a course ID to a course name by making a HTTP GET request.
     *   The course name is then used to update relevant UI elements.
     *
     * @param courseId The ID of the course to be converted to a name.
     */
    private void convertToName(String courseId) {
        // URL pointing to the courses resource
        String url = "http://coms-309-030.class.las.iastate.edu:8080/courses/" + courseId;

        // Create a new JSON array request to receive a JSON array from the given URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                // Listener for successful responses
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
//                        try {
//                            // Get each course as a JSON object
//                            // Extract and store relevant information from each course object
//                            JSONObject course = response.getJSONObject(0);
//                            String id = response.getString("courseid");
//                            String name = response.getString("name");
//                            String description = response.getString("description");
//                            System.out.println(name);
//                            System.out.println(description);
//                            System.out.println(id);
//
//                        } catch (JSONException e) {
//                            // Print stack trace for JSONException
//                            e.printStackTrace();
//                        }
                    }
                },
                // Listener for error responses
                new Response.ErrorListener() {

                    /**
                     * Log error responses from Volley
                     * @param error
                     */
                    @Override

                    public void onErrorResponse(VolleyError error) {
                        // Log error responses with tag "VolleyError"
                        Log.e("VolleyError", error.toString());
                    }
                });

        // Add the created request to the Volley request queue
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }

}
