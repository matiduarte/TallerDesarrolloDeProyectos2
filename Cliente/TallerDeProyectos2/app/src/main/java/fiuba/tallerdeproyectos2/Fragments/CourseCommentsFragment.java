package fiuba.tallerdeproyectos2.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fiuba.tallerdeproyectos2.Activities.CourseChatActivity;
import fiuba.tallerdeproyectos2.Adapters.CourseChatRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.CourseChatCardViewData;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Utilities.Utilities;

public class CourseCommentsFragment extends Fragment {

    private String comments, califications;
    private static final String TAG = CourseCommentsFragment.class.getSimpleName();
    private RecyclerView.Adapter adapter;
    ArrayList courseComments = new ArrayList<>();
    TextView calificationTV, opinionTV;
    HorizontalBarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    int green = Color.parseColor("#74d600");
    int ligthGreen = Color.parseColor("#adff00");
    int yellow = Color.parseColor("#f6f610");
    int orange = Color.parseColor("#ffa500");
    int red = Color.parseColor("#ff1919");
    LinearLayout calificationLayout;
    Float averageCalification;
    Integer totalCalifications, totalOneStarCalification, totalTwoStarCalification, totalThreeStarCalification, totalFourStarCalification, totalFiveStarCalification;

    public CourseCommentsFragment() {}

    public static CourseCommentsFragment newInstance(String comments, String califications) {
        CourseCommentsFragment fragment = new CourseCommentsFragment();
        Bundle args = new Bundle();
        args.putString("comments", comments);
        args.putString("califications", califications);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comments = getArguments().getString("comments");
            califications = getArguments().getString("califications");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_comments, container, false);

        calificationTV = (TextView) rootView.findViewById(R.id.calification_number);
        opinionTV = (TextView) rootView.findViewById(R.id.opinion_text);
        calificationLayout = (LinearLayout) rootView.findViewById(R.id.calification_layout);

        chart = (HorizontalBarChart) rootView.findViewById(R.id.chart1);
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<>();

        try {
            JSONArray courseCalificationsData = new JSONArray(califications);
            if(courseCalificationsData.length() > 0) {
                averageCalification = Utilities.getAverageCalification(courseCalificationsData);
                calificationTV.setText(averageCalification.toString());
                totalCalifications = courseCalificationsData.length();
                totalOneStarCalification = 0;
                totalTwoStarCalification = 0;
                totalThreeStarCalification = 0;
                totalFourStarCalification = 0;
                totalFiveStarCalification = 0;

                for (int i = 0; i < courseCalificationsData.length(); i++) {
                    JSONObject courseCalificationArray = new JSONObject(courseCalificationsData.getString(i));
                    Integer calification = courseCalificationArray.getInt("calification");
                    switch(calification) {
                        case 1:
                            totalOneStarCalification++;
                            break;
                        case 2:
                            totalTwoStarCalification++;
                            break;
                        case 3:
                            totalThreeStarCalification++;
                            break;
                        case 4:
                            totalFourStarCalification++;
                            break;
                        case 5:
                            totalFiveStarCalification++;
                            break;
                    }
                }

                addLabelsToChart();
                addValuesToChart(totalOneStarCalification, totalTwoStarCalification, totalThreeStarCalification, totalFourStarCalification, totalFiveStarCalification);
                Bardataset = new BarDataSet(BARENTRY, averageCalification.toString() + " de 5 estrellas de " + totalCalifications + " calificaciones");
                BARDATA = new BarData(BarEntryLabels, Bardataset);
                Bardataset.setColors(ColorTemplate.createColors(new int[]{red, orange, yellow, ligthGreen, green}));
                chart.setData(BARDATA);
                chart.setDescription("");
                chart.animateY(3000);
            }

            JSONArray courseCommentsData = new JSONArray(comments);
            if(courseCommentsData.length() > 0){
                for (int i = 0; i < courseCommentsData.length() ; i++) {
                    JSONObject courseCommentsArray = new JSONObject(courseCommentsData.getString(i));
                    String message = courseCommentsArray.getString("message");
                    String time = "a las " + courseCommentsArray.getString("time");
                    String name = courseCommentsArray.getString("studentFirstName");
                    String surname = courseCommentsArray.getString("studentLastName");
                    String date = "";
                    CourseChatCardViewData obj = new CourseChatCardViewData(name, surname, time, message, false, date);
                    courseComments.add(i, obj);
                }

                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);

                adapter = new CourseChatRecyclerViewAdapter(courseComments);
                recyclerView.setAdapter(adapter);
            }

            if(courseCommentsData.length() == 0 && courseCalificationsData.length() == 0){
                TextView noOpinions = (TextView) rootView.findViewById(R.id.no_opinions);
                noOpinions.setVisibility(View.VISIBLE);
                calificationLayout.setVisibility(View.GONE);
                chart.setVisibility(View.GONE);
                opinionTV.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void addValuesToChart(Integer totalOneStarCalification, Integer totalTwoStarCalification, Integer totalThreeStarCalification, Integer totalFourStarCalification, Integer totalFiveStarCalification){
        BARENTRY.add(new BarEntry(totalOneStarCalification.floatValue(), 0));
        BARENTRY.add(new BarEntry(totalTwoStarCalification.floatValue(), 1));
        BARENTRY.add(new BarEntry(totalThreeStarCalification.floatValue(), 2));
        BARENTRY.add(new BarEntry(totalFourStarCalification.floatValue(), 3));
        BARENTRY.add(new BarEntry(totalFiveStarCalification.floatValue(), 4));
    }

    public void addLabelsToChart(){
        BarEntryLabels.add("1 *");
        BarEntryLabels.add("2 *");
        BarEntryLabels.add("3 *");
        BarEntryLabels.add("4 *");
        BarEntryLabels.add("5 *");
    }

}
