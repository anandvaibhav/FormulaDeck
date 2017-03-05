package xyz.mrdeveloper.formuladeck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vaibhav on 02-03-2017.
 */

public class FormulaAdapter extends BaseAdapter {

    ArrayList<Formula> formulaList;

    int totalFormulas = 0;

    ViewHolderForFormula holder;

    Context mContext;

    public FormulaAdapter(Context context, ArrayList<Formula> formulas) {
        mContext = context;
        if (formulas != null)
            formulaList = formulas;
        else
            formulaList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return formulaList.size();
    }

    @Override
    public Object getItem(int position) {
        return formulaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // check if the view already exists if so, no need to inflate and findViewById again!
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Inflate the custom row layout from your XML.
            convertView = inflater.inflate(R.layout.list_item_formula, parent, false);

            // create a new "Holder" with subviews
            holder = new ViewHolderForFormula();
            holder.textFormulaName = (TextView) convertView.findViewById(R.id.text_formula_name);
            holder.textFormulaAnswer = (TextView) convertView.findViewById(R.id.text_formula_answer);
            holder.layoutFormulaEquation = (ViewGroup) convertView.findViewById(R.id.layout_formula_equation);

            Formula formulaData = (Formula) getItem(position);

            holder.textFormulaName.setText(formulaData.formulaName);

            formulaList.get(position)
                    .SplitAndShowFormula(mContext, holder.layoutFormulaEquation, holder.textFormulaAnswer);

            convertView.setTag(holder);
        } else {
            // skip all the expensive inflation/findViewById and just get the eventDetailsHolder you already made
            holder = (ViewHolderForFormula) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolderForFormula {
        TextView textFormulaName;
        TextView textFormulaAnswer;
        ViewGroup layoutFormulaEquation;
    }
}
