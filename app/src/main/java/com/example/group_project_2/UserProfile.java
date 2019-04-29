package com.example.group_project_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    TextView profilename, balance;

    DatabaseReference dbr;

    private Spinner mySpinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        loadActivity();


    }

    public void loadActivity() {
        profilename = (TextView) findViewById(R.id.profilename);
        balance = (TextView) findViewById(R.id.balance);
        profilename.setText(getIntent().getStringExtra("username"));

        mySpinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UserProfile.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(myAdapter);

        dbr = FirebaseDatabase.getInstance().getReference("users");

        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if(user.getUsername().equals(profilename.getText().toString())) {
                                balance.setText(String.valueOf(snapshot.getValue(User.class).getBalance()));

                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    public void scanNow(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setResultDisplayDuration(0);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            dbr = FirebaseDatabase.getInstance().getReference("items");

            // display it on screen
            String id = dbr.push().getKey();
            dbr.child(id).setValue(scanContent);
            Toast.makeText(UserProfile.this, "Scan Successful", Toast.LENGTH_LONG).show();

            dbr = FirebaseDatabase.getInstance().getReference("users");

            FirebaseDatabase.getInstance().getReference().child("users")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                if(user.getUsername().equals(profilename.getText().toString())) {
                                    dbr = FirebaseDatabase.getInstance().getReference("users").child(snapshot.getKey());
                                    Map<String, Object> hopperUpdates = new HashMap<>();
                                    hopperUpdates.put("balance", snapshot.getValue(User.class).getBalance() + 0.20);
                                    dbr.updateChildren(hopperUpdates);


                                }
                            }
                            loadActivity();

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });


        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
