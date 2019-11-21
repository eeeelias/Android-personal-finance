package se.mau.eliasmoltedo.p1.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import se.mau.eliasmoltedo.p1.Controller;
import se.mau.eliasmoltedo.p1.R;
import se.mau.eliasmoltedo.p1.database.Expenditure;
import se.mau.eliasmoltedo.p1.database.Income;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {
    Controller controller;
    private TextView tvName;
    private TextView tvIncome;
    private TextView tvExpenditure;
    private TextView tvBalance;
    private Button btnDateFrom;
    private Button btnDateTo;
    private Button btnDateFilter;
    private FloatingActionButton abUser;
    private EditText dialogET;
    private String userName;

    private Calendar c;
    private DatePickerDialog dpd;


    private Date dateFrom;
    private Date dateTo;

    private List<Income> incomeList;
    private List<Expenditure> expenditureList;
    private int incomeTotal, tempInTotal;
    private int expenditureTotal, tempExTotal;


    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        initComponents(view);
        initListeners(view);
        getDataForIncomeAndExpenditure();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ResultFragment", Activity.MODE_PRIVATE);
        String tvUserText = sharedPreferences.getString("Name", null);
        if(tvUserText != null){
            tvName.setText(tvUserText);
        }


        tvIncome.setText(incomeTotal + ":-");
        tvExpenditure.setText(expenditureTotal + ":-");
        tvBalance.setText("" + (incomeTotal - expenditureTotal) + ":-");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null){
            tvIncome.setText(savedInstanceState.getString("Income"));
            tvExpenditure.setText((savedInstanceState.getString(("Expenditure"))));
            tvBalance.setText((savedInstanceState.getString("Balance")));
            dateFrom = (Date) savedInstanceState.getSerializable("DateFrom");
            dateTo = (Date) savedInstanceState.getSerializable("DateTo");

        }
    }

    private void initListeners(View view) {
        abUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hello", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Välj användarnamn");
                dialogET = new EditText(getContext());
                builder.setView(dialogET);
                builder.setPositiveButton("Klar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userName = dialogET.getText().toString();
                        tvName.setText(userName);
                        Toast.makeText(getContext(), userName, Toast.LENGTH_LONG).show();
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ResultFragment", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor =  sharedPreferences.edit();
                        editor.putString("Name", userName);
                        editor.apply();
                        dialog.cancel();
                    }
                });
                builder.show();

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
                        String s = dayOfMonth + "-" + month + "-" +  year;
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
                        String s = dayOfMonth + "-" + month + "-" +  year;
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
                int tempIncome = getIncomeFilteredAmount(dateFrom, dateTo);
                int tempExpenditure = getExpenditureFilteredAmount(dateFrom, dateTo);

                tvIncome.setText(tempIncome + ":-");
                tvExpenditure.setText(tempExpenditure + ":-");
                tvBalance.setText("" + (tempIncome-tempExpenditure) + ":-");
            }
        });

        if(dateFrom == null || dateTo == null){
            btnDateFilter.setEnabled(false);
        }
    }

    private int getIncomeFilteredAmount(Date from, Date to){
        int amount = 0;
        List<Income> tempList = getIncomesWithinDate(from, to);
        for(Income i : tempList){
            amount += Integer.parseInt(i.getAmount());
        }

        return amount;
    }

    private int getExpenditureFilteredAmount(Date from, Date to){
        int amount = 0;
        List<Expenditure> tempList = getExpendituresWithinDate(from, to);
        for(Expenditure e : tempList){
            amount += Integer.parseInt(e.getAmount());
        }

        return amount;
    }

    private List<Income> getIncomesWithinDate(Date dateFrom, Date dateTo){
        Date date;
        List<Income> tempList = new ArrayList<Income>();

        for(Income i : incomeList){
            date = i.getDate();
            if(dateFrom.compareTo(date) * date.compareTo(dateTo) >= 0)
                tempList.add(i);
        }

        return tempList;
    }

    private List<Expenditure> getExpendituresWithinDate(Date dateFrom, Date dateTo){
        Date date;
        List<Expenditure> tempList = new ArrayList<Expenditure>();

        for(Expenditure e : expenditureList){
            date = e.getDate();
            if(dateFrom.compareTo(date) * date.compareTo(dateTo) >= 0)
                tempList.add(e);
        }

        return tempList;
    }

    private void getDataForIncomeAndExpenditure() {

        if(controller != null) {
            controller.addData();
            incomeList = controller.getIncomeList();
            expenditureList = controller.getExpenditureList();
        }



        for(Income i : incomeList){
            tempInTotal += Integer.parseInt(i.getAmount());
        }
        if(tempInTotal != incomeTotal){
            incomeTotal = tempInTotal;
        }

        for(Expenditure e : expenditureList){
            tempExTotal += Integer.parseInt(e.getAmount());
        }
        if(tempExTotal != expenditureTotal){
            expenditureTotal = tempExTotal;
        }

        tempExTotal = 0;
        tempInTotal = 0;
    }

    private void initComponents(View view) {
        tvName = view.findViewById(R.id.tvName);
        tvIncome = view.findViewById(R.id.tvIncome);
        tvExpenditure = view.findViewById(R.id.tvExpenditure);
        tvBalance = view.findViewById(R.id.tvBalance);
        abUser = view.findViewById(R.id.abUser);

        btnDateFilter = view.findViewById(R.id.btnDateFilter);
        btnDateFrom = view.findViewById(R.id.btnDateFrom);
        btnDateTo = view.findViewById(R.id.btnDateTo);
    }

    public void setController(Controller controller){
        this.controller = controller;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Income", tvIncome.getText().toString());
        outState.putString("Expenditure", tvExpenditure.getText().toString());
        outState.putString("Balance", tvBalance.getText().toString());
        outState.putSerializable("DateFrom", dateFrom);
        outState.putSerializable("DateTo", dateTo);
    }
}
