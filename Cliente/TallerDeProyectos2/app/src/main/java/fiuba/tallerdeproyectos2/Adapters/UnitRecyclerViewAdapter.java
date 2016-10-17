package fiuba.tallerdeproyectos2.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fiuba.tallerdeproyectos2.Models.UnitsCardViewData;
import fiuba.tallerdeproyectos2.R;

public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.UnitsCardViewDataHolder> {
    private ArrayList<UnitsCardViewData> dataset;
    private static MyClickListener clickListener;

    static class UnitsCardViewDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView unitTitle;
        TextView unitDescription;
        TextView unitId;
        TextView unitReference;

        UnitsCardViewDataHolder(View itemView) {
            super(itemView);
            unitTitle = (TextView) itemView.findViewById(R.id.unit_title);
            unitDescription = (TextView) itemView.findViewById(R.id.unit_description);
            unitId = (TextView) itemView.findViewById(R.id.unit_id);
            unitReference = (TextView) itemView.findViewById(R.id.unit_reference);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public UnitRecyclerViewAdapter(ArrayList<UnitsCardViewData> myDataset) {
        dataset = myDataset;
    }

    @Override
    public UnitsCardViewDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.units_card_view, parent, false);
        return new UnitsCardViewDataHolder(view);
    }

    @Override
    public void onBindViewHolder(UnitsCardViewDataHolder holder, int position) {
        holder.unitTitle.setText(dataset.get(position).getUnitTitle());
        holder.unitDescription.setText(dataset.get(position).getUnitDescription());
        holder.unitId.setText(dataset.get(position).getUnitId());
        holder.unitReference.setText(String.valueOf(position+1) + ".");
    }

    public void addItem(UnitsCardViewData unitsCardViewData, int index) {
        dataset.add(index, unitsCardViewData);
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
