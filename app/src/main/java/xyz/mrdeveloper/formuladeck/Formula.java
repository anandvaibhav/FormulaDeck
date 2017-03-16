package xyz.mrdeveloper.formuladeck;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

public class Formula {

    String formulaName;
    String formulaEquation;
//    ArrayList<String> formulaUnits;

    //    String question = "[x]+[y]";
    StringBuilder formulaEquationForParser;
    Integer totalEditTexts = 0;
    Map<String, Double> variablesValue;
    List<String> variables;
    List<String> mathematicalEntities;
    List<EditText> editTextVariableValues;

    Formula(String name, String equation) {
        formulaName = name;
        formulaEquation = equation;

        variables = new ArrayList<>();
        mathematicalEntities = new ArrayList<>();
        editTextVariableValues = new ArrayList<>();
        variablesValue = new HashMap<>();

        formulaEquationForParser = new StringBuilder(formulaEquation);

//        SplitAndShowFormula();
//
//        CalculateAnswer();
    }

    public Formula() {
        variables = new ArrayList<>();
        mathematicalEntities = new ArrayList<>();
        editTextVariableValues = new ArrayList<>();
        variablesValue = new HashMap<>();
    }

    void SplitAndShowFormula(Context context, ViewGroup viewGroup, TextView textAnswer) {
        int beginIndex = 0, endIndex = -1, i;

        Log.d("Check", "formulaEquation: " + formulaEquation);
        formulaEquationForParser = new StringBuilder(formulaEquation);
        Log.d("Check", "formulaEquationForParser: " + formulaEquationForParser);

        for (i = 0; i < formulaEquation.length(); ++i) {
            Log.d("Check", "i: " + i + " Value of i: " + formulaEquation.charAt(i));
            if (formulaEquation.charAt(i) == '[') {
                beginIndex = i;
                formulaEquationForParser.setCharAt(i, '(');

                String text = formulaEquation.substring(endIndex + 1, i);
                if (i > 0) {
                    SetFormulaEquationTextView(viewGroup, context, text, 15);
                }

            } else if (formulaEquation.charAt(i) == ']') {
                endIndex = i;
                formulaEquationForParser.setCharAt(i, ')');
                String variable = formulaEquation.substring(beginIndex + 1, endIndex);

                Log.d("Check", "variable: " + variable);

                variables.add(variable);

                SetEditText(viewGroup, textAnswer, context, variable);
                totalEditTexts++;

            } else if (formulaEquation.charAt(i) == '{') {
                beginIndex = i;
                for (int j = i; j < formulaEquation.length(); ++j) {
                    if (formulaEquation.charAt(j) == '}') {
                        endIndex = j;
                        i = j;
                        //formulaEquationForParser.replace(beginIndex, endIndex, "");
                        String unit = formulaEquation.substring(beginIndex + 1, endIndex);
                        SetFormulaEquationTextView(viewGroup, context, unit, 7);
                        break;
                    }
                }
            } /*else if (formulaEquation.charAt(i) == '%') {
                beginIndex = i;
                for (int j = i; j < formulaEquation.length(); ++j) {
                    if (formulaEquation.charAt(j) == '%') {
                        endIndex = j;
                        i = j;
                        formulaEquationForParser.replace(beginIndex, endIndex, "");
                        String answerUnit = formulaEquation.substring(beginIndex + 1, endIndex);
                        SetFormulaEquationTextView(viewGroup, context, answerUnit, 7);
                    }
                }
            }*/
        }
        SetFormulaEquationTextView(viewGroup, context, formulaEquation.substring(endIndex + 1, i), 15);   //It will show formulaEquation

        Log.d("Check", "Length: " + formulaEquation.length());

        for (i = 0; i < formulaEquationForParser.length(); ++i) {
            if (formulaEquationForParser.charAt(i) == '{') {
                beginIndex = i;
                for (int j = i; j < formulaEquationForParser.length(); ++j) {
                    if (formulaEquationForParser.charAt(j) == '}') {
                        endIndex = j;
                        i = j;
                        Log.d("Check", "BeginIndex: " + beginIndex + " EndIndex: " + endIndex);
                        formulaEquationForParser.replace(beginIndex, endIndex, "");
                        break;
                    }
                }
            }
        }
    }

    public void SetFormulaEquationTextView(ViewGroup viewGroup, Context context, String toSet, int fontSize) {
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

//        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(
//                FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView mathematicalEntity = new TextView(context);
        mathematicalEntity.setTextColor(Color.BLACK);
        mathematicalEntity.setTextSize(fontSize);
        mathematicalEntity.setText(toSet);

//        layoutParams.setMargins(0, 0, 0, 0);
//        mathematicalEntity.setLayoutParams(layoutParams);

        viewGroup.addView(mathematicalEntity);
    }

    public void SetEditText(ViewGroup viewGroup, TextView textAnswer, Context context, String hint) {
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

//        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
//                FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);

//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        EditText variableEditText = new EditText(context);
//        layoutParams.setMargins(20, 0, 0, 0);
//        variable.setLayoutParams(layoutParams);

        variableEditText.setFocusableInTouchMode(true);
        variableEditText.setFocusable(true);

        variableEditText.setTextSize(15);
        variableEditText.setHint(hint);
        variableEditText.setHintTextColor(Color.GRAY);
        variableEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        variableEditText.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        variableEditText.addTextChangedListener(new CustomTextWatcher(variableEditText, textAnswer));

        viewGroup.addView(variableEditText);
        editTextVariableValues.add(variableEditText);
    }

    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;
        private TextView textAnswer;

        CustomTextWatcher(EditText e, TextView textView) {
            mEditText = e;
            textAnswer = textView;
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
                        SetAnswerTextView(textAnswer, "Fill all the values you Dumb Ass Mother Fucker!");
                        break;
                    } else {
                        Log.d("Check", "editText: " + editText);
                    }
                }

                Log.d("Check", "i: " + i + "  " + editTextVariableValues.size());
                Log.d("Check", "editText: " + variablesValue);

                if (i >= editTextVariableValues.size()) {
                    SetAnswerTextView(textAnswer, CalculateAnswer());
                }
            } else {
                SetAnswerTextView(textAnswer, "Fill all the values you Dumb Ass Mother Fucker!");
            }
        }
    }

    //Here I will add Firebase Functionality

    public String CalculateAnswer() {
        Expression e = new ExpressionBuilder(formulaEquationForParser.toString())
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

    public void SetAnswerTextView(TextView textView, String answer) {
        Log.d("Check", "Here I am, this is me!");
        textView.setText(answer);

    }
}
