package com.mirea.kabanovasvetlana.lesson6;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Superhero.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SuperheroDao superheroDao();
}
