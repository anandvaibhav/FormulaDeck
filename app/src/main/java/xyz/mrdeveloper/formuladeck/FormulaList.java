package xyz.mrdeveloper.formuladeck;

import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

/**
 * Created by Vaibhav on 22-02-2017.
 */

public class FormulaList extends Fragment {
    public String question = "x,+,y,*,log,z";

    public FormulaList() {
        Calculate();
    }

//    public void setFormulaList(String decidingFactor, int decidingValue) {
    //decidingValue 1 for Day, 2 for Category.
//        mDecidingFactor = decidingFactor;
//        mDecidingValue = decidingValue;
//    }

    public static FormulaList newInstance() {
        return new FormulaList();
    }

    public void Calculate() {

        EditText et = new EditText(getActivity());
// setting input type filter
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
// setting input max length
   /*     InputFilter maxLengthFilter = new InputFilter.LengthFilter(inp.getLength());
        et.setFilters((new InputFilter[]{ maxLengthFilter }));*/
// settin it to password
        et.setTransformationMethod(PasswordTransformationMethod.getInstance());

        String[] parts = question.split(",");

        for (String part : parts) {
            if (part.equals("+")) {

            } else if (part.equals("-")) {

            } else if (part.equals("*")) {

            } else if (part.equals("/")) {

            }
        }

//            }  else  if (part.equals("+")) {
//
//            }  else  if (part.equals("+")) {
//
//            }  else  if (part.equals("+")) {
//
//            }  else  if (part.equals("+")) {
//
//            }
    }

    public void CalculateAns() {

    }
}