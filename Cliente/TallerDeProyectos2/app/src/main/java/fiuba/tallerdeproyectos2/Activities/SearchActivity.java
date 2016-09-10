package fiuba.tallerdeproyectos2.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fiuba.tallerdeproyectos2.Models.Search;
import fiuba.tallerdeproyectos2.Models.SearchResponse;
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
            String query = intent.getStringExtra(SearchManager.QUERY);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<SearchResponse> call = apiService.getSearchCourses(query);
            call.enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse>call, Response<SearchResponse> response) {
                    List<Search> data = response.body().getData();
                    String success =response.body().getSuccess();
                    String message =response.body().getMessage();
                    Log.d(TAG, "Courses match search: " + data.size());
                    Log.d(TAG, "Courses match: " + data.toString());
                    Log.d(TAG, "Success: " + success);
                    Log.d(TAG, "Message: " + message);

                    if(success == "true"){
                        for (int i=0; i< data.size(); i++) {
                            coursesList.add("Taller 2");
                        }
                    } else {
                        coursesList.add("No results for your search");
                    }
                    ArrayAdapter<String> courses = new ArrayAdapter<>(getApplicationContext(),R.layout.list_item,coursesList);
                    lv.setAdapter(courses);
                }

                @Override
                public void onFailure(Call<SearchResponse>call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
    }
}


