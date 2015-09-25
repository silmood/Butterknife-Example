package com.silmood.butterknife_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Demonstrate how to bind components in an activity with butterknife.
 *
 * */

public class LoginActivity extends AppCompatActivity {

    //The bind annotation is used instead of findViewById() method.
    @Bind(R.id.email)
    EditText mEmail;

    @Bind(R.id.password)
    EditText mPassword;

    @Bind(R.id.main_container)
    RelativeLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The magic happen when you call this method. All the widgets with the Bind annotation could be manipulated
        //Such as when we used findViewById();
        ButterKnife.bind(this);
    }


    //The OnClick annotation it's a clean listener implementation. No more setOnClickListener()
    @OnClick(R.id.login)
    protected void login(){
        if(formFilled())
            showMessage(R.string.login_succeed);

        else if (isMailEmpty())
            mEmail.setError(getString(R.string.email_error));

        else if (isPasswordEmpty())
            mPassword.setError(getString(R.string.password_empty));
    }

    @OnClick(R.id.sign_up)
    protected void launchSignUpActivity () {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    private void showMessage(int stringId) {
        String message = getString(stringId);
        Snackbar.make(mContainer, message, Snackbar.LENGTH_SHORT).show();
    }


    private boolean formFilled() {
        return !(isPasswordEmpty()|| isMailEmpty());
    }

    public boolean isPasswordEmpty() {
        return mPassword.getText().toString().isEmpty();
    }

    public boolean isMailEmpty() {
        return mEmail.getText().toString().isEmpty();
    }
}
