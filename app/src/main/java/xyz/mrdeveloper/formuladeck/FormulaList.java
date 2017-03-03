package xyz.mrdeveloper.formuladeck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Vaibhav on 22-02-2017.
 */

public class FormulaList extends Fragment {

    View view;
    ListView flyingSpaghettiMonster;
    ArrayList<FormulaData> formulaDataList;

    public static FormulaList newInstance(ArrayList<FormulaData> formulas) {
        FormulaList formulaList = new FormulaList();
        formulaList.formulaDataList = formulas;
        return formulaList;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.formula_list, container, false);

        flyingSpaghettiMonster = (ListView) view.findViewById(R.id.list_formula);

        final FormulaAdapter adapter = new FormulaAdapter(getContext(), formulaDataList);

        flyingSpaghettiMonster.setAdapter(new SlideExpandableListAdapter(
                adapter,
                R.id.button_expand_toggle,
                R.id.layout_expand
        ));

        return view;
    }

//    public void setFormulaList(String decidingFactor, int decidingValue) {
    //decidingValue 1 for Day, 2 for Category.
//        mDecidingFactor = decidingFactor;
//        mDecidingValue = decidingValue;
//    }
}