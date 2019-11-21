package se.mau.eliasmoltedo.p1.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateExConverter {
    @TypeConverter
    public static Long toLong(Date date){
        if(date == null){
            return (null);
        }
        return (date.getTime());
    }

    @TypeConverter
    public static Date toDate(Long longDate){
        if(longDate == null){
            return (null);
        }
        return (new Date(longDate));
    }
}
