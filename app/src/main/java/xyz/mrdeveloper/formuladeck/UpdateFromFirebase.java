package xyz.mrdeveloper.formuladeck;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
 * Created by Vaibhav on 19-01-2017.
 */

public class UpdateFromFirebase extends android.app.Application {

    public static ArrayList<FormulaDetails> allFormulaList;
    public Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public UpdateFromFirebase() {
    }

    public UpdateFromFirebase(Context context) {
        mContext = context;
    }

    public void Update() {
        Log.d("Check", "Here I am, this is me 1");
        UpdateAllFormulas("allformuladetails");
    }

    public void UpdateAllFormulas(final String arrayName) {
        final DatabaseReference mFirebaseDatabase;
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(arrayName);

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Check", "Value: In the last!!!!!");
                AddAllFormulas(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Check", "Failed to read app title value.", error.toException());
            }
        });
    }

    public void AddAllFormulas(DataSnapshot dataSnapshot) {
        // Get formula categories names and descriptions.
        allFormulaList = new ArrayList<>();
        Log.d("Check", "fatal Here I am, this is me 4");

        for (DataSnapshot alert : dataSnapshot.getChildren()) {
            FormulaDetails FormulaDetails = new FormulaDetails();

            if (alert != null) {
                FormulaDetails.category = alert.child("category").getValue(String.class);
                FormulaDetails.formula = alert.child("formula").getValue(String.class);
                FormulaDetails.name = alert.child("name").getValue(String.class);

                allFormulaList.add(FormulaDetails);
            }
        }
    }
}