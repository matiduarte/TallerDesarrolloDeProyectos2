package fiuba.tallerdeproyectos2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fiuba.tallerdeproyectos2.Activities.UnitDetailsActivity;
import fiuba.tallerdeproyectos2.Adapters.UnitRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.UnitsCardViewData;
import fiuba.tallerdeproyectos2.R;

public class CourseUnitiesFragment extends Fragment{

    private RecyclerView.Adapter adapter;
    ArrayList units = new ArrayList<>();
    ArrayList activeUnits = new ArrayList();
    Boolean showExam = false;
    private Boolean isSubscribed;
    private Integer unitId, courseId, sessionId;
    private String courseUnities;

    public CourseUnitiesFragment() {}

    public static CourseUnitiesFragment newInstance(Integer courseId, Integer sessionId, Boolean isSubscribed, String courseUnities) {
        CourseUnitiesFragment courseUnitiesFragment = new CourseUnitiesFragment();
        Bundle args = new Bundle();
        args.putInt("courseId", courseId);
        args.putInt("sessionId", sessionId);
        args.putBoolean("isSubscribed", isSubscribed);
        args.putString("courseUnities", courseUnities);
        courseUnitiesFragment.setArguments(args);
        return courseUnitiesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            courseId = getArguments().getInt("courseId");
            sessionId = getArguments().getInt("sessionId");
            isSubscribed = getArguments().getBoolean("isSubscribed");
            courseUnities = getArguments().getString("courseUnities");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_unities, container, false);

        try {
            JSONArray courseUnitiesData = new JSONArray(courseUnities);
            if(courseUnitiesData.length() > 0){
                for (int i=0; i<courseUnitiesData.length(); i++) {
                    JSONObject courseUnitiesArray = new JSONObject(courseUnitiesData.getString(i));
                    Boolean isActive = courseUnitiesArray.getBoolean("isActive");
                    if(isActive){
                        activeUnits.add(i);
                    }
                    if(!isSubscribed || isActive){
                        UnitsCardViewData obj = new UnitsCardViewData(courseUnitiesArray.getString("name"), courseUnitiesArray.getString("description"), courseUnitiesArray.getString("id"));
                        units.add(i, obj);
                    }
                }
            }

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            if (recyclerView != null) {
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new UnitRecyclerViewAdapter(units);
                recyclerView.setAdapter(adapter);
            }

            ((UnitRecyclerViewAdapter) adapter).setOnItemClickListener(new UnitRecyclerViewAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    TextView tv = (TextView) v.findViewById(R.id.unit_id);
                    unitId = Integer.valueOf(tv.getText().toString());
                    if(activeUnits.contains(position+1)){
                        showExam = true;
                    }
                    if(isSubscribed){
                        navigateToUnitDetailsActivity();
                    } else {
                        Toast.makeText(getContext(), R.string.unit_detail_alert, Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void navigateToUnitDetailsActivity(){
        Intent intent = new Intent(getContext(), UnitDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("unitId", unitId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("showExam", showExam);
        intent.putExtra("sessionId", sessionId);
        startActivity(intent);
    }
}
