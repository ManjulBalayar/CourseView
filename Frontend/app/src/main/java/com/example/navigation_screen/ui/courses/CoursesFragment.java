package com.example.navigation_screen.ui.courses;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigation_screen.R;
import com.example.navigation_screen.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Spinner spinnerCourses;
    private List<String> courseNames = new ArrayList<>();

    private TextView textViewCourseDescription;

    private List<String> courseDescriptions;
    private ArrayAdapter<String> adapter;

    private Button buttonAddCourse;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CourseViewModel courseViewModel =
                new ViewModelProvider(this).get(CourseViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        spinnerCourses = root.findViewById(R.id.spinner_courses);
        textViewCourseDescription = root.findViewById(R.id.textView_course_description);

        courseNames = new ArrayList<>();
        courseDescriptions = new ArrayList<>();

        buttonAddCourse = root.findViewById(R.id.button_add_course);

        TextView textViewSelectedCourse = root.findViewById(R.id.textView_selected_course);


        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, courseNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapter);


        loadCourses();

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = (String) parent.getItemAtPosition(position);
                textViewSelectedCourse.setText("Selected Course: " + selectedCourse);
                textViewCourseDescription.setText("Course Description: " + courseDescriptions.get(position));

                if(selectedCourse != null && !selectedCourse.isEmpty()) {
                    buttonAddCourse.setVisibility(View.VISIBLE); // Make the button visible
                } else {
                    buttonAddCourse.setVisibility(View.GONE); // Hide the button if no course is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no item selected
                textViewSelectedCourse.setText("No Course Selected");
                buttonAddCourse.setVisibility(View.GONE);
                textViewCourseDescription.setText("Course Description: None");
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadCourses() {
        String url = "http://coms-309-030.class.las.iastate.edu:8080/courses";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            courseNames.clear();
                            courseDescriptions.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject course = response.getJSONObject(i);
                                String name = course.getString("courseName");
                                String description = course.getString("description");
                                courseNames.add(name);
                                courseDescriptions.add(description);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Log.e("VolleyError", error.toString());
            }
        });
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }



}






