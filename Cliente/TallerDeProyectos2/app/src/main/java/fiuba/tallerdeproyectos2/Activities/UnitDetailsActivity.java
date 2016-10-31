package fiuba.tallerdeproyectos2.Activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.Models.UnitData;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnitDetailsActivity extends AppCompatActivity {

    private static final String TAG = UnitDetailsActivity.class.getSimpleName();
    VideoView videoView;
    MediaController mediaController;
    TextView html;
    Integer unitId, courseId, studentId, sessionId;
    String unitName;
    Boolean showExam, passExam, isPractice;
    HashMap<String, String> subtitles;
    Spinner subtitlesSpinner;
    Float nota;
    SessionManagerActivity session;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        html = (TextView) findViewById(R.id.html);
        videoView = (VideoView) findViewById(R.id.video);
        mediaController = new MediaController(this);

        subtitlesSpinner = (Spinner) findViewById(R.id.subtitles_spinner);
        subtitlesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                videoView.resume();
                String languageSelected = subtitlesSpinner.getSelectedItem().toString();
                String subtitleUrl = subtitles.get(languageSelected);
                if(subtitleUrl != null){
                    new RetrieveSubtitlesTask(videoView, getApplicationContext()).execute(subtitleUrl);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        subtitles = new HashMap<>();

        Intent intent = getIntent();
        unitId = intent.getIntExtra("unitId", 0);
        courseId = intent.getIntExtra("courseId", 0);
        sessionId = intent.getIntExtra("sessionId", 0);
        showExam = intent.getBooleanExtra("showExam", false);
        //nota = intent.getFloatExtra("nota", 0);

        session = new SessionManagerActivity(getApplicationContext());
        session.checkLogin();
        user = session.getUserDetails();
        studentId = Integer.valueOf(user.get(SessionManagerActivity.KEY_ID));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getUnitDataById(unitId, studentId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse>call, Response<ServerResponse> response) {
                try {
                    Boolean success =response.body().getSuccess();
                    if(success.equals(true)){
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        UnitData unit = gson.fromJson(data, UnitData.class);
                        JSONObject unitData = new JSONObject(unit.getUnitData());
                        setTitle(unitData.getString("name"));
                        unitName = unitData.getString("name");
                        html.setText(Html.fromHtml(unitData.getString("html")));

                        JSONArray subsArray = new JSONArray(unit.getSubtitles());

                        if(subsArray.length() > 0){
                            List<String> languages = new ArrayList<>();
                            languages.add(getString(R.string.subtitles_selector));
                            subtitlesSpinner.setVisibility(View.VISIBLE);
                            for (int i=0; i< subsArray.length(); i++) {
                                String subUrl = subsArray.getString(i);
                                String language = getLanguageSubtitleUrl(subUrl);
                                subtitles.put(language, subUrl);
                                languages.add(language);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    getApplicationContext(), android.R.layout.simple_spinner_item, languages);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subtitlesSpinner.setAdapter(adapter);
                        }

                        if(unitData.has("videoUrl")){
                            videoView.setVideoPath(ApiClient.BASE_URL + unitData.getString("videoUrl"));
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                                        @Override
                                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                            videoView.setMediaController(mediaController);
                                            mediaController.setAnchorView(videoView);
                                        }
                                    });
                                }
                            });
                            videoView.setVisibility(View.VISIBLE);
                            videoView.start();
                        }
                        passExam = unitData.getBoolean("passExam");
                        nota = Float.valueOf(unitData.getString("examResult"));
                        if(passExam){
                            Button passExamButton = (Button)findViewById(R.id.exam_pass_btn);
                            if (passExamButton != null) {
                                if(nota >= 6){
                                    passExamButton.setBackgroundColor(getResources().getColor(R.color.approveExam));
                                }
                                passExamButton.setText(getString(R.string.pass_exam) + nota);
                                passExamButton.setVisibility(View.VISIBLE);
                            }
                        } else if(showExam){
                            Button examButton = (Button)findViewById(R.id.exam_btn);
                            if (examButton != null) {
                                examButton.setVisibility(View.VISIBLE);
                                isPractice = false;
                            }
                        } else {
                            Button practiceExamButton = (Button)findViewById(R.id.practice_exam_btn);
                            if (practiceExamButton != null) {
                                practiceExamButton.setVisibility(View.VISIBLE);
                                isPractice = true;
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
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

    private String getLanguageSubtitleUrl(String subUrl) {
        String[] parts = subUrl.split("/");
        return parts[parts.length - 1];
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    public void examButtonClick(View view){
        navigateToExamActivity();
    }

    public void practiceExamButtonClick(View view){
        navigateToExamActivity();
    }

    private void navigateToExamActivity(){
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("unitId", unitId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("unitName", unitName);
        intent.putExtra("sessionId", sessionId);
        intent.putExtra("isPractice", isPractice);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    class RetrieveSubtitlesTask extends AsyncTask<String, Void, InputStream> {

        private Exception exception;
        private VideoView videoView;
        private Context context;
        private Locale locale;

        RetrieveSubtitlesTask(VideoView videoView, Context c){
            this.videoView = videoView;
            this.context = c;
        }

        protected InputStream doInBackground(String... url) {
            try {
                return new URL(ApiClient.BASE_URL + url[0]).openStream();
            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }

        protected void onPostExecute(InputStream subsInput) {
            videoView.addSubtitleSource(subsInput , MediaFormat.createSubtitleFormat("text/vtt", Locale.CANADA.getLanguage()));
        }
    }
}
