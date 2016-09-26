package fiuba.tallerdeproyectos2.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fiuba.tallerdeproyectos2.Models.Search;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();
    List<String> coursesList = new ArrayList<>();
    private ListView lv;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lv = (ListView) findViewById(R.id.list_view);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ServerResponse> call = apiService.getSearchCourses(query);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse>call, Response<ServerResponse> response) {
                    try {
                        Boolean success =response.body().getSuccess();
                        if(success.equals(true)){
                            String data = response.body().getData();
                            Gson gson = new Gson();
                            Search courses = gson.fromJson(data, Search.class);
                            JSONArray coursesData = new JSONArray(courses.getCoursesData());
                            for (int i=0; i<coursesData.length(); i++) {
                                JSONObject coursesArray = new JSONObject(coursesData.getString(i));
                                coursesList.add(coursesArray.getString("name"));
                            }
                        } else {
                            Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(homeIntent);
                            finish();
                            Toast.makeText(getApplicationContext(), R.string.search_no_results, Toast.LENGTH_LONG).show();
                        }
                        ArrayAdapter<String> courses = new ArrayAdapter<>(getApplicationContext(),R.layout.list_item,coursesList);
                        lv.setAdapter(courses);
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
    }
}


