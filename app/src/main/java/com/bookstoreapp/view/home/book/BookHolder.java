package com.bookstoreapp.view.home.book;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookstoreapp.R;
import com.bookstoreapp.network.models.Book;
import com.squareup.picasso.Picasso;

public class BookHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView tvBookName;

    private TextView tvPrice;

    private TextView tvOldPrice;
    private View viewHasDiscount;

    private TextView tvFree;

    // FEATURE
    private RatingBar ratingBar;

    // MY BOOKS
    public Button btnOpen;

    private BookType bookType;

    public BookHolder(@NonNull View itemView, BookType bookType) {
        super(itemView);
        this.bookType = bookType;
        initView(itemView);
    }

    private void initView(View view) {
        imageView = view.findViewById(R.id.book_imageView);
        tvBookName = view.findViewById(R.id.book_name_textView);
        tvPrice = view.findViewById(R.id.book_price_textView);
        tvOldPrice = view.findViewById(R.id.book_old_price_textView);
        viewHasDiscount = view.findViewById(R.id.has_discount_group);
        tvFree = view.findViewById(R.id.book_free_textView);

        if (bookType == BookType.FEATURE || bookType == BookType.MY_BOOKS) {
            ratingBar = view.findViewById(R.id.book_ratingBar);
            btnOpen = view.findViewById(R.id.book_open_button);
        }
    }

    public void bind(Book book) {
        Picasso.get()
                .load(book.getImage())
                .placeholder(R.drawable.img_placeholder)
                .into(imageView);

        tvBookName.setText(book.getName());

        if (bookType == BookType.MY_BOOKS) {
            tvFree.setVisibility(View.GONE);
            viewHasDiscount.setVisibility(View.GONE);
            tvPrice.setVisibility(View.GONE);
            btnOpen.setVisibility(View.VISIBLE);

        } else if (bookType == BookType.FEATURE || bookType == BookType.HOME) {
            if (bookType == BookType.FEATURE) {
                ratingBar.setRating(book.getRating());
            }
            if (book.getPrice() == 0) {
                tvFree.setVisibility(View.VISIBLE);
                viewHasDiscount.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);
            } else {
                tvFree.setVisibility(View.GONE);
                tvPrice.setVisibility(View.VISIBLE);

                if (book.getDiscount() != 0) {
                    viewHasDiscount.setVisibility(View.VISIBLE);

                    tvOldPrice.setText(String.valueOf(book.getPrice()));
                    double discount = (book.getPrice() - (book.getPrice() * ((double) book.getDiscount() / 100)));
                    tvPrice.setText(String.valueOf(discount));
                } else {
                    viewHasDiscount.setVisibility(View.GONE);

                    tvPrice.setText(String.valueOf(book.getPrice()));
                }
            }
        }
    }
}
