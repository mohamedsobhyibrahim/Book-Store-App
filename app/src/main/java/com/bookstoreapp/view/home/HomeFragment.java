package com.bookstoreapp.view.home;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bookstoreapp.R;
import com.bookstoreapp.db.AppDatabase;
import com.bookstoreapp.network.api.APIClient;
import com.bookstoreapp.network.models.Book;
import com.bookstoreapp.network.services.APIService;
import com.bookstoreapp.util.OnItemClick;
import com.bookstoreapp.view.home.book.BookAdapter;
import com.bookstoreapp.view.home.book.BookType;
import com.bookstoreapp.view.home.slider.SliderAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener, OnItemClick {

    private RecyclerView recyclerViewSlider;
    private RecyclerView recyclerViewBooks;
    private Button btnSeeAll;
    private NavController navController;

    public List<Book> books;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);

        loadSlider();
        loadBooks();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void initView(View view) {
        recyclerViewSlider = view.findViewById(R.id.home_slider_recyclerView);
        recyclerViewBooks = view.findViewById(R.id.home_books_recyclerView);
        btnSeeAll = view.findViewById(R.id.home_see_all_button);
        btnSeeAll.setOnClickListener(this);
    }

    private void loadSlider() {
        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.getSlider().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NotNull Call<List<String>> call, @NotNull Response<List<String>> response) {
                if (response.isSuccessful()) {
                    setupSlider(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<String>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSlider(List<String> images) {
        SliderAdapter sliderAdapter = new SliderAdapter(images);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false);

        recyclerViewSlider.setLayoutManager(layoutManager);
        recyclerViewSlider.setAdapter(sliderAdapter);
    }

    private void loadBooks() {
        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.getBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NotNull Call<List<Book>> call, @NotNull Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    books = response.body();
                    setupBooks(books);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Book>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBooks(List<Book> books) {
        BookAdapter bookAdapter = new BookAdapter(books, BookType.HOME, this, null);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false);

        recyclerViewBooks.setLayoutManager(layoutManager);
        recyclerViewBooks.setAdapter(bookAdapter);

//        saveBooksToDB();
    }

//    void saveBooksToDB() {
//        InsetBooksTask insetBooksTask = new InsetBooksTask();
//        insetBooksTask.execute(books);
//    }

    void getBooksFromDB() {
        GetBooksTask getBooksTask = new GetBooksTask();
        getBooksTask.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_see_all_button:
                navController.navigate(R.id.action_homeFragment_to_featuredFragment);
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("BOOK", books.get(position));
        navController.navigate(R.id.action_homeFragment_to_bookDetailsFragment, bundle);
    }

//    class InsetBooksTask extends AsyncTask<List<Book>, Void, Void> {
//
//        @Override
//        protected Void doInBackground(List<Book>... lists) {
//            AppDatabase db = Room.databaseBuilder(Objects.requireNonNull(getActivity()).getApplicationContext(),
//                    AppDatabase.class,
//                    "book-store-db")
//                    // .allowMainThreadQueries()
//                    .build();
//            db.bookDao().insertAll(lists[0]);
//            return null;
//        }
//
////        @Override
////        protected void onProgressUpdate(Void... values) {
////            super.onProgressUpdate(values);
////        }
////
////        @Override
////        protected void onPostExecute(Void aVoid) {
////            super.onPostExecute(aVoid);
////        }
//    }

    class GetBooksTask extends AsyncTask<Void, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(Objects.requireNonNull(getActivity()).getApplicationContext(),
                    AppDatabase.class,
                    "book-store-db")
                    // .allowMainThreadQueries()
                    .build();
            return db.bookDao().getAll();
        }

//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            // I have books
            setupBooks(books);
        }
    }
}
