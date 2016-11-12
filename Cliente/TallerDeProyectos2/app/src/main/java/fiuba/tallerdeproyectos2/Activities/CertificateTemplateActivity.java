package fiuba.tallerdeproyectos2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fiuba.tallerdeproyectos2.R;

public class CertificateTemplateActivity extends AppCompatActivity {

    String studentName, teacherName, courseName, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_template);

        Intent intent = getIntent();
        studentName = intent.getStringExtra("studentName");
        teacherName = intent.getStringExtra("teacherName");
        result = intent.getStringExtra("result");
        courseName = intent.getStringExtra("courseName");

        setTitle("Certificado");

        TextView studentNameTV = (TextView) findViewById(R.id.studentName);
        TextView teacherNameTV = (TextView) findViewById(R.id.teacherName);
        TextView resultTV = (TextView) findViewById(R.id.result);
        TextView courseNameTV = (TextView) findViewById(R.id.courseName);

        studentNameTV.setText(studentName);
        teacherNameTV.setText(teacherName);
        resultTV.setText(result);
        courseNameTV.setText(courseName);
    }

    private void navigateToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("showCertificateFragment", true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        navigateToMainActivity();
    }
}


