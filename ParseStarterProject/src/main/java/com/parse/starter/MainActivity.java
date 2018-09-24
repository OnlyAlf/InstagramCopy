/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  private boolean signUpMode = true;
  TextView changeButtonText;
  EditText password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    password = (EditText) findViewById(R.id.et_password);

    password.setOnKeyListener(this);

    changeButtonText = (TextView) findViewById(R.id.change_mode);

    changeButtonText.setOnClickListener(this);

    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rv);

    relativeLayout.setOnClickListener(this);

    ImageView imageView = (ImageView) findViewById(R.id.iv);

    imageView.setOnClickListener(this);

    if(ParseUser.getCurrentUser() != null){
      showUserList();
    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void signUp(View v){

    EditText username = (EditText) findViewById(R.id.et_username);

    if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){

      Toast.makeText(this, "Username and/or password is required", Toast.LENGTH_SHORT).show();

    }else {

      if (signUpMode) {

        final ParseUser user = new ParseUser();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("Sign up", "Successful");
              Toast.makeText(MainActivity.this, "Created user " + user.getUsername(), Toast.LENGTH_SHORT).show();
              showUserList();
            } else
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });

      }else{

        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if(user != null){
              Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
              showUserList();
            }else
              Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
      }
    }
  }

  @Override
  public void onClick(View view) {

    if(view.getId() == R.id.change_mode){

      Button signUp = (Button) findViewById(R.id.bt_button);

      if(signUpMode){

        signUpMode = false;
        signUp.setText("Login");
        changeButtonText.setText("Or, Sign up");

      }else{

        signUpMode = true;
        signUp.setText("Sign Up");
        changeButtonText.setText("Or, Login");
      }

    }else if(view.getId() == R.id.rv || view.getId() == R.id.iv){

      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }
  }

  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {

    if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){

      signUp(view);

    }

    return false;
  }

  public void showUserList(){
    Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
    startActivity(intent);
  }

}
