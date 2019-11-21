package se.mau.eliasmoltedo.p1.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import se.mau.eliasmoltedo.p1.CategoryEx;

@Entity(tableName = "expenditure_table")
@TypeConverters({CategoryExConverter.class, DateExConverter.class})
public class Expenditure {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "expenditure_id")
    private String expenditureId;
    @ColumnInfo(name = "expenditure_date")
    private Date date;
    @ColumnInfo(name = "expenditure_title")
    private String title;
    @ColumnInfo(name = "expenditure_category")
    private CategoryEx category;
    @ColumnInfo(name = "expenditure_amount")
    private String amount;

    public Expenditure(@NonNull String expenditureId, Date date, String title, CategoryEx category, String amount){

        this.expenditureId = expenditureId;
        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
    }

    @NonNull
    public String getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(@NonNull String expenditureId) {
        this.expenditureId = expenditureId;
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

    public CategoryEx getCategory() {
        return category;
    }

    public void setCategory(CategoryEx category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
