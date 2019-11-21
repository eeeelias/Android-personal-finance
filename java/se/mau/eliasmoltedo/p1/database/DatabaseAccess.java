package se.mau.eliasmoltedo.p1.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DatabaseAccess {
    @Insert
    void insertIncome(Income income);

    @Insert
    void insertExpenditure (Expenditure expenditure);

    @Query("SELECT * FROM income_table")
    List<Income> getAllIncomes();

    @Query("SELECT * FROM expenditure_table")
    List<Expenditure> getAllExpenditures();

    @Update
    void updateIncome(Income income);

    @Update
    void updateExpenditure(Expenditure expenditure);

    @Delete
    void deleteIncome(Income income);

    @Delete
    void deleteExpenditure(Expenditure expenditure);

    @Query("DELETE FROM income_table" )
    void deleteAllIncomes();

    @Query("DELETE FROM expenditure_table")
    void deleteAllExpenditures();

}
