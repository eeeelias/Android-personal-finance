package se.mau.eliasmoltedo.p1.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import se.mau.eliasmoltedo.p1.CategoryIn;

@Entity (tableName = "income_table")
@TypeConverters({CategoryInConverter.class, DateInConverter.class})
public class Income {
    @NonNull
    @PrimaryKey //(autoGenerate = true)
    @ColumnInfo(name = "income_id")
    private String incomeId;
    @ColumnInfo(name = "income_date")
    private Date date;
    @ColumnInfo(name = "income_title")
    private String title;
    @ColumnInfo(name = "income_category")
    private CategoryIn category;
    @ColumnInfo(name = "income_amount")
    private String amount;

    public Income(@NonNull String incomeId, Date date, String title, CategoryIn category, String amount){
        //Parameter och variabel ifall autogenererat id inte fungerar.
        //Parameter i konstruktor:      @NonNull String incomeId,
        this.incomeId = incomeId;
        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
    }

    @NonNull
    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(@NonNull String incomeId) {
        this.incomeId = incomeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CategoryIn getCategory() {
        return category;
    }

    public void setCategory(CategoryIn category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
