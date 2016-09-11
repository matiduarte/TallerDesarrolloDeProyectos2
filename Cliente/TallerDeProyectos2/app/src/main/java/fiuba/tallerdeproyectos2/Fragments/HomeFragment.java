package fiuba.tallerdeproyectos2.Fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fiuba.tallerdeproyectos2.Adapters.ExpandableListViewAdapter;
import fiuba.tallerdeproyectos2.Models.Courses;
import fiuba.tallerdeproyectos2.Models.CoursesResponse;
import fiuba.tallerdeproyectos2.Models.Search;
import fiuba.tallerdeproyectos2.Models.SearchResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private static final float INITIAL_ITEMS_COUNT = 1.0F;
    private LinearLayout mCarouselContainer;
    private ExpandableListView expandableListView;
    private List<String> parentHeaderInformation;
    private int lastExpandedPosition = -1;
    private static final String TAG = HomeFragment.class.getSimpleName();

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentHeaderInformation = new ArrayList<String>();
        parentHeaderInformation.add("Computacion");
        parentHeaderInformation.add("Matematica");
        parentHeaderInformation.add("Electronica");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mCarouselContainer = (LinearLayout) rootView.findViewById(R.id.carousel);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CoursesResponse> call = apiService.getCourses();
        call.enqueue(new Callback<CoursesResponse>() {
            @Override
            public void onResponse(Call<CoursesResponse>call, Response<CoursesResponse> response) {
                List<Courses> data = response.body().getData();
                String success =response.body().getSuccess();
                String message =response.body().getMessage();
                Log.d(TAG, "Courses: " + data.toString());
                Log.d(TAG, "Success: " + success);
                Log.d(TAG, "Message: " + message);

            }

            @Override
            public void onFailure(Call<CoursesResponse>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        HashMap<String, List<String>> allChildItems = returnGroupedChildItems();
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandableList);
        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(getActivity().getApplicationContext(), parentHeaderInformation, allChildItems);
        expandableListView.setAdapter(expandableListViewAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);
        final TypedArray carouselImagesArray = getResources().obtainTypedArray(R.array.carousel_demo);

        ImageView imageItem;
        TextView titleTex;
        TextView descTex;
        for (int i = 0 ; i < carouselImagesArray.length() ; ++i) {

            LinearLayout linearLayoutParent = new LinearLayout(getActivity().getApplicationContext());
            linearLayoutParent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayoutParent.setOrientation(LinearLayout.VERTICAL);
            linearLayoutParent.setBackgroundResource(R.drawable.shadow);

            LinearLayout linearLayoutTitle = new LinearLayout(getActivity().getApplicationContext());
            linearLayoutTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
            linearLayoutTitle.setOrientation(LinearLayout.VERTICAL);

            LinearLayout linearLayoutChild = new LinearLayout(getActivity().getApplicationContext());
            linearLayoutChild.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            titleTex = new TextView(getActivity().getApplicationContext());
            titleTex.setText("Algoritmos y Programacion II");
            titleTex.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            titleTex.setPadding(30, 20, 30, 20);
            titleTex.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));

            linearLayoutTitle.addView(titleTex);

            descTex = new TextView(getActivity().getApplicationContext());
            descTex.setText("La descripcion del curso va aca. ");
            descTex.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            descTex.setPadding(30, 30, 30, 30);
            descTex.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 300));

            imageItem = new ImageView(getActivity().getApplicationContext());
            imageItem.setImageResource(carouselImagesArray.getResourceId(i, -1));
            imageItem.setLayoutParams(new LinearLayout.LayoutParams(width / 2, 300));

            linearLayoutChild.addView(descTex);
            linearLayoutChild.addView(imageItem);

            linearLayoutTitle.addView(linearLayoutChild);

            linearLayoutParent.addView(linearLayoutTitle);

            mCarouselContainer.addView(linearLayoutParent);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private HashMap<String, List<String>> returnGroupedChildItems(){

        HashMap<String, List<String>> childContent = new HashMap<String, List<String>>();

        List<String> computacion = new ArrayList<String>();
        computacion.add("Taller de Programacion II");
        computacion.add("Tecnicas de diseno");
        computacion.add("Algoritmos y Programacion I");
        computacion.add("Adminiistracion de Proyectos I");

        List<String> matematica = new ArrayList<String>();
        matematica.add("Analisis Matematico II");
        matematica.add("Algebra II");
        matematica.add("Probabilidad y Estadistica");

        List<String> electronica = new ArrayList<String>();
        electronica.add("Organizacion de Computadoras");
        electronica.add("Estructura del Computador");
        electronica.add("Analisis de Circuitos");

        childContent.put(parentHeaderInformation.get(0), computacion);
        childContent.put(parentHeaderInformation.get(1), matematica);
        childContent.put(parentHeaderInformation.get(2), electronica);

        return childContent;
    }

}
