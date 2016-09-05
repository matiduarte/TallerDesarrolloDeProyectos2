package fiuba.tallerdeproyectos2;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final float INITIAL_ITEMS_COUNT = 1.0F;
    private LinearLayout mCarouselContainer;
    private ExpandableListView expandableListView;
    private List<String> parentHeaderInformation;

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
        final int height = (int) (displayMetrics.heightPixels / 3);
        final TypedArray carouselImagesArray = getResources().obtainTypedArray(R.array.carousel_demo);

        ImageView imageItem;
        TextView titleTex;
        TextView descTex;
        for (int i = 0 ; i < carouselImagesArray.length() ; ++i) {

            LinearLayout linearLayoutParent = new LinearLayout(getActivity().getApplicationContext());
            linearLayoutParent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayoutParent.setOrientation(LinearLayout.VERTICAL);

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
            descTex.setText("adsdsa adsdds  asdas d asdsa das ");
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
