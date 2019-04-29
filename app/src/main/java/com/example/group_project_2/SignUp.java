package com.example.group_project_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.group_project_2.User;

public class SignUp extends AppCompatActivity {

    private EditText username, password, password2;
    private Button signup;


    DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbr = FirebaseDatabase.getInstance().getReference("users");

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);

        signup = (Button) findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup() {

        String newusername = username.getText().toString();
        String newpassword = password.getText().toString();
        String newpassword2 = password2.getText().toString();

        if (newusername.equals("") || newpassword.equals("") || newpassword2.equals("")) {
            Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        if (!newpassword.equals(newpassword2) ) {
            Toast.makeText(SignUp.this, "Please make sure that your passwords are equal", Toast.LENGTH_LONG).show();
            return;
        }

        String id = dbr.push().getKey();
        User x = new User(newusername, newpassword, "user");
        dbr.child(id).setValue(x);

        Toast.makeText(SignUp.this, "Sign-Up Successful", Toast.LENGTH_LONG).show();

        username.setText("");
        password.setText("");
        password2.setText("");
    }
}
