package fiuba.tallerdeproyectos2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fiuba.tallerdeproyectos2.Adapters.CertificateRecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Adapters.RecyclerViewAdapter;
import fiuba.tallerdeproyectos2.Models.CertificateCardViewData;
import fiuba.tallerdeproyectos2.R;

public class CertificatesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = CoursesFragment.class.getSimpleName();
    ArrayList certificates = new ArrayList<>();
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView noCertificates;
    private RecyclerView.Adapter adapter;

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

        for (int i=0; i< 10; i++) {
            CertificateCardViewData obj = new CertificateCardViewData("certificado_Taller2_" + i + ".pdf");
            certificates.add(i, obj);
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
                Toast.makeText(getContext(), "Descargar archivo", Toast.LENGTH_LONG).show();

            }
        });

        return rootView;
    }

    @Override
    public void onRefresh() {
        certificates.clear();
        for (int i=0; i< 10; i++) {
            CertificateCardViewData obj = new CertificateCardViewData("certificado_Taller2.pdf");
            certificates.add(i, obj);
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
                Toast.makeText(getContext(), "Descargar archivo", Toast.LENGTH_LONG).show();

            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }
}
