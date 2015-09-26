package com.silmood.butterknife_example.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.silmood.butterknife_example.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUpFragment extends Fragment {

    @Bind(R.id.container)
    LinearLayout mContainer;

    @Bind(R.id.email)
    EditText email;

    //Also we can bind multiple views into an array
    @Bind({R.id.password, R.id.repeat_password})
    List<EditText> passwordViews;

    EditText firstName;

    EditText lastName;

    public SignUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //The binding process in a fragment is a little bit different, because its view is inflated manually.
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        //So we have to specify WHERE we gonna find the views with @Bind annotation
        ButterKnife.bind(this, root);

        //Additionally if you have to use findViewByID() but you are tired to casting every view, you can use Butterknife.findViewBiId();
        firstName = ButterKnife.findById(root, R.id.first_name);
        lastName = ButterKnife.findById(root, R.id.last_name);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //To avoid memory leaks you have to reset binding
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.sign_up)
    protected void signUp(){
        if (formFilled() && passwordsMatch()){
            showMessage(R.string.signed_up);
        }
        else if (isEmailEmpty()){
            email.setError(getString(R.string.email_error));
        }
        else if (isFirstNameEmpty()){
            firstName.setError(getString(R.string.name_error));
        }
        else if (isLastNameEmpty()){
            lastName.setError(getString(R.string.name_error));
        }
        else if (isPasswordEmpty()){
            //With Butterknife we can apply properties to an array of views
            ButterKnife.apply(passwordViews, EMPTY_ERROR);
        }
        else if (!passwordsMatch()){
            ButterKnife.apply(passwordViews, MATCH_ERROR);
        }
    }

    private void showMessage(int stringId) {
        String message = getString(stringId);
        Snackbar.make(mContainer, message, Snackbar.LENGTH_SHORT).show();
    }

    private boolean passwordsMatch() {
        return (passwordViews.get(0).getText()).toString().contentEquals(passwordViews.get(1).getText());
    }

    private boolean formFilled() {
        return !(isEmailEmpty() || isNameEmpty() || isPasswordEmpty());
    }

    private boolean isEmailEmpty(){
        return email.getText().toString().isEmpty();
    }

    private boolean isNameEmpty() {
        return (firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty());

    }

    public boolean isFirstNameEmpty() {
        return firstName.getText().toString().isEmpty();
    }

    public boolean isLastNameEmpty() {
        return lastName.getText().toString().isEmpty();
    }

    public boolean isPasswordEmpty() {
        for(EditText password : passwordViews){
            if (password.getText().toString().isEmpty())
                return true;
        }

        return false;
    }

    //This action will be applied to every edit text in the array
    static final ButterKnife.Action<EditText> EMPTY_ERROR = new ButterKnife.Action<EditText>() {
        @Override
        public void apply(EditText view, int index) {
            view.setError("You must provide a a password");
        }
    };

    static final ButterKnife.Action<EditText> MATCH_ERROR = new ButterKnife.Action<EditText>() {
        @Override
        public void apply(EditText view, int index) {
            view.setText("");
            view.setError("Passwords don't match");
        }
    };

}
