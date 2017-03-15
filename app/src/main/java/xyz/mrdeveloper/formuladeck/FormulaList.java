package xyz.mrdeveloper.formuladeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import static xyz.mrdeveloper.formuladeck.UpdateFromFirebase.allFormulaList;

/**
 * Created by Vaibhav on 22-02-2017.
 */

public class FormulaList extends Fragment {

    View view;
    ListView flyingSpaghettiMonster;
    ArrayList<Formula> formulaDataList;

    public static FormulaList newInstance(ArrayList<Formula> formulas) {
        FormulaList formulaList = new FormulaList();
        formulaList.formulaDataList = formulas;
        return formulaList;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.list_formula, container, false);

        flyingSpaghettiMonster = (ListView) view.findViewById(R.id.list_formula);
        Log.d("Check", "Here I am, this is me!");

        if (allFormulaList == null) {
            Log.d("Check", "NULL NULL NULL!");
        } else {

            final FormulaAdapter adapter = new FormulaAdapter(getContext(), allFormulaList);

            flyingSpaghettiMonster.setAdapter(new SlideExpandableListAdapter(
                    adapter,
                    R.id.button_expand_toggle,
                    R.id.layout_expand
            ));
        }

        return view;
    }

//    public void setFormulaList(String decidingFactor, int decidingValue) {
    //decidingValue 1 for Day, 2 for Category.
//        mDecidingFactor = decidingFactor;
//        mDecidingValue = decidingValue;
//    }
}