package fiuba.tallerdeproyectos2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import fiuba.tallerdeproyectos2.Adapters.ExpandableListViewAdapter;
import fiuba.tallerdeproyectos2.Adapters.RecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.Courses;
import fiuba.tallerdeproyectos2.Models.CoursesCardViewData;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import fiuba.tallerdeproyectos2.Utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ExpandableListView expandableListView;
    private List<String> parentHeaderInformation;
    HashMap<String, List<String>> childContent;
    private static final String TAG = HomeFragment.class.getSimpleName();
    private int lastExpandedPosition = -1;
    public String courseName;
    public Integer courseId;
    List<CourseInfo> courseInfo = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    ExpandableListViewAdapter expandableListViewAdapter;
    private RecyclerView.Adapter adapter;
    ArrayList soonCourses = new ArrayList<>();
    SessionManagerActivity session;
    HashMap<String, String> user;
    Integer studentId;
    RecyclerView recyclerView;
    TextView noSoonCourses;
    Float averageCalification;

    public HomeFragment() {}

    public class CourseInfo{
        public String id, name;

        CourseInfo(String id, String name){
            this.id = id;
            this.name = name;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentHeaderInformation = new ArrayList<>();
        childContent = new HashMap<>();
    }

    @Override
    public void onRefresh() {
        soonCourses.clear();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getCourses(studentId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse>call, Response<ServerResponse> response) {
                try {
                    Boolean success =response.body().getSuccess();
                    if(success.equals(true)){
                        parentHeaderInformation.clear();
                        childContent.clear();
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        Courses courses = gson.fromJson(data, Courses.class);
                        JSONArray allCategoriesCoursesData = new JSONArray(courses.getAllCategories());
                        for (int i=0; i<allCategoriesCoursesData.length(); i++) {
                            JSONObject allCategoriesCoursesArray = new JSONObject(allCategoriesCoursesData.getString(i));
                            String categoryName = allCategoriesCoursesArray.getString("name");
                            JSONArray coursesInCategoryData = new JSONArray(allCategoriesCoursesArray.getString("courses"));
                            if(coursesInCategoryData.length() > 0){
                                parentHeaderInformation.add(categoryName);
                                List<String> categoryCoursesList = new ArrayList<String>();
                                for (int j=0; j<coursesInCategoryData.length(); j++) {
                                    JSONObject coursesInCategoryArray = new JSONObject(coursesInCategoryData.getString(j));
                                    categoryCoursesList.add(coursesInCategoryArray.getString("name"));
                                    courseInfo.add(new CourseInfo(coursesInCategoryArray.getString("id"),coursesInCategoryArray.getString("name")));
                                }
                                childContent.put(parentHeaderInformation.get(i), categoryCoursesList);
                            }
                        }
                        String pictureUrl;
                        JSONArray soonCoursesData = new JSONArray(courses.getSoonCourses());
                        for (int i=0; i< soonCoursesData.length(); i++) {
                            JSONObject soonCoursesArray = new JSONObject(soonCoursesData.getString(i));
                            pictureUrl = "";
                            if(soonCoursesArray.has("pictureUrl")){
                                pictureUrl = soonCoursesArray.getString("pictureUrl");
                            }
                            JSONArray courseCalificationsData = new JSONArray(soonCoursesArray.getString("califications"));
                            averageCalification = Utilities.getAverageCalification(courseCalificationsData);

                            CoursesCardViewData obj = new CoursesCardViewData(soonCoursesArray.getString("name"), pictureUrl, soonCoursesArray.getString("id"), averageCalification.toString());
                            soonCourses.add(i, obj);
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
                expandableListViewAdapter.refresh(parentHeaderInformation, childContent);
                adapter = new RecyclerViewAdapter(soonCourses);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ServerResponse>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableList);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        noSoonCourses = (TextView) rootView.findViewById(R.id.no_soon_courses);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        session = new SessionManagerActivity(getContext());
        session.checkLogin();
        user = session.getUserDetails();
        studentId = Integer.valueOf(user.get(SessionManagerActivity.KEY_ID));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getCourses(studentId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse>call, Response<ServerResponse> response) {
                try {
                    Boolean success =response.body().getSuccess();
                    if(success.equals(true)){
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        Courses courses = gson.fromJson(data, Courses.class);
                        JSONArray allCategoriesCoursesData = new JSONArray(courses.getAllCategories());
                        for (int i=0; i<allCategoriesCoursesData.length(); i++) {
                            JSONObject allCategoriesCoursesArray = new JSONObject(allCategoriesCoursesData.getString(i));
                            String categoryName = allCategoriesCoursesArray.getString("name");
                            JSONArray coursesInCategoryData = new JSONArray(allCategoriesCoursesArray.getString("courses"));
                            if(coursesInCategoryData.length() > 0){
                                parentHeaderInformation.add(categoryName);
                                List<String> categoryCoursesList = new ArrayList<String>();
                                for (int j=0; j<coursesInCategoryData.length(); j++) {
                                    JSONObject coursesInCategoryArray = new JSONObject(coursesInCategoryData.getString(j));
                                    categoryCoursesList.add(coursesInCategoryArray.getString("name"));
                                    courseInfo.add(new CourseInfo(coursesInCategoryArray.getString("id"),coursesInCategoryArray.getString("name")));
                                }
                                childContent.put(parentHeaderInformation.get(i), categoryCoursesList);
                            }
                        }

                        expandableListViewAdapter = new ExpandableListViewAdapter(getApplicationContext(), parentHeaderInformation, childContent);
                        expandableListView.setAdapter(expandableListViewAdapter);
                        expandableListView.setIndicatorBounds(expandableListView.getWidth(), expandableListView.getRight() - 40);

                        String pictureUrl;
                        JSONArray soonCoursesData = new JSONArray(courses.getSoonCourses());
                        for (int i=0; i< soonCoursesData.length(); i++) {
                            JSONObject soonCoursesArray = new JSONObject(soonCoursesData.getString(i));
                            pictureUrl = "";
                            if(soonCoursesArray.has("pictureUrl")){
                                pictureUrl = soonCoursesArray.getString("pictureUrl");
                            }
                            JSONArray courseCalificationsData = new JSONArray(soonCoursesArray.getString("califications"));
                            averageCalification = Utilities.getAverageCalification(courseCalificationsData);
                            CoursesCardViewData obj = new CoursesCardViewData(soonCoursesArray.getString("name"), pictureUrl, soonCoursesArray.getString("id"), averageCalification.toString());
                            soonCourses.add(i, obj);
                        }

                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new RecyclerViewAdapter(soonCourses);
                        recyclerView.setAdapter(adapter);

                        if(soonCourses.isEmpty()){
                            noSoonCourses.setVisibility(View.VISIBLE);
                        }

                        ((RecyclerViewAdapter) adapter).setOnItemClickListener(new RecyclerViewAdapter.MyClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                TextView tv = (TextView) v.findViewById(R.id.course_id);
                                courseId = Integer.valueOf(tv.getText().toString());
                                navigateToCourseDetailsActivity();
                            }
                        });

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

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {

                TextView tv= (TextView) v.findViewById(R.id.child_layout);
                courseName = tv.getText().toString();
                for (int i=0; i < courseInfo.size();i++){
                    if(courseInfo.get(i).name == courseName){
                        courseId = Integer.valueOf(courseInfo.get(i).id);
                    }
                }
                navigateToCourseDetailsActivity();
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

}
