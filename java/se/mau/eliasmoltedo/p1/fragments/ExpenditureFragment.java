package se.mau.eliasmoltedo.p1.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import se.mau.eliasmoltedo.p1.Controller;
import se.mau.eliasmoltedo.p1.R;
import se.mau.eliasmoltedo.p1.adapters.ExpenditureAdapter;
import se.mau.eliasmoltedo.p1.database.Expenditure;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenditureFragment extends Fragment {
    private RecyclerView rvExpenditures;
    private ExpenditureAdapter expenditureAdapter;
    private List<Expenditure> content = new ArrayList<Expenditure>();
    private List<Expenditure> filteredContent = new ArrayList<Expenditure>();

    private Controller controller;
    private Calendar c;
    private DatePickerDialog dpd;
    private Date dateTo;
    private Date dateFrom;

    private Button btnAddExpenditure;
    private Button btnDateFrom;
    private Button btnDateTo;
    private Button btnDateFilter;

    public ExpenditureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        rvExpenditures = view.findViewById(R.id.rvExpenditures);
        rvExpenditures.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenditureAdapter = new ExpenditureAdapter(getActivity(), content);
        expenditureAdapter.setContent(content);
        expenditureAdapter.setController(controller);
        rvExpenditures.setAdapter(expenditureAdapter);
        initComponents(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        expenditureAdapter.setContent(content);
    }

    private void initComponents(View view){
        btnAddExpenditure = view.findViewById(R.id.btnInsertExpend);
        btnDateFrom = view.findViewById(R.id.btnExDateFrom);
        btnDateTo = view.findViewById(R.id.btnExDateTo);
        btnDateFilter = view.findViewById(R.id.btnExDateFilter);

        btnAddExpenditure.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                controller.setFragment("ExpenditureAddFragment");
            }
        });

        btnDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String s = dayOfMonth + "-" + (month+1) + "-" + year;

                        dateFrom = new Date(year, month, dayOfMonth);
                        btnDateFrom.setText(s);
                    }
                }, year, month, day);
                dpd.show();
                if(dateTo != null)
                    btnDateFilter.setEnabled(true);
            }
        });

        btnDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String s = dayOfMonth + "-" + (month+1) + "-" + year;

                        dateTo = new Date(year, month, dayOfMonth);
                        btnDateTo.setText(s);
                    }
                }, year, month, day);
                dpd.show();
                if(dateFrom != null)
                    btnDateFilter.setEnabled(true);
            }
        });

        btnDateFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredContent = getExpendituresWithinDate(dateFrom, dateTo);
                expenditureAdapter.setContent(filteredContent);
            }
        });

        if(dateFrom == null || dateTo == null){
            btnDateFilter.setEnabled(false);
        }

    }

    public List<Expenditure> getExpendituresWithinDate(Date dateFrom, Date dateTo){
        Date date;
        List<Expenditure> tempList = new ArrayList<Expenditure>();

        for(Expenditure e : content){
            date = e.getDate();
            if(dateFrom.compareTo(date) * date.compareTo(dateTo) >= 0)
                tempList.add(e);
        }

        return tempList;
    }

    public List<Expenditure> getExpenditureList(){
        return content;
    }

    public void setContentToFragment(List<Expenditure> content){
        this.content = content;
    }

    public void setController(Controller controller){
        this.controller = controller;
        if(expenditureAdapter != null){
            expenditureAdapter.setController(controller);
        }
    }
}



//package se.mau.eliasmoltedo.p1.fragments;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import se.mau.eliasmoltedo.p1.Controller;
//import se.mau.eliasmoltedo.p1.R;
//import se.mau.eliasmoltedo.p1.adapters.ExpenditureAdapter;
//import se.mau.eliasmoltedo.p1.database.Expenditure;
//import se.mau.eliasmoltedo.p1.database.Income;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class ExpenditureFragment extends Fragment {
//    private RecyclerView rvExpenditure;
//    private ExpenditureAdapter expenditureAdapter;
//    private List<Expenditure> content = new ArrayList<Expenditure>();
//    private Controller controller;
//    //private
//
//    public ExpenditureFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
//        rvExpenditure = view.findViewById(R.id.rvExpenditures);
//        rvExpenditure.setLayoutManager(new LinearLayoutManager(getActivity()));
//        expenditureAdapter = new ExpenditureAdapter(getActivity(), content);
//        expenditureAdapter.setController(controller);
//        rvExpenditure.setAdapter(expenditureAdapter);
//
//        return view;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        expenditureAdapter.setContent(content);
//    }
//
//    public void setContentToFragment(List<Expenditure> content) {
//        this.content = content;
//    }
//
//    public void setController(Controller controller) {
//        this.controller = controller;
//        if (expenditureAdapter != null) {
//            expenditureAdapter.setController(controller);
//        }
//    }
//
//}
