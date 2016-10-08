package fiuba.tallerdeproyectos2.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import fiuba.tallerdeproyectos2.Adapters.ExpandableListViewAdapter;
import fiuba.tallerdeproyectos2.Adapters.UnitRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.CourseData;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.Models.UnitsCardViewData;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private static final String TAG = CourseDetailsActivity.class.getSimpleName();
    private RecyclerView.Adapter adapter;
    ArrayList units = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Intent intent = getIntent();
        int itemId = intent.getIntExtra("courseId", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getCourseDataById(itemId);
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
                        TextView courseDesc = (TextView) findViewById(R.id.course_description);
                        courseDesc.setText(courseData.getString("description"));
                        TextView teacherName = (TextView) findViewById(R.id.course_teacher_name);
                        teacherName.setText(courseData.getString("teacherName"));
                        ImageView header = (ImageView) findViewById(R.id.header);
                        if(courseData.has("pictureUrl")) {
                            new DownloadImageTask(header).execute(ApiClient.BASE_URL + courseData.getString("pictureUrl"));
                        }

                        JSONArray courseSessionsData = new JSONArray(courseData.getString("courseSessions"));
                        TextView courseStartDate = (TextView) findViewById(R.id.start_date);
                        TextView courseInscriptionDates = (TextView) findViewById(R.id.inscription_dates);
                        if(courseSessionsData.length() > 0){
                            JSONObject courseSessionsArray = new JSONObject(courseSessionsData.getString(0));
                            String startDateString = courseSessionsArray.getString("date");
                            courseStartDate.setText(startDateString);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
                            Date startDate = dateFormat.parse(startDateString);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(startDate);
                            calendar.add(Calendar.DAY_OF_YEAR, -7);
                            Date startInscriptionDate = calendar.getTime();
                            String startInscriptionDateString = dateFormat.format(startInscriptionDate);
                            calendar.add(Calendar.DAY_OF_YEAR, +9);
                            Date finishInscriptionDate = calendar.getTime();
                            String finishInscriptionDateString = dateFormat.format(finishInscriptionDate);
                            courseInscriptionDates.setText(startInscriptionDateString + " - " + finishInscriptionDateString);
                        } else {
                            TextView courseStartDateTxt = (TextView) findViewById(R.id.start_date_txt);
                            TextView courseInscriptionDatesTxt = (TextView) findViewById(R.id.inscription_label);
                            courseStartDateTxt.setVisibility(View.GONE);
                            courseStartDate.setVisibility(View.GONE);
                            courseInscriptionDatesTxt.setVisibility(View.GONE);
                            courseInscriptionDates.setVisibility(View.GONE);
                        }

                        JSONArray courseUnitiesData = new JSONArray(courseData.getString("courseUnities"));
                        Log.d("courseUnities", courseUnitiesData.toString());
                        Log.d("courseUnitiesLenght", String.valueOf(courseUnitiesData.length()));

                        if(courseUnitiesData.length() > 0){
                            TextView unitsHeader = (TextView) findViewById(R.id.units_header);
                            unitsHeader.setVisibility(View.VISIBLE);
                            View topLine = findViewById(R.id.top_line);
                            topLine.setVisibility(View.VISIBLE);
                            View bottomLine = findViewById(R.id.bottom_line);
                            bottomLine.setVisibility(View.VISIBLE);
                            for (int i=0; i<courseUnitiesData.length(); i++) {
                                JSONObject courseUnitiesArray = new JSONObject(courseUnitiesData.getString(i));

                                Log.d("unitName", courseUnitiesArray.getString("name"));
                                Log.d("unitDesc", courseUnitiesArray.getString("description"));

                                UnitsCardViewData obj = new UnitsCardViewData(courseUnitiesArray.getString("name"), courseUnitiesArray.getString("description"), courseUnitiesArray.getString("id"));
                                units.add(i, obj);
                            }
                        }

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);

                        adapter = new UnitRecyclerViewAdapter(units);
                        recyclerView.setAdapter(adapter);

                        ((UnitRecyclerViewAdapter) adapter).setOnItemClickListener(new UnitRecyclerViewAdapter.MyClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Log.i(" Clicked on Item ", String.valueOf(position));
                                TextView tv = (TextView) v.findViewById(R.id.unit_id);
                                navigateToUnitDetailsActivity();
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                } catch (ParseException e) {
                    e.printStackTrace();
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
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void navigateToUnitDetailsActivity(){
        Intent intent = new Intent(getApplicationContext(), UnitDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
