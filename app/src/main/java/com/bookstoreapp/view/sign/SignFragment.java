package com.bookstoreapp.view.sign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bookstoreapp.R;
import com.bookstoreapp.network.api.APIClient;
import com.bookstoreapp.network.models.SignForm;
import com.bookstoreapp.network.models.Token;
import com.bookstoreapp.network.models.User;
import com.bookstoreapp.network.services.APIService;
import com.bookstoreapp.util.PrefManager;
import com.bookstoreapp.view.splash.SplashActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignFragment extends Fragment implements View.OnClickListener{

    private View viewSign;
    private View viewProfile;
    private Context context;

    // Sign
    private Group groupSignUp;
    private Button btnSignUpHeader;
    private Button btnSignInHeader;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtPassword;
    private Button btnSign;

    // Profile
    private ImageView imageProfile;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private Button btnLogOut;

    public SignFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        context = Objects.requireNonNull(getActivity()).getApplicationContext();
        initView(view);

        if (PrefManager.retrieveAccessToken(Objects.requireNonNull(getActivity())) != null) {
            viewProfile.setVisibility(View.VISIBLE);
            viewSign.setVisibility(View.GONE);

            initProfileView(view);
            getProfile(PrefManager.retrieveAccessToken(getActivity()));

        } else {
            viewProfile.setVisibility(View.GONE);
            viewSign.setVisibility(View.VISIBLE);

            initSignView(view);
        }
        return view;
    }

    private void initProfileView(View view) {
        imageProfile = view.findViewById(R.id.profile_imageView);
        tvName = view.findViewById(R.id.profile_name_textView);
        tvEmail = view.findViewById(R.id.profile_email_textView);
        tvPhone = view.findViewById(R.id.profile_phone_textView);
        btnLogOut = view.findViewById(R.id.profile_sign_out_button);
        btnLogOut.setOnClickListener(this);
    }

    private void initSignView(View view) {
        groupSignUp = view.findViewById(R.id.sign_sign_up_group);
        btnSignUpHeader = view.findViewById(R.id.sign_signUp_header_button);
        btnSignInHeader = view.findViewById(R.id.sign_signIn_header_button);
        btnSign = view.findViewById(R.id.sign_button);

        edtName = view.findViewById(R.id.sign_name_editText);
        edtEmail = view.findViewById(R.id.sign_email_editText);
        edtPhone = view.findViewById(R.id.sign_mobile_editText);
        edtAddress = view.findViewById(R.id.sign_address_editText);
        edtPassword = view.findViewById(R.id.sign_password_editText);

        btnSignUpHeader.setOnClickListener(this);
        btnSignInHeader.setOnClickListener(this);
        btnSign.setOnClickListener(this);
    }

    private void initView(View view) {
        viewSign = view.findViewById(R.id.layout_sign);
        viewProfile = view.findViewById(R.id.layout_profile);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_signUp_header_button:
                groupSignUp.setVisibility(View.VISIBLE);

                btnSignUpHeader.setTextAppearance(context, R.style.ButtonClicked);
                btnSignInHeader.setTextAppearance(context, R.style.ButtonDisable);

                btnSign.setText(R.string.sign_up);
                break;
            case R.id.sign_signIn_header_button:
                groupSignUp.setVisibility(View.GONE);

                btnSignUpHeader.setTextAppearance(context, R.style.ButtonDisable);
                btnSignInHeader.setTextAppearance(context, R.style.ButtonClicked);

                btnSign.setText(R.string.sign_in);
                break;

            case R.id.sign_button:
                // TODO: Validation

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if (groupSignUp.getVisibility() == View.VISIBLE) {
                    String name = edtName.getText().toString();
                    String phone = edtPhone.getText().toString();
                    String address = edtAddress.getText().toString();

                    signUp(new SignForm(name, phone, email, address, password));
                } else {
                    signIn(new SignForm(email, password));
                }
                break;

            case R.id.profile_sign_out_button:
                PrefManager.storeAccessToken(Objects.requireNonNull(getActivity()), null);
                resetApp();
                break;
        }
    }

    private void getProfile(String accessToken) {
        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.getProfile(accessToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    assert user != null;
                    Picasso.get().load(user.getImage()).into(imageProfile);
                    tvName.setText(user.getName());
                    tvEmail.setText(user.getEmail());
                    tvPhone.setText(user.getPhone());
                }
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn(SignForm signForm) {
        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.login(signForm).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NotNull Call<Token> call, @NotNull Response<Token> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show();
                    assert response.body() != null;
                    storeToken(response.body());
                    resetApp();
                } else {
                    Toast.makeText(context, "Wrong email or password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Token> call, @NotNull Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUp(SignForm signForm) {
        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.register(signForm).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NotNull Call<Token> call, @NotNull Response<Token> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Hi!", Toast.LENGTH_SHORT).show();
                    assert response.body() != null;
                    storeToken(response.body());
                    resetApp();
                } else {
                    Toast.makeText(context, "Wrong email or password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Token> call, @NotNull Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeToken(Token token) {
        PrefManager.storeAccessToken(Objects.requireNonNull(getActivity()), token.getAccessToken());
    }

    private void resetApp() {
        Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), SplashActivity.class));
        getActivity().finish();
    }
}
