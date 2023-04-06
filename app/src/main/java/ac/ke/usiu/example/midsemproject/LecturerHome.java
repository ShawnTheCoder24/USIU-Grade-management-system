package ac.ke.usiu.example.midsemproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LecturerHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_home);
        //Hiding the name bar
        ActionBar appName = getSupportActionBar();
        appName.hide();

        //Go to the record creation page
        Button createRecord = findViewById(R.id.recordGrade);
        Button removeRecord = findViewById(R.id.deleteGrades);
        Button update = findViewById(R.id.updateRecords);
        Button viewGrades = findViewById(R.id.viewGrades);

        createRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextPage = new Intent(LecturerHome.this,setAllGrades.class);
                startActivity(nextPage);
            }
        });
        removeRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deletePage = new Intent(LecturerHome.this, DeleteRecord.class);
                startActivity(deletePage);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updatePage = new Intent(LecturerHome.this, updateHomePage.class);
                startActivity(updatePage);
            }
        });

        viewGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewScores = new Intent(LecturerHome.this, ViewStudentScores.class);
                startActivity(viewScores);
            }
        });

    }
}