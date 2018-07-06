package com.codeali.firebaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseUser user;

    private String TAG = " DATABASE";

    private Button saveB, loadB;
    private EditText first_name, last_name, fav_ic;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        saveB = (Button) findViewById(R.id.button4);
        loadB = (Button) findViewById(R.id.button5);
        first_name = (EditText) findViewById(R.id.editText);
        last_name = (EditText) findViewById(R.id.editText2);
        fav_ic = (EditText) findViewById(R.id.editText5);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance().getReference();


        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null){

                    FirebaseData data = new FirebaseData(first_name.getText().toString(), last_name.getText().toString(), fav_ic.getText().toString());

                    mDatabase.child("data").child(user.getUid()).setValue(data, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(DatabaseActivity.this, "Data could not be saved " + databaseError.getMessage(),
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(DatabaseActivity.this, "Data saved successfully.",
                                        Toast.LENGTH_SHORT).show();


                            }
                        }
                    });

                }
            }
        });

        loadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null){

                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get Post object and use the values to update the UI
                            //Log.e("SNAPSHOT: ", String.valueOf(dataSnapshot.getChildren()));

                            FirebaseData data = dataSnapshot.getValue(FirebaseData.class);

                            if (data != null){
                                first_name.setText(data.getFirst_name());
                                last_name.setText(data.getLast_name());
                                fav_ic.setText(data.getFavorite_ice_cream());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                            // [START_EXCLUDE]

                            // [END_EXCLUDE]
                        }
                    };
                    mDatabase.child("data").child(user.getUid().toString()).addListenerForSingleValueEvent (postListener);
                }
            }
        });
    }
}
