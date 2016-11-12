package fiuba.tallerdeproyectos2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import fiuba.tallerdeproyectos2.Activities.CourseDetailsActivity;
import fiuba.tallerdeproyectos2.Activities.SessionManagerActivity;
import fiuba.tallerdeproyectos2.Models.CourseData;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseInformationFragment extends Fragment{

    SessionManagerActivity session;
    HashMap<String, String> user;
    private static final String TAG = CourseInformationFragment.class.getSimpleName();
    private Integer studentId, courseId, sessionId;
    TextView courseDesc, teacher, courseStartDate, courseInscriptionDates, courseStartDateTxt, courseInscriptionDatesTxt;
    private Boolean isSubscribed;
    Button inscriptionButton, unsubscriptionButton;
    private String teacherName, description, date;

    public CourseInformationFragment() {}

    public static CourseInformationFragment newInstance(Integer courseId, Integer sessionId, String description, String teacherName, Boolean isSubscribed, String date) {
        CourseInformationFragment courseInformationFragment = new CourseInformationFragment();
        Bundle args = new Bundle();
        args.putInt("courseId", courseId);
        args.putInt("sessionId", sessionId);
        args.putString("description", description);
        args.putString("teacherName", teacherName);
        args.putBoolean("isSubscribed", isSubscribed);
        args.putString("date", date);
        courseInformationFragment.setArguments(args);
        return courseInformationFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            courseId = getArguments().getInt("courseId");
            sessionId = getArguments().getInt("sessionId");
            description = getArguments().getString("description");
            teacherName = getArguments().getString("teacherName");
            isSubscribed = getArguments().getBoolean("isSubscribed");
            date = getArguments().getString("date");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_information, container, false);

        session = new SessionManagerActivity(getContext());
        session.checkLogin();
        user = session.getUserDetails();
        studentId = Integer.valueOf(user.get(SessionManagerActivity.KEY_ID));

        courseDesc = (TextView) rootView.findViewById(R.id.course_description);
        teacher = (TextView) rootView.findViewById(R.id.course_teacher_name);
        courseStartDate = (TextView) rootView.findViewById(R.id.start_date);
        courseInscriptionDates = (TextView) rootView.findViewById(R.id.inscription_dates);
        courseStartDateTxt = (TextView) rootView.findViewById(R.id.start_date_txt);
        courseInscriptionDatesTxt = (TextView) rootView.findViewById(R.id.inscription_label);
        inscriptionButton = (Button) rootView.findViewById(R.id.inscription_btn);
        unsubscriptionButton = (Button) rootView.findViewById(R.id.unsubscription_btn);

        if (courseDesc != null) {
            courseDesc.setText(description);
        }

        if (teacherName != null) {
            teacher.setText(teacherName);
        }
        if (courseStartDate != null) {
            courseStartDate.setText(date);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        try {
            startDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date startInscriptionDate = calendar.getTime();
        String startInscriptionDateString = dateFormat.format(startInscriptionDate);
        calendar.add(Calendar.DAY_OF_YEAR, +9);
        Date finishInscriptionDate = calendar.getTime();
        String finishInscriptionDateString = dateFormat.format(finishInscriptionDate);
        if (courseInscriptionDates != null) {
            courseInscriptionDates.setText(startInscriptionDateString + " - " + finishInscriptionDateString);
        }

        if(startInscriptionDate.compareTo(today) <= 0 && finishInscriptionDate.compareTo(today) >= 0
                &&!isSubscribed){
            if (inscriptionButton != null) {
                inscriptionButton.setVisibility(View.VISIBLE);
            }
        } else if(isSubscribed){
            if (unsubscriptionButton != null) {
                unsubscriptionButton.setVisibility(View.VISIBLE);
            }
        }

        Button inscriptionButton = (Button) rootView.findViewById(R.id.inscription_btn);
        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ServerResponse> call = apiService.postStudentSubscribe(studentId, sessionId);
                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse>call, Response<ServerResponse> response) {
                        Boolean success =response.body().getSuccess();
                        if(success.equals(true)){
                            FirebaseMessaging.getInstance().subscribeToTopic("course_" + courseId + "_" + sessionId);
                            refreshActivity();
                            Toast.makeText(getContext(), R.string.inscription_success, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse>call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
            }
        });

        Button unsubscriptionButton = (Button) rootView.findViewById(R.id.unsubscription_btn);
        unsubscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ServerResponse> call = apiService.postStudentUnsubscribe(studentId, sessionId);
                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse>call, Response<ServerResponse> response) {
                        Boolean success =response.body().getSuccess();
                        if(success.equals(true)){
                            FirebaseMessaging.getInstance().unsubscribeFromTopic("course_" + courseId + "_" + sessionId);
                            refreshActivity();
                            Toast.makeText(getContext(), R.string.unsubscription_success, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse>call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
            }
        });

        return rootView;
    }

    private void refreshActivity(){
        Intent intent = new Intent(getContext(), CourseDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }
}


