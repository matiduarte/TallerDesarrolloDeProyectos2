package fiuba.tallerdeproyectos2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;

import fiuba.tallerdeproyectos2.Activities.CourseChatActivity;
import fiuba.tallerdeproyectos2.Adapters.CourseChatRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.CourseChatCardViewData;
import fiuba.tallerdeproyectos2.R;

public class CourseCommentsFragment extends Fragment {

    private String mParam1, mParam2;
    private static final String TAG = CourseCommentsFragment.class.getSimpleName();
    private RecyclerView.Adapter adapter;
    ArrayList comments = new ArrayList<>();

    public CourseCommentsFragment() {}

    public static CourseCommentsFragment newInstance(String param1, String param2) {
        CourseCommentsFragment fragment = new CourseCommentsFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("param1");
            mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_comments, container, false);

        for (int i = 0; i < 10; i++) {
            String message = "El curso me parecio muy interesante. El contenido excelente. Lo recomiendo!";
            String time = "15:20";
            String name = "Matias";
            String surname = "Carreras";
            String date = "15/10/16";
            CourseChatCardViewData obj = new CourseChatCardViewData(name, surname, time, message, date);
            comments.add(i, obj);
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CourseChatRecyclerViewAdapter(comments);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
