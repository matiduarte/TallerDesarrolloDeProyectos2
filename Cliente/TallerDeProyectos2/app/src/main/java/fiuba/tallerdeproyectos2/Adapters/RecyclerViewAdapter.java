package fiuba.tallerdeproyectos2.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import fiuba.tallerdeproyectos2.Models.CoursesCardViewData;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CoursesCardViewDataHolder> {
    private ArrayList<CoursesCardViewData> dataset;
    private static MyClickListener clickListener;

    static class CoursesCardViewDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView courseTitle;
        ImageView courseImage;
        TextView courseId;

        CoursesCardViewDataHolder(View itemView) {
            super(itemView);
            courseTitle = (TextView) itemView.findViewById(R.id.course_title);
            courseImage = (ImageView) itemView.findViewById(R.id.course_image);
            courseId = (TextView) itemView.findViewById(R.id.course_id);
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

    public RecyclerViewAdapter(ArrayList<CoursesCardViewData> myDataset) {
        dataset = myDataset;
    }

    @Override
    public CoursesCardViewDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_card_view, parent, false);
        return new CoursesCardViewDataHolder(view);
    }

    @Override
    public void onBindViewHolder(CoursesCardViewDataHolder holder, int position) {
        holder.courseTitle.setText(dataset.get(position).getCourseTitle());
        if(dataset.get(position).getCourseImageUrl() != ""){
            new DownloadImageTask(holder.courseImage).execute(ApiClient.BASE_URL + dataset.get(position).getCourseImageUrl());
        } else {
            holder.courseImage.setImageResource(R.drawable.default_image);
        }
        holder.courseId.setText(dataset.get(position).getCourseId());
    }

    public void addItem(CoursesCardViewData coursesCardViewData, int index) {
        dataset.add(index, coursesCardViewData);
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
