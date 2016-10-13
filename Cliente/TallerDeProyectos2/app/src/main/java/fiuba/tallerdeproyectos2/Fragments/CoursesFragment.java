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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fiuba.tallerdeproyectos2.Activities.CourseDetailsActivity;
import fiuba.tallerdeproyectos2.Activities.SessionManagerActivity;
import fiuba.tallerdeproyectos2.Adapters.RecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.CourseData;
import fiuba.tallerdeproyectos2.Models.Courses;
import fiuba.tallerdeproyectos2.Models.CoursesCardViewData;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    public Integer courseId;
    SessionManagerActivity session;
    HashMap<String, String> user;
    Integer studentId;
    private static final String TAG = CoursesFragment.class.getSimpleName();
    ArrayList myCourses = new ArrayList<>();
    ImageView courseIconInscripted;


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

        courseIconInscripted = (ImageView) rootView.findViewById(R.id.course_icon_inscripted);


        session = new SessionManagerActivity(getContext());
        session.checkLogin();
        user = session.getUserDetails();
        studentId = Integer.valueOf(user.get(SessionManagerActivity.KEY_ID));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getSubscriptions(studentId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse>call, Response<ServerResponse> response) {
                try {
                    Boolean success =response.body().getSuccess();
                    if(success.equals(true)){
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        CourseData course = gson.fromJson(data, CourseData.class);

                        String pictureUrl;
                        JSONArray courseData = new JSONArray(course.getCourseData());
                        for (int i=0; i< courseData.length(); i++) {
                            JSONObject courseDataArray = new JSONObject(courseData.getString(i));
                            pictureUrl = "";
                            if(courseDataArray.has("pictureUrl")){
                                pictureUrl = courseDataArray.getString("pictureUrl");
                            }
                            if(courseDataArray.getBoolean("isSubscribed")){
                                courseIconInscripted.setVisibility(View.VISIBLE);
                            }
                            CoursesCardViewData obj = new CoursesCardViewData(courseDataArray.getString("name"), pictureUrl, courseDataArray.getString("id"));
                            myCourses.add(i, obj);
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


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
                    "Files/Course/11/img_oficina_de_proyectos_4.jpg", String.valueOf(index));
            results.add(index, obj);
        }
        return results;
    }



}
