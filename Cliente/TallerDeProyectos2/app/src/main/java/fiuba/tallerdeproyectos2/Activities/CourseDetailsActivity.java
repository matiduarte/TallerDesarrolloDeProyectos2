package fiuba.tallerdeproyectos2.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fiuba.tallerdeproyectos2.Adapters.UnitRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Fragments.CourseCommentsFragment;
import fiuba.tallerdeproyectos2.Fragments.CourseInformationFragment;
import fiuba.tallerdeproyectos2.Fragments.CourseUnitiesFragment;
import fiuba.tallerdeproyectos2.Models.CourseData;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.Models.UnitsCardViewData;
import fiuba.tallerdeproyectos2.Notifications.Config;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbar;
    private static final String TAG = CourseDetailsActivity.class.getSimpleName();
    Integer sessionId, studentId, courseId;
    SessionManagerActivity session;
    HashMap<String, String> user;
    String courseName, teacherName, description, date, courseUnities;
    Boolean isSubscribed;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", 0);

        session = new SessionManagerActivity(getApplicationContext());
        session.checkLogin();
        user = session.getUserDetails();
        studentId = Integer.valueOf(user.get(SessionManagerActivity.KEY_ID));

        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getCourseDataById(courseId, studentId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse>call, Response<ServerResponse> response) {
                try {
                    Boolean success =response.body().getSuccess();
                    if(success.equals(true)){
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        CourseData course = gson.fromJson(data, CourseData.class);
                        JSONObject courseData = new JSONObject(course.getCourseData());
                        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                        collapsingToolbar.setTitle(courseData.getString("name"));
                        courseName = courseData.getString("name");
                        teacherName = courseData.getString("teacherName");
                        description = courseData.getString("description");
                        ImageView header = (ImageView) findViewById(R.id.header);
                        if(courseData.has("pictureUrl")) {
                            new DownloadImageTask(header).execute(ApiClient.BASE_URL + courseData.getString("pictureUrl"));
                        }
                        isSubscribed = courseData.getBoolean("isSubscribed");
                        JSONArray courseSessionsData = new JSONArray(courseData.getString("courseSessions"));
                        if(courseSessionsData.length() > 0){
                            JSONObject courseSessionsArray = new JSONObject(courseSessionsData.getString(0));
                            date = courseSessionsArray.getString("date");
                            sessionId = Integer.valueOf(courseSessionsArray.getString("id"));
                        }

                        courseUnities = courseData.getString("courseUnities");

                        viewPager = (ViewPager) findViewById(R.id.viewpager);
                        setupViewPager(viewPager);

                        tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(viewPager);

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_chat:
                if(isSubscribed){
                    navigateToCourseChatActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Tenes que estar inscripto para poder acceder al foro del curso!", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e(String.valueOf(R.string.error), e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
         protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
    }

    private void navigateToCourseChatActivity(){
        Intent intent = new Intent(getApplicationContext(), CourseChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("courseId", courseId);
        intent.putExtra("studentId", studentId);
        intent.putExtra("sessionId", sessionId);
        intent.putExtra("courseName", courseName);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        navigateToMainActivity();
    }

    private void navigateToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CourseInformationFragment.newInstance(courseId, sessionId, description, teacherName, isSubscribed, date), "Informacion");
        adapter.addFragment(CourseUnitiesFragment.newInstance(courseId, sessionId, isSubscribed, courseUnities), "Contenido");
        adapter.addFragment(new CourseCommentsFragment(), "Opiniones");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
