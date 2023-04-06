package ac.ke.usiu.example.midsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCourses extends AppCompatActivity {
    FirebaseFirestore courseStore;
    String storageID;
    String KeyCourseID ="Course ID";
    String KeyCourseName = "Course Name";
    String KeyLecID = "Lecturer ID";
    String KeyStudentID = "Student ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);

        //Hiding the name bar
        ActionBar appName = getSupportActionBar();
        appName.hide();

        EditText courseID = findViewById(R.id.addCourseID);
        courseID.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        EditText courseName = findViewById(R.id.addCourseName);
        EditText lecturer = findViewById(R.id.addLecID);
        EditText student = findViewById(R.id.addStudentID);
        Button addCourses = findViewById(R.id.addCourseInfo);
        courseStore = FirebaseFirestore.getInstance();

        addCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = courseID.getText().toString().trim();
                String course = courseName.getText().toString().trim();
                String lec = lecturer.getText().toString().trim();
                String studentID = student.getText().toString().trim();

                if(ID.isEmpty())
                {
                    courseID.setError("Course ID is required");
                    return;
                }
                else if(course.isEmpty())
                {
                    courseName.setError("Course Name is needed");
                    return;
                }
                else if(lec.isEmpty())
                {
                    lecturer.setError("Lecturer ID is required");
                    return;
                }
                else if(studentID.isEmpty())
                {
                    student.setError("Student ID is required");
                    return;
                }
                else if(ID.length()!= 7)
                {
                    courseID.setError("Course ID must have 7 characters only");
                    return;
                }
                else if(lec.length()!=6)
                {
                    lecturer.setError("Lecturer ID must only be 6 characters");
                    return;
                }
                else if(studentID.length()!= 6)
                {
                    student.setError("Student ID needs to be 6 characters only");
                    return;
                }
                else{
                    storageID = studentID + ID;
                    Map<String, String>courseInfo = new HashMap<>();
                    courseInfo.put(KeyCourseID,ID);
                    courseInfo.put(KeyCourseName, course);
                    courseInfo.put(KeyLecID, lec);
                    courseInfo.put(KeyStudentID,studentID);

                    courseStore.collection("CourseInformation")
                            .document(storageID)
                            .set(courseInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Course has been created",Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Course could not be created",Toast.LENGTH_LONG).show();
                                }
                            });


                }


            }
        });

    }
}