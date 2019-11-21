package se.mau.eliasmoltedo.p1;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.List;

import se.mau.eliasmoltedo.p1.database.DatabaseAccess;
import se.mau.eliasmoltedo.p1.database.Expenditure;
import se.mau.eliasmoltedo.p1.database.Income;
import se.mau.eliasmoltedo.p1.database.MyDatabase;


public class AccessDatabase {
    private static final String DATABASE_NAME = "accounting_database";
    private MyDatabase myDatabase;
    private DatabaseAccess databaseAccess;

    public AccessDatabase(Context context){
        myDatabase = Room.databaseBuilder(context, MyDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        databaseAccess = myDatabase.databaseAccess();
    }

    public void insertIncome(Income income){
        databaseAccess.insertIncome(income);
    }

    public void updateIncome(Income income){
        databaseAccess.updateIncome(income);
    }

    public void insertExpenditure(Expenditure expenditure){
        databaseAccess.insertExpenditure(expenditure);
    }

    public void updateExpenditure(Expenditure expenditure){
        databaseAccess.updateExpenditure(expenditure);
    }

    public void deleteIncome(Income income){
        databaseAccess.deleteIncome(income);
    }

    public void deleteExpenditure(Expenditure expenditure){
        databaseAccess.deleteExpenditure(expenditure);
    }

    public List<Income> getAllIncomes(){
        return databaseAccess.getAllIncomes();
    }

    public List<Expenditure> getAllExpenditures(){
        return databaseAccess.getAllExpenditures();
    }

    public void deleteAllIncomes(){
        databaseAccess.deleteAllIncomes();
    }

    public void deleteAllExpenditures(){
        databaseAccess.deleteAllExpenditures();
    }
}
