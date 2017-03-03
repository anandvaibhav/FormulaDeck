/*
package xyz.mrdeveloper.formuladeck;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;

*/
/**
 * Created by Vaibhav on 04-01-2017.
 *//*


public class FormulaList extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    public ListView mListView;
    public View view;
    public int mDecidingValue;
    public ViewGroup mContainer;
    public LayoutInflater mInflater;

    ArrayList<FormulaDetails> timeTable;
    FormulaAdapter adapter;
    FormulaDetails selectedFormulaDetails;

    public FormulaList() {
    }

    public void setFormulaList(int decidingValue) {
        mDecidingValue = decidingValue;
    }

    public static FormulaList newInstance() {
        return new FormulaList();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.formula_list_view, container, false);
        mContainer = container;
        mInflater = inflater;

        DoRemainingTasks();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void DoRemainingTasks() {
        FormulaAdapter adapter = new FormulaAdapter(getContext());

        final ArrayList<FormulaDetails> formulaDetails = adapter.GetFormulasForChapter(getContext(), mDecidingValue);

        adapter.mDataSource = timeTable;
        // Create list view
        mListView = (ListView) view.findViewById(R.id.schedule_list_view);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FormulaDetails selectedFormulaDetails = timeTable.get(position);
                String selectedEventTitle = selectedFormulaDetails.title;
                Log.d("Check", "hehe01 Here: " + selectedEventTitle);
                String selectedEventDesc = selectedFormulaDetails.description;
                Log.d("Check", "hehe01 Here: " + selectedEventDesc);
                String selectedEventDay = selectedFormulaDetails.day;
                Log.d("Check", "hehe01 Here: " + selectedEventDay);

//                View child = mInflater.inflate(R.layout.event_details, mContainer, true);
//                EventDetailsAdapter eventDetailsAdapter = new EventDetailsAdapter(getContext(), view, mContainer, mInflater, mListView, selectedFormulaDetails);
//                mListView.setAdapter(eventDetailsAdapter);
                isDetailsOpened = true;

                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.event_details_container, EventDetailsFragment.newInstance(selectedFormulaDetails), "FormulaDetails")
                        .commitAllowingStateLoss();
//                getChildFragmentManager()
//                        .beginTransaction()
//                        .addToBackStack(null)
//                       .commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}*/
