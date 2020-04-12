package com.bookstoreapp.view.featured;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bookstoreapp.R;
import com.bookstoreapp.network.api.APIClient;
import com.bookstoreapp.network.models.Book;
import com.bookstoreapp.network.services.APIService;
import com.bookstoreapp.util.OnItemClick;
import com.bookstoreapp.view.home.book.BookAdapter;
import com.bookstoreapp.view.home.book.BookType;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeaturedFragment extends Fragment implements OnItemClick {

    private RecyclerView recyclerView;
    private NavController navController;
    private List<Book> books;

    public FeaturedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featured, container, false);
        initView(view);
        loadBooks();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);

        ImageView imgBack = view.findViewById(R.id.back_imageView);
        imgBack.setOnClickListener(v -> navController.popBackStack());
    }

    private void loadBooks() {
        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.getBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NotNull Call<List<Book>> call, @NotNull Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    books = response.body();
                    setupBooks();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Book>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBooks() {
        BookAdapter bookAdapter = new BookAdapter(books, BookType.FEATURE, this, null);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("BOOK", books.get(position));
        navController.navigate(R.id.action_featuredFragment_to_bookDetailsFragment, bundle);
    }
}
