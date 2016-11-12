package fiuba.tallerdeproyectos2.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import fiuba.tallerdeproyectos2.R;

public class CourseOpinionActivity extends AppCompatActivity {

    public RatingBar ratingBar;
    EditText opinionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_opinion);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        opinionET = (EditText) findViewById(R.id.opinion);
        setTitle("Opinion");
    }

    public void sendRatingAndOpinion(View view){
        String opinion = opinionET.getText().toString();
        Toast.makeText(getApplicationContext(), "Rating: " +
                String.valueOf(ratingBar.getRating()) + "Opinion: " + opinion, Toast.LENGTH_LONG).show();
    }
}
