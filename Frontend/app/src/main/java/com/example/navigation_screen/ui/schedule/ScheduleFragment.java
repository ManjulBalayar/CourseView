package com.example.navigation_screen.ui.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigation_screen.Login;
import com.example.navigation_screen.R;
import com.example.navigation_screen.WorkloadGauge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment representing the schedule view in the application.
 * This fragment allows users to view their courses and calculate their academic workload.
 * The fragment handles network requests to fetch course data and workload calculations.
 */
public class ScheduleFragment extends Fragment {

    // List of course names to be displayed in the ListView.
    private List<String> courseNames = new ArrayList<>();

    // ArrayAdapter for populating the ListView with course names.
    private ArrayAdapter<String> adapter;

    private String workloadRating;
    private String workloadDifficulty;
    private String workloadTimeCommitment;


    // User ID of the currently logged-in user.
    int userid = Login.userid;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This method sets up the ListView for displaying courses and initializes a button
     * for calculating workload.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        // Set up the ListView and adapter
        ListView listViewCourses = root.findViewById(R.id.listView_courses);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, courseNames);
        listViewCourses.setAdapter(adapter);

        // Load courses for the given student ID
        loadCourses(userid);

        // Find the button by ID and set an OnClickListener
        Button calculateWorkloadButton = root.findViewById(R.id.button_calculate_workload);
        calculateWorkloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateWorkload();
                // Start the WorkloadGaugeActivity
                Intent intent = new Intent(getActivity(), WorkloadGauge.class);

                startActivity(intent);
            }
        });


        return root;
    }

    private void calculateWorkload() {
        String url = "http://coms-309-030.class.las.iastate.edu:8080/workload/byUserid/" + userid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            workloadRating = response.getString("rating");
                            workloadDifficulty = response.getString("difficulty");
                            workloadTimeCommitment = response.getString("time_commitment");

                            float difficultyValue = Float.parseFloat(workloadDifficulty);
                            int difficultyIntValue = (int) difficultyValue;

                            // Log statement to verify the parsed integer value
                            Log.d("WorkloadGauge", "Parsed Difficulty: " + difficultyValue);



                            Intent intent = new Intent(getActivity(), WorkloadGauge.class);
                          //  intent.putExtra("WORKLOAD_RATING", workloadRating);
                            intent.putExtra("WORKLOAD_DIFFICULTY", difficultyIntValue);
                         //   intent.putExtra("WORKLOAD_TIME_COMMITMENT", workloadTimeCommitment);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
            }
        });

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }


    /**
     * Loads courses for a given student ID from a remote server and updates the ListView.
     * This method sends a GET request to fetch course names, which are then added to the courseNames list.
     * The adapter for the ListView is notified of data changes to update the UI.
     *
     * @param userid The student ID for which the course list is to be loaded.
     */
    private void loadCourses(int userid) {
        Log.d("Debug", "Student ID: " + userid);

        String url = "http://coms-309-030.class.las.iastate.edu:8080/schedule/" + userid;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    /**
                     * On response iterate through JSON and get name and add it
                     * to courseNames list
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            courseNames.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject course = response.getJSONObject(i);
                                String name = course.getString("name");
                                courseNames.add(name);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    /**
                     * Log Volley error on error response
                     * @param error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                    }
                });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }


}
