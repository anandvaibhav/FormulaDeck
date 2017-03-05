package xyz.mrdeveloper.formuladeck;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vaibhav on 02-03-2017.
 */

public class FormulaAdapter extends BaseAdapter {

    private ArrayList<FormulaData> formulaDataList;
    private ViewHolderForFormula holder;
    private String question = "(2 * log10[x]) / ( cos[y])";
    private StringBuilder questionForParser;
    private Integer totalEditTexts = 0;
    private Map<String, Double> variablesValue;
    private List<String> variables;
    private List<String> mathematicalEntities;
    private List<EditText> editTextVariableValues;
    private Context mContext;

    public FormulaAdapter(Context context, ArrayList<FormulaData> formulas) {
        mContext = context;
        formulaDataList = formulas;
    }

    @Override
    public int getCount() {
        return formulaDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return formulaDataList.get(position);
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
            convertView = inflater.inflate(R.layout.formula_list_item, parent, false);

            // create a new "Holder" with subviews
            holder = new ViewHolderForFormula();
            holder.textFormulaName = (TextView) convertView.findViewById(R.id.text_formula_name);
            holder.textFormulaAnswer = (TextView) convertView.findViewById(R.id.text_formula_answer);
            holder.layoutFormulaEquation = (LinearLayout) convertView.findViewById(R.id.layout_formula_equation);

            FormulaData formulaData = (FormulaData) getItem(position);

            question = formulaData.FormulaEquation;

            // Update row view's textviews to display recipe information
            holder.textFormulaName.setText(formulaData.FormulaName);

            totalEditTexts = 0;
            variables = new ArrayList<>();
            mathematicalEntities = new ArrayList<>();
            editTextVariableValues = new ArrayList<>();
            variablesValue = new HashMap<>();

            questionForParser = new StringBuilder(question);
            SplitAndShowFormula();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

//            holder.textFormulaAnswer = new TextView(mContext);
//            holder.textFormulaAnswer.setTextColor(Color.BLACK);
//            holder.textFormulaAnswer.setTextSize(20);
//            holder.textFormulaAnswer.setText(R.string.no_value_given);
//            layoutParams.setMargins(10, 200, 0, 0);
//
//            holder.textFormulaAnswer.setLayoutParams(layoutParams);
//            holder.layoutFormulaEquation.addView(holder.textFormulaAnswer);


            //SetAnswerTextView("Fill values you Dumb Ass!");
            //CalculateAnswer();

            // hang onto this eventDetailsHolder for future recycle
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
        LinearLayout layoutFormulaEquation;
    }

    private void SplitAndShowFormula() {
        int beginIndex = 0, endIndex = -1, i;

        for (i = 0; i < question.length(); ++i) {
            if (question.charAt(i) == '[') {
                beginIndex = i;
                questionForParser.setCharAt(i, '(');

                String text = question.substring(endIndex + 1, i);
                if (i > 0) {
                    SetQuestionTextView(text);
                }
            } else if (question.charAt(i) == ']') {
                endIndex = i;
                questionForParser.setCharAt(i, ')');
                String variable = question.substring(beginIndex + 1, endIndex);

                variables.add(variable);

                SetEditText(variable);
                totalEditTexts++;
            }
        }
        SetQuestionTextView(question.substring(endIndex + 1, i)); //It will show question.
    }

    private void SetQuestionTextView(String toSet) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView mathematicalEntity = new TextView(mContext);
        mathematicalEntity.setTextColor(Color.BLACK);
        mathematicalEntity.setTextSize(18);
        mathematicalEntity.setText(toSet);

        layoutParams.setMargins(0, 0, 0, 0);
        mathematicalEntity.setLayoutParams(layoutParams);

        holder.layoutFormulaEquation.addView(mathematicalEntity);
    }

    private void SetEditText(String hint) {
        EditText variable = new EditText(mContext);

        editTextVariableValues.add(variable);
        editTextVariableValues.get(totalEditTexts).setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        editTextVariableValues.get(totalEditTexts).setHint(hint);
        editTextVariableValues.get(totalEditTexts).setHintTextColor(Color.GRAY);
        editTextVariableValues.get(totalEditTexts).setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        holder.layoutFormulaEquation.addView(variable);

        editTextVariableValues.get(totalEditTexts).addTextChangedListener(new CustomTextWatcher(editTextVariableValues.get(totalEditTexts)));
    }

    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;

        CustomTextWatcher(EditText e) {
            mEditText = e;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (!(s.toString().isEmpty() || s.length() == 0 || s.toString().equals(""))) {

                variablesValue.put(mEditText.getHint().toString(), Double.parseDouble(s.toString()));

                int i;
                for (i = 0; i < editTextVariableValues.size(); ++i) {
                    String editText = editTextVariableValues.get(i).getText().toString().trim();
                    if (editText.isEmpty() || editText.length() == 0 || editText.equals("")) {
                        //EditText is empty
                        SetAnswerTextView("Fill all the values you Dumb Ass Mother Fucker!");
                        break;
                    } else {
                        Log.d("Check", "editText: " + editText);
                    }
                }

                Log.d("Check", "i: " + i + "  " + editTextVariableValues.size());
                Log.d("Check", "editText: " + variablesValue);

                if (i >= editTextVariableValues.size()) {
                    SetAnswerTextView(CalculateAnswer());
                }
            } else {
                SetAnswerTextView("Fill all the values you Dumb Ass Mother Fucker!");
            }
        }
    }

    private String CalculateAnswer() {
        Expression e = new ExpressionBuilder(questionForParser.toString())
                .variables(variables.toArray(new String[0]))
                .build()
                .setVariables(variablesValue);

        double result = e.evaluate();
        Double truncatedResult = BigDecimal.valueOf(result)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        Log.d("Check", "Answer: " + result);
        return Double.toString(truncatedResult);
    }

    private void SetAnswerTextView(String answer) {
        Log.d("Check", "Here I am, this is me!");
        holder.textFormulaAnswer.setText(answer);

    }
}
