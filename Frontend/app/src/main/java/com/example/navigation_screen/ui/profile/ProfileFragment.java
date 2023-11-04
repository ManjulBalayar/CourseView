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

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    TextView textUsername, textEmail;

    Button viewReviewsBtn;

    private List<String> courseIds = new ArrayList<>();
    private List<String> courseDescriptions = new ArrayList<>();

    private List<String> courseNames = new ArrayList<>();

    int userid = Login.userid;

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

            @Override
            public void onClick(View view) {
                getReviews();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // This method loads courses from a specified URL and populates course-related variables.
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
                            textUsername.setText(username);
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
