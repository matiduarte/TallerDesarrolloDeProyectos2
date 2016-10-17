package fiuba.tallerdeproyectos2.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fiuba.tallerdeproyectos2.Fragments.HomeFragment;
import fiuba.tallerdeproyectos2.Models.Courses;
import fiuba.tallerdeproyectos2.Models.Exam;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamActivity extends AppCompatActivity {

    private static final String TAG = ExamActivity.class.getSimpleName();
    LinearLayout exam;
    List selchkboxlist = new ArrayList();
    List<AnswersInfo> answersInfo = new ArrayList<>();
    List<ExamInfo> examInfo = new ArrayList<>();
    Integer unitId, courseId;
    public class AnswersInfo{
        public Integer id;
        public Boolean isCorrect;

        AnswersInfo(Integer id, Boolean isCorrect){
            this.id = id;
            this.isCorrect = isCorrect;
        }
    }

    public class ExamInfo{
        public Integer questionId;
        public Integer correctAnswers;
        public List<AnswersInfo> answersInfo;

        ExamInfo(Integer questionId, Integer correctAnswers, List<AnswersInfo> answersInfo){
            this.questionId = questionId;
            this.correctAnswers = correctAnswers;
            this.answersInfo = answersInfo;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        unitId = intent.getIntExtra("unitId", 0);
        courseId = intent.getIntExtra("courseId", 0);
        String unitName = intent.getStringExtra("unitName");

        setTitle("Examen - Unidad " + unitName);

        exam = (LinearLayout) findViewById(R.id.exam);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getUnitExam(unitId);
        call.enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                try {
                    Boolean success = response.body().getSuccess();
                    if (success.equals(true)) {
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        Exam examData = gson.fromJson(data, Exam.class);
                        JSONArray questions = new JSONArray(examData.getQuestions());
                        Integer correctAnswers = 0;
                        Log.d("questions", questions.toString());
                        for (int i=0; i < questions.length(); i++) {
                            JSONObject questionArray = new JSONObject(questions.getString(i));
                            String question = questionArray.getString("question");
                            Log.d("question", question);
                            TextView textView = new TextView(ExamActivity.this);
                            textView.setText(question);
                            textView.setTextSize(15);
                            textView.setTypeface(null, Typeface.BOLD_ITALIC);
                            textView.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                            exam.addView(textView);

                            JSONArray answers = new JSONArray(questionArray.getString("answers"));
                            Log.d("answers", answers.toString());
                            for (int j=0; j < answers.length(); j++) {
                                JSONObject answerArray = new JSONObject(answers.getString(j));
                                String answer = answerArray.getString("answer");
                                Boolean isCorrect = answerArray.getBoolean("isCorrect");
                                Log.d("answer", answer);
                                Log.d("isCorrect", isCorrect.toString());
                                if(isCorrect){
                                    correctAnswers++;
                                }
                                answersInfo.add(new AnswersInfo(answerArray.getInt("id"),isCorrect));
                                CheckBox checkBox = new CheckBox(ExamActivity.this);
                                checkBox.setText(answer);
                                checkBox.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                                checkBox.setId(answerArray.getInt("id"));
                                exam.addView(checkBox);

                                checkBox.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Integer chk = null;
                                        chk = v.getId();
                                        if (((CheckBox) v).isChecked()) {
                                            selchkboxlist.add(chk);

                                        } else {
                                            selchkboxlist.remove(chk);
                                        }
                                    }
                                });
                            }
                            examInfo.add(new ExamInfo(questionArray.getInt("id"), correctAnswers, answersInfo));
                        }
                    }

                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    public void finishExam(View view){
        double points = 0;
        Integer totalPoints = examInfo.size();
        for (int i = 0; i < examInfo.size(); i++){
            Integer correctResponses = 0;
            Integer correctAnswers = examInfo.get(i).correctAnswers;
            List<AnswersInfo> answers = examInfo.get(i).answersInfo;
            for (int j = 0; j < answers.size(); j++){
                if(selchkboxlist.contains(answers.get(j).id)){
                    if(answers.get(j).isCorrect){
                        correctResponses++;
                    }
                }
            }
            points += correctResponses/correctAnswers;
        }
        double nota = (points/totalPoints)*10;
        Toast.makeText(getApplicationContext(), "La nota del examen es: " + nota, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), UnitDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("unitId", unitId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("passExam", true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), UnitDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("unitId", unitId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("showExam", true);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
