package com.bookstoreapp.view.bookdetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bookstoreapp.network.models.Book;

public class BooksDetailsViewModel extends ViewModel {

    public MutableLiveData<Book> book = new MutableLiveData<>();
}
