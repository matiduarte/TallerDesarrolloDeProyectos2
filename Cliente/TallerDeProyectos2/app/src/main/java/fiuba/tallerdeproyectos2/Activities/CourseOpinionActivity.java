package fiuba.tallerdeproyectos2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.HashMap;

import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseOpinionActivity extends AppCompatActivity {

    public RatingBar ratingBar;
    EditText opinionET;
    SessionManagerActivity session;
    HashMap<String, String> user;
    Integer studentId, courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_opinion);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        opinionET = (EditText) findViewById(R.id.opinion);
        session = new SessionManagerActivity(getApplicationContext());
        session.checkLogin();
        user = session.getUserDetails();
        studentId = Integer.valueOf(user.get(SessionManagerActivity.KEY_ID));
        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", 2);
        setTitle("Opinion");
    }

    public void sendRatingAndOpinion(View view){
        String opinion = opinionET.getText().toString();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.postCourseComments(studentId, courseId, opinion);
        call.enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Boolean success = response.body().getSuccess();
                if (success.equals(true)) {

                    ApiInterface apiService2 = ApiClient.getClient().create(ApiInterface.class);
                    Call<ServerResponse> call2 = apiService2.postCourseCalifications(studentId, courseId, (int) ratingBar.getRating());
                    call2.enqueue(new Callback<ServerResponse>() {

                        @Override
                        public void onResponse(Call<ServerResponse> call2, Response<ServerResponse> response) {
                            Boolean success = response.body().getSuccess();
                            if (success.equals(true)) {
                                Toast.makeText(getApplicationContext(), "Tu opinion ha sido guardada satisfactoriamente! Gracias!", Toast.LENGTH_LONG).show();
                                navigateToMainActivity();
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call2, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call2, Throwable t) {

            }
        });
    }

    public void noOpinion(View view){
        navigateToMainActivity();
    }

    private void navigateToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
