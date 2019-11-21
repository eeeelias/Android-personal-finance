package se.mau.eliasmoltedo.p1.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import se.mau.eliasmoltedo.p1.adapters.IncomeAdapter;
import se.mau.eliasmoltedo.p1.database.Income;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {
    private RecyclerView rvIncomes;
    private IncomeAdapter incomeAdapter;
    private List<Income> content = new ArrayList<Income>();
    private List<Income> filteredContent = new ArrayList<Income>();

    private Controller controller;
    private Calendar c;
    private DatePickerDialog dpd;
    private Date dateTo;
    private Date dateFrom;

    private Button btnAddIncome;
    private Button btnDateFrom;
    private Button btnDateTo;
    private Button btnDateFilter;

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        rvIncomes = view.findViewById(R.id.rvIncomes);
        rvIncomes.setLayoutManager(new LinearLayoutManager(getActivity()));
        incomeAdapter = new IncomeAdapter(getActivity(), content);
        incomeAdapter.setContent(content);
        incomeAdapter.setController(controller);
        rvIncomes.setAdapter(incomeAdapter);
        initComponents(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null){
            dateFrom = (Date) savedInstanceState.getSerializable("DateFrom");
            dateTo = (Date) savedInstanceState.getSerializable("DateTo");
        }
//        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        incomeAdapter.setContent(content);
    }

    private void initComponents(View view){
        btnAddIncome = view.findViewById(R.id.btnInsertIncome);
        btnDateFrom = view.findViewById(R.id.btnInDateFrom);
        btnDateTo = view.findViewById(R.id.btnInDateTo);
        btnDateFilter = view.findViewById(R.id.btnInDateFilter);

        btnAddIncome.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                controller.setFragment("IncomeAddFragment");
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
                filteredContent = getIncomesWithinDate(dateFrom, dateTo);
                incomeAdapter.setContent(filteredContent);
            }
        });

        if(dateFrom == null || dateTo == null){
            btnDateFilter.setEnabled(false);
        }

    }

    public List<Income> getIncomesWithinDate(Date dateFrom, Date dateTo){
        Date date;
        List<Income> tempList = new ArrayList<Income>();

        for(Income i : content){
            date = i.getDate();
            if(dateFrom.compareTo(date) * date.compareTo(dateTo) >= 0)
                tempList.add(i);
        }

        return tempList;
    }

    public List<Income> getIncomeList(){
        return content;
    }

    public void setContentToFragment(List<Income> content){
        this.content = content;
    }

    public void setController(Controller controller){
        this.controller = controller;
        if(incomeAdapter != null){
            incomeAdapter.setController(controller);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("DateFrom", dateFrom);
        outState.putSerializable("DateTo", dateTo);
    }
}
