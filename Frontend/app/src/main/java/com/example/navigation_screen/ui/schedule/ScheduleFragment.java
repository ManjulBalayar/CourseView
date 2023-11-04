package com.example.navigation_screen.ui.schedule;

import android.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ScheduleFragment extends Fragment {

    private List<String> courseNames = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    int userid = Login.userid;

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
                // Execute your GET request logic here
                calculateWorkload();
            }
        });


        return root;
    }

    private void loadCourses(int userid) {
        Log.d("Debug", "Student ID: " + userid);

        String url = "http://coms-309-030.class.las.iastate.edu:8080/schedule/" + userid;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
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
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                    }
                });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }

    private void calculateWorkload() {
        String url = "http://coms-309-030.class.las.iastate.edu:8080/workload/byUserid/" + userid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            System.out.println(response);
                            //String workload = response.toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("LOOKING AT YOUR COURSES AND BOOM MAGIC");

                            String rating = response.getString("rating");
                            String difficulty = response.getString("difficulty");
                            String time_commitment = response.getString("time_commitment");

                            builder.setMessage("Your calculated workload is... \nRating: " + rating +
                            "\nDifficulty: " + difficulty + "\nTime Commitment: " + time_commitment);
                            builder.setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                    }
                });

        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);


    }
}
