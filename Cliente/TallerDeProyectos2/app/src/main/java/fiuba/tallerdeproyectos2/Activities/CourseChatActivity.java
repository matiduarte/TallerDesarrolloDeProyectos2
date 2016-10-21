package fiuba.tallerdeproyectos2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import fiuba.tallerdeproyectos2.Adapters.CourseChatRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Adapters.UnitRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.CourseChatCardViewData;
import fiuba.tallerdeproyectos2.Models.UnitsCardViewData;
import fiuba.tallerdeproyectos2.R;

public class CourseChatActivity extends AppCompatActivity {

    private static final String TAG = CourseChatActivity.class.getSimpleName();
    private RecyclerView.Adapter adapter;
    ArrayList chats = new ArrayList<>();
    Integer courseId;
    String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", 0);
        courseName = intent.getStringExtra("courseName");

        setTitle("Foro de" + courseName);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        for (int i=0; i < 10; i++) {
            int hour = Calendar.getInstance().get(Calendar.HOUR);
            int minutes = Calendar.getInstance().get(Calendar.MINUTE);

            CourseChatCardViewData obj = new CourseChatCardViewData("Matias", "Carreras", hour + ":" + minutes, "Hola a todos! Alguien quiere juntarse a practicar para el examen final?");
            chats.add(i, obj);
        }

        adapter = new CourseChatRecyclerViewAdapter(chats);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }
}
