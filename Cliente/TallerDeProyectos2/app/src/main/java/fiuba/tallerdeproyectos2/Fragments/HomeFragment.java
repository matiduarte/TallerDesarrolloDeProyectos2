package fiuba.tallerdeproyectos2.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiuba.tallerdeproyectos2.Activities.CourseDetailsActivity;
import fiuba.tallerdeproyectos2.Activities.MainActivity;
import fiuba.tallerdeproyectos2.Activities.SessionManagerActivity;
import fiuba.tallerdeproyectos2.Adapters.ExpandableListViewAdapter;
import fiuba.tallerdeproyectos2.Models.Courses;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import fiuba.tallerdeproyectos2.Utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final float INITIAL_ITEMS_COUNT = 1.0F;
    private LinearLayout mCarouselContainer;
    private ExpandableListView expandableListView;
    private List<String> parentHeaderInformation;
    HashMap<String, List<String>> childContent;
    private static final String TAG = HomeFragment.class.getSimpleName();
    final DisplayMetrics displayMetrics = new DisplayMetrics();
    private int lastExpandedPosition = -1;
    public String courseName;
    public Integer courseId;
    List<CourseInfo> courseInfo = new ArrayList<CourseInfo>();
    SwipeRefreshLayout swipeRefreshLayout;
    ExpandableListViewAdapter expandableListViewAdapter;
    public HomeFragment() {}

    public class CourseInfo{
        public String id, name;

        public CourseInfo(String id, String name){
            this.id = id;
            this.name = name;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentHeaderInformation = new ArrayList<String>();
        childContent = new HashMap<String, List<String>>();
    }

    @Override
    public void onRefresh() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getCourses();
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

                        JSONArray soonCoursesData = new JSONArray(courses.getSoonCourses());
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        final int width = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT) - 25;
                        ImageView imageItem;
                        TextView titleTex,descTex;
                        for (int i=0; i<soonCoursesData.length(); i++) {
                            JSONObject soonCoursesArray = new JSONObject(soonCoursesData.getString(i));

                            LinearLayout linearLayoutParent = new LinearLayout(getActivity().getApplicationContext());
                            linearLayoutParent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            linearLayoutParent.setOrientation(LinearLayout.VERTICAL);
                            linearLayoutParent.setBackgroundResource(R.drawable.shadow);

                            LinearLayout linearLayoutTitle = new LinearLayout(getActivity().getApplicationContext());
                            linearLayoutTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                            linearLayoutTitle.setOrientation(LinearLayout.VERTICAL);

                            LinearLayout linearLayoutChild = new LinearLayout(getActivity().getApplicationContext());
                            linearLayoutChild.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                            titleTex = new TextView(getActivity().getApplicationContext());
                            titleTex.setText(soonCoursesArray.getString("name"));
                            titleTex.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            titleTex.setPadding(30, 20, 30, 20);
                            titleTex.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));

                            linearLayoutTitle.addView(titleTex);

                            descTex = new TextView(getActivity().getApplicationContext());
                            descTex.setText(soonCoursesArray.getString("description"));
                            descTex.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            descTex.setPadding(30, 30, 30, 30);
                            descTex.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 300));

                            linearLayoutChild.addView(descTex);

                            imageItem = new ImageView(getActivity().getApplicationContext());
                            if(soonCoursesArray.has("pictureUrl")){
                                new DownloadImageTask(imageItem).execute(ApiClient.BASE_URL + soonCoursesArray.getString("pictureUrl"));
                            } else {
                                imageItem.setImageResource(R.drawable.default_image);
                            }
                            imageItem.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 300));
                            linearLayoutChild.addView(imageItem);

                            linearLayoutTitle.addView(linearLayoutChild);
                            linearLayoutParent.addView(linearLayoutTitle);
                            linearLayoutParent.setId(Integer.parseInt(soonCoursesArray.getString("id")));

                            linearLayoutParent.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    courseId = view.getId();
                                    navigateToCourseDetailsActivity();
                                }
                            });

                            mCarouselContainer.addView(linearLayoutParent);
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
                expandableListViewAdapter.refresh(parentHeaderInformation, childContent);
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
        mCarouselContainer = (LinearLayout) rootView.findViewById(R.id.carousel);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableList);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getCourses();
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

                        expandableListViewAdapter = new ExpandableListViewAdapter(getActivity().getApplicationContext(), parentHeaderInformation, childContent);
                        expandableListView.setAdapter(expandableListViewAdapter);
                        expandableListView.setIndicatorBounds(expandableListView.getWidth(), expandableListView.getRight() - 40);

                        String pictureUrl;
                        JSONArray soonCoursesData = new JSONArray(courses.getSoonCourses());
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        final int width = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT) - 25;
                        ImageView imageItem;
                        TextView titleTex,descTex;
                        for (int i=0; i<soonCoursesData.length(); i++) {
                            JSONObject soonCoursesArray = new JSONObject(soonCoursesData.getString(i));

                            LinearLayout linearLayoutParent = new LinearLayout(getActivity().getApplicationContext());
                            linearLayoutParent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            linearLayoutParent.setOrientation(LinearLayout.VERTICAL);
                            linearLayoutParent.setBackgroundResource(R.drawable.shadow);

                            LinearLayout linearLayoutTitle = new LinearLayout(getActivity().getApplicationContext());
                            linearLayoutTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                            linearLayoutTitle.setOrientation(LinearLayout.VERTICAL);

                            LinearLayout linearLayoutChild = new LinearLayout(getActivity().getApplicationContext());
                            linearLayoutChild.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                            titleTex = new TextView(getActivity().getApplicationContext());
                            titleTex.setText(soonCoursesArray.getString("name"));
                            titleTex.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            titleTex.setPadding(30, 20, 30, 20);
                            titleTex.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));

                            linearLayoutTitle.addView(titleTex);

                            descTex = new TextView(getActivity().getApplicationContext());
                            descTex.setText(soonCoursesArray.getString("description"));
                            descTex.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            descTex.setPadding(30, 30, 30, 30);
                            descTex.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 300));

                            linearLayoutChild.addView(descTex);

                            imageItem = new ImageView(getActivity().getApplicationContext());
                            if(soonCoursesArray.has("pictureUrl")){
                                new DownloadImageTask(imageItem).execute(ApiClient.BASE_URL + soonCoursesArray.getString("pictureUrl"));
                            } else {
                                imageItem.setImageResource(R.drawable.default_image);
                            }
                            imageItem.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 300));
                            linearLayoutChild.addView(imageItem);

                            linearLayoutTitle.addView(linearLayoutChild);
                            linearLayoutParent.addView(linearLayoutTitle);
                            linearLayoutParent.setId(Integer.parseInt(soonCoursesArray.getString("id")));

                            linearLayoutParent.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    courseId = view.getId();
                                    navigateToCourseDetailsActivity();
                                }
                            });

                            mCarouselContainer.addView(linearLayoutParent);
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
