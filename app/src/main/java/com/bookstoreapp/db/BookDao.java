package com.bookstoreapp.db;

import androidx.room.Insert;
import androidx.room.Query;

import com.bookstoreapp.network.models.Book;

import java.util.List;

public interface BookDao {

    @Query("SELECT * FROM Book")
    List<Book> getAll();

    @Query("SELECT * FROM Book WHERE id = :id")
    Book getBookById(String id);

    @Insert
    void insertAll(List<Book> books);
}
