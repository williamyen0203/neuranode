package com.neuranode.neuranodeapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.neuranode.neuranodeapp.R;

import static android.Manifest.permission.READ_CONTACTS;

public class RegisterActivity extends Activity {
    private Firebase firebaseRef;

    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText passwordConfirmField;

    private TextView firstNameMessageText;
    private TextView lastNameMessageText;
    private TextView emailMessageText;
    private TextView passwordMessageText;
    private TextView confirmMessageText;

    private Button registerButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseRef = new Firebase(MainActivity.FIREBASE_URL);

        firstNameField = (EditText) findViewById(R.id.firstNameField);
        lastNameField = (EditText) findViewById(R.id.lastNameField);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        passwordConfirmField = (EditText) findViewById(R.id.passwordConfirmField);

        firstNameMessageText = (TextView) findViewById(R.id.firstNameMessageText);
        lastNameMessageText = (TextView) findViewById(R.id.lastNameMessageText);
        emailMessageText = (TextView) findViewById(R.id.emailMessageText);
        passwordMessageText = (TextView) findViewById(R.id.passwordMessageText);
        confirmMessageText = (TextView) findViewById(R.id.confirmMessageText);

        registerButton = (Button) findViewById(R.id.registerButton);
        backButton = (Button) findViewById(R.id.backButton);

        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameMessageText.setVisibility(View.INVISIBLE);
                lastNameMessageText.setVisibility(View.INVISIBLE);
                emailMessageText.setVisibility(View.INVISIBLE);
                passwordMessageText.setVisibility(View.INVISIBLE);
                confirmMessageText.setVisibility(View.INVISIBLE);
                attemptRegister();
            }
        });

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void attemptRegister() {
        final String firstName = firstNameField.getText().toString();
        final String lastName = lastNameField.getText().toString();
        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();
        final String passwordConfirm = passwordConfirmField.getText().toString();

        if (fieldChecks(firstName, lastName, email, password, passwordConfirm)) {
            firebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    Firebase userRef = firebaseRef.child("users");
                    User user = new User(firstName, lastName, email);
                    Firebase newUserRef = userRef.push();
                    newUserRef.setValue(user);

                    finish();
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    emailMessageText.setText(getResources().getText(R.string.error_email_exists));
                    emailMessageText.setVisibility(View.VISIBLE);
                }
            });
        } else {
            if (!isFirstNameValid(firstName)){
                firstNameMessageText.setVisibility(View.VISIBLE);
            }
            if (!isLastNameValid(lastName)){
                lastNameMessageText.setVisibility(View.VISIBLE);
            }
            if (!isEmailValid(email)) {
                emailMessageText.setText(getResources().getText(R.string.error_invalid_email));
                emailMessageText.setVisibility(View.VISIBLE);
            }
            if (!isPasswordValid(password)) {
                passwordMessageText.setVisibility(View.VISIBLE);
            }
            if (!password.equals(passwordConfirm) || passwordConfirm.length() == 0) {
                confirmMessageText.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean fieldChecks(String firstName, String lastName, String email, String password, String passwordConfirm){
        return isFirstNameValid(firstName)
                && isLastNameValid(lastName)
                && isEmailValid(email)
                && isPasswordValid(password)
                && password.equals(passwordConfirm);
    }

    private boolean isFirstNameValid(String firstName){
        return firstName.length() != 0;
    }

    private boolean isLastNameValid(String lastName){
        return lastName.length() != 0;
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }
}

