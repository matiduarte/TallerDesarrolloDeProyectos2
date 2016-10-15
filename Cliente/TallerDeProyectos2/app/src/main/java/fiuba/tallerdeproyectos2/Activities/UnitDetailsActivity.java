package fiuba.tallerdeproyectos2.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        html = (TextView) findViewById(R.id.html);
        videoView = (VideoView) findViewById(R.id.video);
        mediaController = new MediaController(this);

        Intent intent = getIntent();
        int unitId = intent.getIntExtra("unitId", 0);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getUnitDataById(unitId);
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
                        html.setText(Html.fromHtml(unitData.getString("html")));
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
                            unitData.getString("subtitles");
                            videoView.setVisibility(View.VISIBLE);
                            videoView.start();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
