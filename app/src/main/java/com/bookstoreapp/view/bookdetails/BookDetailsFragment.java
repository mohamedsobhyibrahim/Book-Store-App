package com.bookstoreapp.view.bookdetails;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookstoreapp.R;
import com.bookstoreapp.databinding.FragmentBookDetailsBinding;
import com.bookstoreapp.network.models.Book;

public class BookDetailsFragment extends Fragment {

    private BooksDetailsViewModel booksDetailsViewModel;
    private NavController navController;
    private Book book;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentBookDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_details, container, false);

        booksDetailsViewModel = ViewModelProviders.of(getActivity()).get(BooksDetailsViewModel.class);

        binding.setLifecycleOwner(getActivity());

        binding.setViewModel(booksDetailsViewModel);

        assert getArguments() != null;
        book = (Book) getArguments().getSerializable("BOOK");

        booksDetailsViewModel.book.setValue(book);

        return binding.getRoot();
    }
}
