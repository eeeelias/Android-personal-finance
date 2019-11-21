package se.mau.eliasmoltedo.p1;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import se.mau.eliasmoltedo.p1.database.DatabaseAccess;
import se.mau.eliasmoltedo.p1.database.Expenditure;
import se.mau.eliasmoltedo.p1.database.Income;
import se.mau.eliasmoltedo.p1.fragments.DataFragment;
import se.mau.eliasmoltedo.p1.fragments.DatePickerFragment;
import se.mau.eliasmoltedo.p1.fragments.ExpenditureAddFragment;
import se.mau.eliasmoltedo.p1.fragments.ExpenditureFragment;
import se.mau.eliasmoltedo.p1.fragments.IncomeAddFragment;
import se.mau.eliasmoltedo.p1.fragments.IncomeFragment;
import se.mau.eliasmoltedo.p1.fragments.ResultFragment;

public class Controller {

    private MainActivity mainActivity;
    private DataFragment dataFragment;
    private AccessDatabase access;
    private ExpenditureAddFragment exAddFragment;
    private ExpenditureFragment exListFragment;
    private IncomeAddFragment inAddFragment;
    private IncomeFragment inListFragment;
    private ResultFragment resultFragment;
    private DatePickerFragment datePickerFragment;
    private List<Income> tempInList;



    public Controller (MainActivity mainActivity){
        this.mainActivity = mainActivity;
        initializeDataFragment();
        initializeDatabase();
        initializeAllFragments();
        addData();
        setFragment(dataFragment.getActiveFragment());
    }

    public List<Income> getIncomeList(){
        return inListFragment.getIncomeList();
    }

    public List<Expenditure> getExpenditureList(){
        return exListFragment.getExpenditureList();
    }

    public void addData(){
        Thread t = new Thread(new Runnable(){
            public void run(){
                List<Expenditure> expenditureList = access.getAllExpenditures();
                List<Income> incomeList = access.getAllIncomes();

                exListFragment.setContentToFragment(expenditureList);
                inListFragment.setContentToFragment(incomeList);
            }
        });

        t.start();
        try{
            t.join();
        } catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }

    private void initializeDataFragment() {
        dataFragment = (DataFragment) mainActivity.getFragment("DataFragment");
        Log.d("DataFragment", (dataFragment == null) ? "null" : "notnull");
        if(dataFragment == null){
            dataFragment = new DataFragment();
            mainActivity.addFragment(dataFragment, "DataFragment");
            dataFragment.setActiveFragment("ResultFragment");
        }
        Log.d("ActiveFragment", dataFragment.getActiveFragment());
    }

    private void initializeDatabase(){
        access = new AccessDatabase(mainActivity);
    }

    private void initializeAllFragments(){
        intiDatePickerFragment();
        initExpenditureAddFragment();
        initIncomeAddFragment();
        initExpenditureFragment();
        initIncomeFragment();
        initResultFragment();
    }

    private void intiDatePickerFragment() {
        datePickerFragment = (DatePickerFragment) mainActivity.getFragment("DatePickerFragment");
        if(datePickerFragment == null){
            datePickerFragment = new DatePickerFragment();
        }
        datePickerFragment.setController(this);
    }

    private void initResultFragment() {
        resultFragment = (ResultFragment) mainActivity.getFragment("ResultFragment");
        if(resultFragment == null){
            resultFragment = new ResultFragment();
        }
        resultFragment.setController(this);
    }

    private void initIncomeFragment() {
        inListFragment = (IncomeFragment) mainActivity.getFragment("IncomeFragment");
        if(inListFragment == null){
            inListFragment = new IncomeFragment();
        }
        inListFragment.setController(this);
    }

    private void initExpenditureFragment() {
        exListFragment = (ExpenditureFragment) mainActivity.getFragment("ExpenditureFragment");
        if(exListFragment == null){
            exListFragment = new ExpenditureFragment();
        }
        exListFragment.setController(this);
    }

    private void initIncomeAddFragment() {
        inAddFragment = (IncomeAddFragment) mainActivity.getFragment("IncomeAddFragment");
        if(inAddFragment == null){
            inAddFragment = new IncomeAddFragment();
        }
        inAddFragment.setController(this);
    }

    private void initExpenditureAddFragment() {
        exAddFragment = (ExpenditureAddFragment) mainActivity.getFragment("ExpenditureAddFragment");
        if(exAddFragment == null){
            exAddFragment = new ExpenditureAddFragment();
        }
        exAddFragment.setController(this);
    }

    public void setFragment(String tag){
        switch(tag){
            case "IncomeFragment" : setFragment(inListFragment, "IncomeFragment"); break;
            case "IncomeAddFragment" : setFragment(inAddFragment, "IncomeAddFragment"); break;
            case "ExpenditureFragment" : setFragment(exListFragment, "ExpenditureFragment"); break;
            case "ExpenditureAddFragment" : setFragment(exAddFragment, "ExpenditureAddFragment"); break;
            case "ResultFragment" : setFragment(resultFragment, "ResultFragment"); break;
            case "DatePickerFragment" : setFragment(datePickerFragment, "DatePickerFragment"); break;
        }
    }

    private void setFragment(Fragment fragment, String tag) {
        mainActivity.setFragment(fragment);
        dataFragment.setActiveFragment(tag);
    }

    public void addIncome(final String id, final Date date,
                          final String title, final CategoryIn category,
                          final String amount){
        new Thread(new Runnable(){
            public void run(){
                try{
                    access.insertIncome(new Income(id,date,title,category,amount));
                    List<Income> list = access.getAllIncomes();
                    inListFragment.setContentToFragment(list);
                }catch(NumberFormatException e){
                    Log.d("addIncome", "Exception i Controller.addIncome()");
                }
            }
        }).start();
    }


    public void addExpenditure(final String id, final Date date,
                               final String title, final CategoryEx category,
                               final String amount){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    access.insertExpenditure(new Expenditure(id, date, title, category, amount));
                    List<Expenditure> list = access.getAllExpenditures();
                    exListFragment.setContentToFragment(list);
                }catch(NumberFormatException e){
                    Log.d("addExpenditure", "Exception i Controller.addExpenditure()");
                }

            }
        }).start();
    }



}
