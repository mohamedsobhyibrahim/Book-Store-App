package com.bookstoreapp.network.services;


import com.bookstoreapp.network.models.Book;
import com.bookstoreapp.network.models.SignForm;
import com.bookstoreapp.network.models.Token;
import com.bookstoreapp.network.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.bookstoreapp.network.api.APIConstants.SERVICE_BOOKS;
import static com.bookstoreapp.network.api.APIConstants.SERVICE_LOGIN;
import static com.bookstoreapp.network.api.APIConstants.SERVICE_REGISTER;
import static com.bookstoreapp.network.api.APIConstants.SERVICE_SLIDER;
import static com.bookstoreapp.network.api.APIConstants.SERVICE_USER_BUY_BOOK;
import static com.bookstoreapp.network.api.APIConstants.SERVICE_USER_MY_BOOKS;
import static com.bookstoreapp.network.api.APIConstants.SERVICE_USER_PROFILE;

public interface APIService {

    @GET(SERVICE_SLIDER)
    Call<List<String>> getSlider();

    @GET(SERVICE_BOOKS)
    Call<List<Book>> getBooks();

    @POST(SERVICE_LOGIN)
    Call<Token> login(@Body SignForm signForm);

    @POST(SERVICE_REGISTER)
    Call<Token> register(@Body SignForm signForm);

    @GET(SERVICE_USER_PROFILE)
    Call<User> getProfile(@Header("x-auth-token") String accessToken);

    @POST(SERVICE_USER_BUY_BOOK + "/{id}")
    Call<ResponseBody> buyBook(@Header("x-auth-token") String accessToken,
                               @Path("id") String id);

    @GET(SERVICE_USER_MY_BOOKS)
    Call<List<Book>> getMyBooks(@Header("x-auth-token") String accessToken);
}
