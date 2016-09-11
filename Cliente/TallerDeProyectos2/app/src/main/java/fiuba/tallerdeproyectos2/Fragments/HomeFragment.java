package fiuba.tallerdeproyectos2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fiuba.tallerdeproyectos2.Adapters.ExpandableListViewAdapter;
import fiuba.tallerdeproyectos2.Models.Courses;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private static final float INITIAL_ITEMS_COUNT = 1.0F;
    private LinearLayout mCarouselContainer;
    private ExpandableListView expandableListView;
    private List<String> parentHeaderInformation;
    HashMap<String, List<String>> childContent;
    private static final String TAG = HomeFragment.class.getSimpleName();
    final DisplayMetrics displayMetrics = new DisplayMetrics();

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentHeaderInformation = new ArrayList<String>();
        childContent = new HashMap<String, List<String>>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mCarouselContainer = (LinearLayout) rootView.findViewById(R.id.carousel);

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
                            Object categoryName = allCategoriesCoursesArray.get("name");
                            parentHeaderInformation.add(categoryName.toString());
                            List<String> categoryCoursesList = new ArrayList<String>();
                            Object coursesInCategory = allCategoriesCoursesArray.get("courses");
                            JSONArray coursesInCategoryData = new JSONArray(coursesInCategory.toString());
                            for (int j=0; j<coursesInCategoryData.length(); j++) {
                                JSONObject coursesInCategoryArray = new JSONObject(coursesInCategoryData.getString(j));
                                Object courseInCategoryName = coursesInCategoryArray.get("name");
                                categoryCoursesList.add(courseInCategoryName.toString());
                            }
                            childContent.put(parentHeaderInformation.get(i), categoryCoursesList);
                            Log.d("childContent", childContent.toString());
                        }

                        JSONArray soonCoursesData = new JSONArray(courses.getSoonCourses());
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        final int width = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT) - 25;
                        ImageView imageItem;
                        TextView titleTex,descTex;
                        for (int i=0; i<soonCoursesData.length(); i++) {
                            JSONObject soonCoursesArray = new JSONObject(soonCoursesData.getString(i));
                            Object name = soonCoursesArray.get("name");
                            Object description = soonCoursesArray.get("description");

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
                            titleTex.setText(name.toString());
                            titleTex.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            titleTex.setPadding(30, 20, 30, 20);
                            titleTex.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));

                            linearLayoutTitle.addView(titleTex);

                            descTex = new TextView(getActivity().getApplicationContext());
                            descTex.setText(description.toString());
                            descTex.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            descTex.setPadding(30, 30, 30, 30);
                            descTex.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 300));

                            linearLayoutChild.addView(descTex);

                            imageItem = new ImageView(getActivity().getApplicationContext());
                            imageItem.setImageResource(R.drawable.background2);
                            imageItem.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 300));

                            linearLayoutChild.addView(imageItem);

                            linearLayoutTitle.addView(linearLayoutChild);
                            linearLayoutParent.addView(linearLayoutTitle);
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

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableList);
        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(getActivity().getApplicationContext(), parentHeaderInformation, childContent);
        expandableListView.setAdapter(expandableListViewAdapter);
        expandableListView.setIndicatorBounds(expandableListView.getWidth(), expandableListView.getRight() - 40);
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
}
