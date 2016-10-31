package fiuba.tallerdeproyectos2.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import fiuba.tallerdeproyectos2.Adapters.CourseChatRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Adapters.UnitRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.CourseChatCardViewData;
import fiuba.tallerdeproyectos2.Models.Exam;
import fiuba.tallerdeproyectos2.Models.Forum;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.Models.UnitsCardViewData;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseChatActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = CourseChatActivity.class.getSimpleName();
    private RecyclerView.Adapter adapter;
    ArrayList chats = new ArrayList<>();
    Integer courseId, sessionId, studentId;
    String courseName;
    EditText messageET;
    private View loadingProgress;
    private View chatFormView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        messageET = (EditText) findViewById(R.id.message_txt);
        chatFormView = findViewById(R.id.swipe_refresh_layout);
        loadingProgress = findViewById(R.id.loading_progress);

        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", 0);
        sessionId = intent.getIntExtra("sessionId", 0);
        studentId = intent.getIntExtra("studentId", 0);
        courseName = intent.getStringExtra("courseName");

        setTitle("Foro de " + courseName);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        showProgress(true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getForum(sessionId);
        call.enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                try {
                    Boolean success = response.body().getSuccess();
                    if (success.equals(true)) {
                        showProgress(false);
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        Forum forumData = gson.fromJson(data, Forum.class);
                        JSONArray messages = new JSONArray(forumData.getMessages());
                        Log.d("messages", messages.toString());
                        for (int i = 0; i < messages.length(); i++) {
                            JSONObject messageArray = new JSONObject(messages.getString(i));
                            String message = messageArray.getString("message");
                            String time = messageArray.getString("time");
                            String name = messageArray.getString("studentFirstName");
                            String surname = messageArray.getString("studentLastName");
                            CourseChatCardViewData obj = new CourseChatCardViewData(name, surname, time, message);
                            chats.add(i, obj);
                        }

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);

                        adapter = new CourseChatRecyclerViewAdapter(chats);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    private void reloadActivity(){
        Intent intent = new Intent(getApplicationContext(), CourseChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("courseId", courseId);
        intent.putExtra("studentId", studentId);
        intent.putExtra("sessionId", sessionId);
        intent.putExtra("courseName", courseName);
        startActivity(intent);
    }

    public void sendMessage(View view){
        String message = messageET.getText().toString();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.postForumMessage(studentId, sessionId, message);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse>call, Response<ServerResponse> response) {
                Boolean success =response.body().getSuccess();
                if(success.equals(true)){
                    reloadActivity();
                    Toast.makeText(getApplicationContext(), "El mensaje fue enviado al foro satisfactoriamente!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            chatFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            chatFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    chatFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            loadingProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            loadingProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadingProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            loadingProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            loadingProgress.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        chats.clear();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getForum(sessionId);
        call.enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                try {
                    Boolean success = response.body().getSuccess();
                    if (success.equals(true)) {
                        showProgress(false);
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        Forum forumData = gson.fromJson(data, Forum.class);
                        JSONArray messages = new JSONArray(forumData.getMessages());
                        Log.d("messages", messages.toString());
                        for (int i = 0; i < messages.length(); i++) {
                            JSONObject messageArray = new JSONObject(messages.getString(i));
                            String message = messageArray.getString("message");
                            String time = messageArray.getString("time");
                            String name = messageArray.getString("studentFirstName");
                            String surname = messageArray.getString("studentLastName");
                            CourseChatCardViewData obj = new CourseChatCardViewData(name, surname, time, message);
                            chats.add(i, obj);
                        }

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);

                        adapter = new CourseChatRecyclerViewAdapter(chats);
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
