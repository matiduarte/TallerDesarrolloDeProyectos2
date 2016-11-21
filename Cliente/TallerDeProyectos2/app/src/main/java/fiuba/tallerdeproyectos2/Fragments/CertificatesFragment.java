package fiuba.tallerdeproyectos2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fiuba.tallerdeproyectos2.Activities.CertificateTemplateActivity;
import fiuba.tallerdeproyectos2.Activities.MainActivity;
import fiuba.tallerdeproyectos2.Activities.SessionManagerActivity;
import fiuba.tallerdeproyectos2.Activities.UnitDetailsActivity;
import fiuba.tallerdeproyectos2.Adapters.CertificateRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Adapters.RecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.Certificate;
import fiuba.tallerdeproyectos2.Models.CertificateCardViewData;
import fiuba.tallerdeproyectos2.Models.Exam;
import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CertificatesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = CoursesFragment.class.getSimpleName();
    ArrayList certificates = new ArrayList<>();
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView noCertificates;
    private RecyclerView.Adapter adapter;
    SessionManagerActivity session;
    HashMap<String, String> user;
    Integer studentId;
    String studentName, teacherName, courseName, result;
    List<CertificatesFragment.CertificatesInfo> certificatesInfos = new ArrayList<>();


    public class CertificatesInfo{
        public Integer id;
        public String courseName;

        CertificatesInfo(Integer id, String courseName){
            this.id = id;
            this.courseName = courseName;
        }
    }

    public CertificatesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_certificates, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        noCertificates = (TextView) rootView.findViewById(R.id.no_certificates);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        session = new SessionManagerActivity(getContext());
        session.checkLogin();
        user = session.getUserDetails();
        studentId = Integer.valueOf(user.get(SessionManagerActivity.KEY_ID));
        studentName = user.get(SessionManagerActivity.KEY_NAME) + " " + user.get(SessionManagerActivity.KEY_SURNAME);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getCertificatesByUser(studentId);
        call.enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                try {
                    Boolean success = response.body().getSuccess();
                    if (success.equals(true)) {
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        Certificate certificateData = gson.fromJson(data, Certificate.class);
                        JSONArray certifications = new JSONArray(certificateData.getCertifications());
                        certificatesInfos.clear();
                        for (int i=0; i < certifications.length(); i++) {
                            JSONObject certificateArray = new JSONObject(certifications.getString(i));
                            teacherName = certificateArray.getString("teachertName");
                            result = certificateArray.getString("result");
                            certificatesInfos.add(new CertificatesFragment.CertificatesInfo(i,certificateArray.getString("courseName")));
                            CertificateCardViewData obj = new CertificateCardViewData(certificateArray.getString("courseName"));
                            certificates.add(i, obj);
                        }
                    }

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new CertificateRecyclerViewAdapter(certificates);
                    recyclerView.setAdapter(adapter);

                    if(certificates.isEmpty()){
                        noCertificates.setVisibility(View.VISIBLE);
                    }

                    ((CertificateRecyclerViewAdapter) adapter).setOnItemClickListener(new CertificateRecyclerViewAdapter.MyClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            TextView tv = (TextView) v.findViewById(R.id.filename);
                            courseName = tv.getText().toString();
                            navigateToCertificateTemplateActivity();
                        }
                    });

                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

        return rootView;
    }

    @Override
    public void onRefresh() {
        certificates.clear();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = apiService.getCertificatesByUser(studentId);
        call.enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                try {
                    Boolean success = response.body().getSuccess();
                    if (success.equals(true)) {
                        String data = response.body().getData();
                        Gson gson = new Gson();
                        Certificate certificateData = gson.fromJson(data, Certificate.class);
                        JSONArray certifications = new JSONArray(certificateData.getCertifications());
                        for (int i=0; i < certifications.length(); i++) {
                            JSONObject certificateArray = new JSONObject(certifications.getString(i));
                            CertificateCardViewData obj = new CertificateCardViewData(certificateArray.getString("courseName"));
                            certificates.add(i, obj);
                        }
                    }

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new CertificateRecyclerViewAdapter(certificates);
                    recyclerView.setAdapter(adapter);

                    if(certificates.isEmpty()){
                        noCertificates.setVisibility(View.VISIBLE);
                    }

                    ((CertificateRecyclerViewAdapter) adapter).setOnItemClickListener(new CertificateRecyclerViewAdapter.MyClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            navigateToCertificateTemplateActivity();

                        }
                    });

                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }

    private void navigateToCertificateTemplateActivity(){
        Intent intent = new Intent(getContext(), CertificateTemplateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("teacherName", teacherName);
        intent.putExtra("studentName", studentName);
        intent.putExtra("result", result);
        intent.putExtra("courseName", courseName);
        startActivity(intent);
    }
}
