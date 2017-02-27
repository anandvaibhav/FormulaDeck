package xyz.mrdeveloper.formuladeck;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String question = "(2 * log10[x]) / ( cos[y])";
    StringBuilder questionForParser;
    LinearLayout mLinearLayout;
    Integer totalEditTexts = 0;
    TextView textViewAnswer;
    Map<String, Double> variablesValue;
    List<String> variables, mathematicalEntities;
    List<EditText> editTextVariableValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_main);

        /*getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, FormulaList.newInstance(), "FormulaList")
                        .addToBackStack("FormulaList")
                        .commitAllowingStateLoss();*/

        variables = new ArrayList<>();
        mathematicalEntities = new ArrayList<>();
        editTextVariableValues = new ArrayList<>();
        variablesValue = new HashMap<>();

        questionForParser = new StringBuilder(question);
        SplitAndShowFormula();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textViewAnswer = new TextView(this);
        textViewAnswer.setTextColor(Color.BLACK);
        textViewAnswer.setTextSize(20);
        textViewAnswer.setText("Fill values you Dumb Ass!");// Use from string.xml
        layoutParams.setMargins(10, 200, 0, 0);

        textViewAnswer.setLayoutParams(layoutParams);
        mLinearLayout.addView(textViewAnswer);
        //SetAnswerTextView("Fill values you Dumb Ass!");
        //CalculateAnswer();
    }

    public void SplitAndShowFormula() {
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

    public void SetQuestionTextView(String toSet) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView mathematicalEntity = new TextView(this);
        mathematicalEntity.setTextColor(Color.BLACK);
        mathematicalEntity.setTextSize(18);
        mathematicalEntity.setText(toSet);

        layoutParams.setMargins(0, 0, 0, 0);
        mathematicalEntity.setLayoutParams(layoutParams);

        mLinearLayout.addView(mathematicalEntity);
    }

    public void SetEditText(String hint) {
        EditText variable = new EditText(this);

        editTextVariableValues.add(variable);
        editTextVariableValues.get(totalEditTexts).setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        editTextVariableValues.get(totalEditTexts).setHint(hint);
        editTextVariableValues.get(totalEditTexts).setHintTextColor(Color.GRAY);
        editTextVariableValues.get(totalEditTexts).setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        mLinearLayout.addView(variable);

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

    //Here I will add Firebase Functionality

    public String CalculateAnswer() {
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

    public void SetAnswerTextView(String answer) {
        Log.d("Check", "Here I am, this is me!");
        textViewAnswer.setText(answer);

    }
}
