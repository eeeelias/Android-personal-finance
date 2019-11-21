package se.mau.eliasmoltedo.p1.database;

import android.arch.persistence.room.TypeConverter;

import se.mau.eliasmoltedo.p1.CategoryIn;

public class CategoryInConverter {

    @TypeConverter
    public static String toString(CategoryIn category){
        if(category == null){
            return null;
        }
        return category.toString();
    }

    @TypeConverter
    public static CategoryIn toCategoryIn(String s){
        if(s == null){
            return null;
        }
        return CategoryIn.valueOf(s);
    }
}
