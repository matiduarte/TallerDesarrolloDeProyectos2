package fiuba.tallerdeproyectos2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fiuba.tallerdeproyectos2.Activities.CourseDetailsActivity;
import fiuba.tallerdeproyectos2.Adapters.RecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.CoursesCardViewData;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;

public class CoursesFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    public Integer courseId;
    public CoursesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_courses, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(getDataSet());
        recyclerView.setAdapter(adapter);

        // Code to Add an item with default animation
        //((RecyclerViewAdapter) adapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((RecyclerViewAdapter) adapter).deleteItem(index);

        return rootView;
    }

    private void navigateToCourseDetailsActivity(){
        Intent intent = new Intent(getActivity().getApplicationContext(), CourseDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RecyclerViewAdapter) adapter).setOnItemClickListener(new RecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(" Clicked on Item ", String.valueOf(position));
                TextView tv = (TextView) v.findViewById(R.id.course_id);
                courseId = Integer.valueOf(tv.getText().toString());
                navigateToCourseDetailsActivity();
            }
        });
    }

    private ArrayList<CoursesCardViewData> getDataSet() {
        ArrayList results = new ArrayList<CoursesCardViewData>();
        for (int index = 0; index < 20; index++) {
            CoursesCardViewData obj = new CoursesCardViewData("Algoritmos y Programacion I",
                    ApiClient.BASE_URL + "Files/Course/11/img_oficina_de_proyectos_4.jpg", String.valueOf(index));
            results.add(index, obj);
        }
        return results;
    }



}
