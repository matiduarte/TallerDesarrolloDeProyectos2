package fiuba.tallerdeproyectos2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fiuba.tallerdeproyectos2.Activities.ExamActivity;
import fiuba.tallerdeproyectos2.Activities.FinalExamActivity;
import fiuba.tallerdeproyectos2.Activities.UnitDetailsActivity;
import fiuba.tallerdeproyectos2.Adapters.UnitRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.Models.UnitsCardViewData;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseUnitiesFragment extends Fragment{

    private RecyclerView.Adapter adapter;
    ArrayList units = new ArrayList<>();
    ArrayList activeUnits = new ArrayList();
    Boolean showExam = false;
    private Boolean isSubscribed, passFinalExam, isFinalExamAvailable, finalExamTimeFinished, examTimeFinished;
    private Integer unitId, courseId, sessionId;
    private String courseUnities;
    private Float finalExamResult;
    Button examButton, passExamButton;
    List<CourseUnitiesFragment.CourseUnitiesInfo> courseUnitiesInfos = new ArrayList<>();
    String courseName;


    public class CourseUnitiesInfo{
        public Integer id;
        public Boolean examTimeFinished;

        CourseUnitiesInfo(Integer id, Boolean examTimeFinished){
            this.id = id;
            this.examTimeFinished = examTimeFinished;
        }
    }

    public CourseUnitiesFragment() {}

    public static CourseUnitiesFragment newInstance(Integer courseId, Integer sessionId, Boolean isSubscribed, String courseUnities,
                                                    Boolean passFinalExam, Float finalExamResult, Boolean isFinalExamAvailable, Boolean examTimeFinished, String courseName) {
        CourseUnitiesFragment courseUnitiesFragment = new CourseUnitiesFragment();
        Bundle args = new Bundle();
        args.putInt("courseId", courseId);
        args.putInt("sessionId", sessionId);
        args.putBoolean("isSubscribed", isSubscribed);
        args.putString("courseUnities", courseUnities);
        args.putBoolean("passFinalExam", passFinalExam);
        args.putFloat("finalExamResult", finalExamResult);
        args.putBoolean("isFinalExamAvailable", isFinalExamAvailable);
        args.putBoolean("examTimeFinished", examTimeFinished);
        args.putString("courseName", courseName);
        courseUnitiesFragment.setArguments(args);
        return courseUnitiesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            courseId = getArguments().getInt("courseId");
            sessionId = getArguments().getInt("sessionId");
            isSubscribed = getArguments().getBoolean("isSubscribed");
            courseUnities = getArguments().getString("courseUnities");
            passFinalExam = getArguments().getBoolean("passFinalExam");
            finalExamResult = getArguments().getFloat("finalExamResult");
            isFinalExamAvailable = getArguments().getBoolean("isFinalExamAvailable");
            finalExamTimeFinished = getArguments().getBoolean("examTimeFinished");
            courseName = getArguments().getString("courseName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_unities, container, false);
        examButton = (Button) rootView.findViewById(R.id.final_exam_btn);
        passExamButton = (Button) rootView.findViewById(R.id.pass_final_exam_btn);

        try {
            JSONArray courseUnitiesData = new JSONArray(courseUnities);
            courseUnitiesInfos.clear();
            if(courseUnitiesData.length() > 0){
                for (int i=0; i<courseUnitiesData.length(); i++) {
                    JSONObject courseUnitiesArray = new JSONObject(courseUnitiesData.getString(i));
                    Boolean isActive = courseUnitiesArray.getBoolean("isActive");
                    if(isActive){
                        activeUnits.add(i);
                    }
                    courseUnitiesInfos.add(new CourseUnitiesFragment.CourseUnitiesInfo(courseUnitiesArray.getInt("id"),courseUnitiesArray.getBoolean("examTimeFinished")));
                    if(!isSubscribed || isActive){
                        UnitsCardViewData obj = new UnitsCardViewData(courseUnitiesArray.getString("name"), courseUnitiesArray.getString("description"), courseUnitiesArray.getString("id"));
                        units.add(i, obj);
                    }
                }
            }

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            if (recyclerView != null) {
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new UnitRecyclerViewAdapter(units);
                recyclerView.setAdapter(adapter);
            }

            ((UnitRecyclerViewAdapter) adapter).setOnItemClickListener(new UnitRecyclerViewAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    TextView tv = (TextView) v.findViewById(R.id.unit_id);
                    unitId = Integer.valueOf(tv.getText().toString());
                    if(activeUnits.contains(position+1)){
                        showExam = true;
                    }
                    for (int i=0; i < courseUnitiesInfos.size();i++){
                        if(courseUnitiesInfos.get(i).id == unitId){
                            examTimeFinished = courseUnitiesInfos.get(i).examTimeFinished;
                        }
                    }
                    if(isSubscribed){
                        navigateToUnitDetailsActivity();
                    } else {
                        Toast.makeText(getContext(), R.string.unit_detail_alert, Toast.LENGTH_LONG).show();
                    }
                }
            });
            if(isFinalExamAvailable) {
                if (examButton != null) {
                    examButton.setVisibility(View.VISIBLE);
                }
            } else if(passFinalExam){
                if (passExamButton != null) {
                    if(finalExamResult >= 6){
                        passExamButton.setBackgroundColor(getResources().getColor(R.color.approveExam));
                    } else {
                        passExamButton.setBackgroundColor(getResources().getColor(R.color.disapproveExam));
                    }
                    passExamButton.setText(getString(R.string.pass_final_exam) + finalExamResult);
                    passExamButton.setVisibility(View.VISIBLE);
                }
            } else if(finalExamTimeFinished) {
                passExamButton.setBackgroundColor(getResources().getColor(R.color.disapproveExam));
                passExamButton.setText("Examen Final No Rendido");
                passExamButton.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        examButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFinalExamActivity();
            }
        });

        return rootView;
    }

    private void navigateToUnitDetailsActivity(){
        Intent intent = new Intent(getContext(), UnitDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("unitId", unitId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("showExam", showExam);
        intent.putExtra("sessionId", sessionId);
        intent.putExtra("examTimeFinished", examTimeFinished);
        startActivity(intent);
    }

    private void navigateToFinalExamActivity(){
        Intent intent = new Intent(getContext(), FinalExamActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("unitId", unitId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("courseName", courseName);
        intent.putExtra("sessionId", sessionId);
        startActivity(intent);
    }
}
