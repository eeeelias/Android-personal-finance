package se.mau.eliasmoltedo.p1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Income.class, Expenditure.class}, version=11, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract DatabaseAccess databaseAccess();
}
