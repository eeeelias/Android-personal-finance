package se.mau.eliasmoltedo.p1.database;

import android.arch.persistence.room.TypeConverter;

import se.mau.eliasmoltedo.p1.CategoryEx;

public class CategoryExConverter {

    @TypeConverter
    public static String toString(CategoryEx category){
        if(category == null){
            return null;
        }
        return category.toString();
    }

    @TypeConverter
    public static CategoryEx toCategoryIn(String s){
        if(s == null){
            return null;
        }
        return CategoryEx.valueOf(s);
    }
}
