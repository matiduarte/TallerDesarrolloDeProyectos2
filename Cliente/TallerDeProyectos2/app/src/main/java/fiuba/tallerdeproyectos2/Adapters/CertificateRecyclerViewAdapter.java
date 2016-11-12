package fiuba.tallerdeproyectos2.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fiuba.tallerdeproyectos2.Models.CertificateCardViewData;
import fiuba.tallerdeproyectos2.R;

public class CertificateRecyclerViewAdapter extends RecyclerView.Adapter<CertificateRecyclerViewAdapter.CertificateCardViewDataHolder> {
    private ArrayList<CertificateCardViewData> dataset;
    private static CertificateRecyclerViewAdapter.MyClickListener clickListener;

    static class CertificateCardViewDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fileName;

        CertificateCardViewDataHolder(View itemView) {
            super(itemView);
            fileName = (TextView) itemView.findViewById(R.id.filename);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(CertificateRecyclerViewAdapter.MyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CertificateRecyclerViewAdapter(ArrayList<CertificateCardViewData> myDataset) {
        dataset = myDataset;
    }

    @Override
    public CertificateRecyclerViewAdapter.CertificateCardViewDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.certificate_card_view, parent, false);
        return new CertificateRecyclerViewAdapter.CertificateCardViewDataHolder(view);
    }

    @Override
    public void onBindViewHolder(CertificateRecyclerViewAdapter.CertificateCardViewDataHolder holder, int position) {
        holder.fileName.setText(dataset.get(position).getFileName());
    }

    public void addItem(CertificateCardViewData certificateCardViewData, int index) {
        dataset.add(index, certificateCardViewData);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        dataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}
