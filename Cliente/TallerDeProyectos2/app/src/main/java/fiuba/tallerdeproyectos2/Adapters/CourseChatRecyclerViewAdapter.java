package fiuba.tallerdeproyectos2.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fiuba.tallerdeproyectos2.Models.CourseChatCardViewData;
import fiuba.tallerdeproyectos2.Models.UnitsCardViewData;
import fiuba.tallerdeproyectos2.R;

public class CourseChatRecyclerViewAdapter extends RecyclerView.Adapter<CourseChatRecyclerViewAdapter.CourseChatCardViewDataHolder> {
    private ArrayList<CourseChatCardViewData> dataset;
    private static CourseChatRecyclerViewAdapter.MyClickListener clickListener;

    static class CourseChatCardViewDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView surname;
        TextView message;
        TextView time;

        CourseChatCardViewDataHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            surname = (TextView) itemView.findViewById(R.id.surname);
            message = (TextView) itemView.findViewById(R.id.message);
            time = (TextView) itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(CourseChatRecyclerViewAdapter.MyClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CourseChatRecyclerViewAdapter(ArrayList<CourseChatCardViewData> myDataset) {
        dataset = myDataset;
    }

    @Override
    public CourseChatRecyclerViewAdapter.CourseChatCardViewDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_chat_card_view, parent, false);
        return new CourseChatRecyclerViewAdapter.CourseChatCardViewDataHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseChatRecyclerViewAdapter.CourseChatCardViewDataHolder holder, int position) {
        holder.name.setText(dataset.get(position).getName());
        holder.surname.setText(dataset.get(position).getSurname());
        holder.message.setText(dataset.get(position).getMessage());
        holder.time.setText(dataset.get(position).getTime());
    }

    public void addItem(CourseChatCardViewData courseChatCardViewData, int index) {
        dataset.add(index, courseChatCardViewData);
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