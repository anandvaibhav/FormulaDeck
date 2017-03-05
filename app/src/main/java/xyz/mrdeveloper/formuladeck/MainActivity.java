package xyz.mrdeveloper.formuladeck;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
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
    ArrayList<FormulaData> formulaDataList;
    public static int activeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_main);
        activeID = R.id.content_frame;

        UpdateFromFirebase updateFromFirebase = new UpdateFromFirebase(this, this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SetFragment();
            }
        }, 1);

       /* formulaDataList = new ArrayList<>();

        FormulaData formulaData = new FormulaData("FORMULA !", "(2 * log10[x]) / ( cos[y])");
        formulaDataList.add(formulaData);
        formulaData = new FormulaData("FORMULA @", "(2 * log10[x]) / ( cos[y])");
        formulaDataList.add(formulaData);
        formulaData = new FormulaData("FORMULA #", "(2 * log10[x]) / ( cos[y])");
        formulaDataList.add(formulaData);
        formulaData = new FormulaData("FORMULA $", "(2 * log10[x]) / ( cos[y])");
        formulaDataList.add(formulaData);*/

//        variables = new ArrayList<>();
//        mathematicalEntities = new ArrayList<>();
//        editTextVariableValues = new ArrayList<>();
//        variablesValue = new HashMap<>();
//
//        questionForParser = new StringBuilder(question);
//        SplitAndShowFormula();
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        textViewAnswer = new TextView(this);
//        textViewAnswer.setTextColor(Color.BLACK);
//        textViewAnswer.setTextSize(20);
//        textViewAnswer.setText(R.string.no_value_given);
//        layoutParams.setMargins(10, 200, 0, 0);
//
//        textViewAnswer.setLayoutParams(layoutParams);
//        mLinearLayout.addView(textViewAnswer);


        //SetAnswerTextView("Fill values you Dumb Ass!");
        //CalculateAnswer();
    }

    /*
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

    }*/

    public void SetFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, FormulaList.newInstance(), "FormulaList")
                .commitAllowingStateLoss();
    }
}
