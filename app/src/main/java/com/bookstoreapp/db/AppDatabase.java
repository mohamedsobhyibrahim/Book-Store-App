package com.bookstoreapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bookstoreapp.network.models.Book;

@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
}
