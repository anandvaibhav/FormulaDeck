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

    public static ArrayList<Formula> allFormulaList;
    public Context mContext;
    public MainActivity mainActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public UpdateFromFirebase() {
    }

    public UpdateFromFirebase(MainActivity mainActivity, Context context) {
        mContext = context;
        this.mainActivity = mainActivity;
        UpdateAllFormulas("allformuladetails");
    }

    public void UpdateAllFormulas(final String arrayName) {
        final DatabaseReference mFirebaseDatabase;
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        Log.d("Check", "Formula");

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(arrayName);

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Check", "Check 1");
                AddAllFormulas(dataSnapshot);
                Log.d("Check", "Check 2");
                //mainActivity.SetFragment();
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
            Formula Formula = new Formula();

            if (alert != null) {
                //Formula.category = alert.child("category").getValue(String.class);
                Formula.formulaEquation = alert.child("formula").getValue(String.class);
                Formula.formulaName = alert.child("name").getValue(String.class);
                Log.d("Check", "Formula Name: " + Formula.formulaName);

                allFormulaList.add(Formula);
                Log.d("Check", "FormulaList Size: " + allFormulaList.size());
            }
        }
        Log.d("Check", "Check 3");
    }
}