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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean signUpModeActive = true;
    TextView changeSignupModetextView;
    EditText passwordEditText;

    //kollar att allt är ifyllt, Om det redan finns en sån användare. annars signar den upp en ny
    public void signUp(View view)
    {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);

        if(usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches(""))
        {
            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(signUpModeActive) //Är vi i signup mode så kör vi signup av ny user
            {
                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null)
                        {
                            Log.i("Signup", "Successful");
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            else   //Annars loggar vi in en befintlig user
            {
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(user != null)
                        {
                            Log.i("Login", "Login successful");
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

        }
    }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout);
      backgroundRelativeLayout.setOnClickListener(this);
      ImageView logoImageView = (ImageView) findViewById(R.id.logoImageView);
      logoImageView.setOnClickListener(this); //För att man ska kunna trycka vartsomhelst på skärmen så att tangentborde stängs ner
      passwordEditText = (EditText) findViewById(R.id.passwordEditText);
      passwordEditText.setOnKeyListener(this);
      changeSignupModetextView = (TextView)findViewById(R.id.changeSignupModetextView);
      changeSignupModetextView.setOnClickListener(this);

    //Spara objekt
//    ParseObject score = new ParseObject("Score");
//    score.put("username", "Marcus");
//    score.put("score", 86);
//    score.saveInBackground(new SaveCallback() {
//      @Override
//      public void done(ParseException e) {
//
//        if(e == null)
//        {
//          Log.i("SaveInBackground", "Successful");
//        }
//        else
//        {
//          Log.i("SaveInBackground", "Failed. Error: " + e.toString());
//        }
//      }
//    });

    //Hämta objekt
//    final ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//    query.getInBackground("LhlEYi5J2O", new GetCallback<ParseObject>() {
//      @Override
//      public void done(ParseObject object, ParseException e) {
//
//        if(e == null && object != null)
//        {
//            object.put("score", 200);
//            object.saveInBackground();
//
//          Log.i("ObjectValue", object.getString("username"));
//          Log.i("ObjectValue", Integer.toString(object.getInt("score")));
//        }
//        else
//        {
//          Log.i("ObjectValue", "Failed to get");
//        }
//
//      }
//    });

//      //skapa klass Tweet, username, tweet save on Parse, then query it, and update the tweet content
//      ParseObject tweet = new ParseObject("Tweet"); //SKapat klassen Tweet
//      tweet.put("username", "Maki");                 //skapat ett username
//      tweet.put("tweet", "First day as a consult!"); //och en tweet
//      tweet.saveInBackground(new SaveCallback() {
//          @Override
//          public void done(ParseException e) {
//
//              if(e == null)
//              {
//                  Log.i("Saved info", "Successful");
//              }
//              else
//              {
//                  Log.i("Saved info", "Failed");
//              }
//
//          }
//      });

//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
//      query.getInBackground("R1ddM3ReWF", new GetCallback<ParseObject>() {  //Hämtar data från id
//          @Override
//          public void done(ParseObject object, ParseException e) {
//
//              if(e == null && object != null)
//              {
//                  object.put("tweet", "Second day as a consult!"); //Uppdaterar
//                  object.saveInBackground();                       //Uppdaterar
//                  Log.i("Obj value", object.getString("username")); //Hämtar username från id
//                  Log.i("Obj value", object.getString("tweet"));  //Hämtar tweet från id
//              }
//              else
//              {
//                  Log.i("ObjectValue", "Failed to get");
//              }
//
//          }
//      });

//      //Hämta alla parse objekt behöver inte specifiera ett id.
//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//      //Om man vill specifiera
//      query.whereEqualTo("username","Marcus");
//      query.setLimit(1);
//      query.findInBackground(new FindCallback<ParseObject>() { //find om man vill hämta alla
//          @Override
//          public void done(List<ParseObject> objects, ParseException e) {
//
//              if( e == null)
//              {
//                  Log.i("findInBackground", "Retrived " + objects.size() + " objects");
//
//                  if(objects.size() > 0)
//                  {
//                      for (ParseObject obj : objects)
//                      {
//                          Log.i("findInBackgroundResult", Integer.toString(obj.getInt("score")));
//                      }
//                  }
//              }
//
//
//          }
//      });

      //Alla som har en score > 199 får 50 extra.
//      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//      query.whereGreaterThanOrEqualTo("score", 200);
//      query.findInBackground(new FindCallback<ParseObject>() {
//          @Override
//          public void done(List<ParseObject> objects, ParseException e) {
//
//              if(e == null)
//              {
//                  if(objects.size() > 0)
//                  {
//                      for (ParseObject obj : objects)
//                      {
//
//                          obj.put("score", obj.getInt("score") + 50);
//                          obj.saveInBackground();
//                          Log.i("findInBackgroundResult", Integer.toString(obj.getInt("score")));
//                      }
//                  }
//              }
//
//          }
//      });

//      //Create user and sign em up!
//      ParseUser user = new ParseUser();
//      user.setUsername("maeki2");
//      user.setPassword("myPass");
//      user.signUpInBackground(new SignUpCallback() {
//          @Override
//          public void done(ParseException e) {
//
//              if(e == null)
//              {
//                  Log.i("Sign up", "Successful");
//              }
//              else
//              {
//                  Log.i("Sign up", "Failed");
//              }
//
//          }
//      });

//      //Logga in en användare
//      ParseUser.logInInBackground("maeki2", "myPas", new LogInCallback() {
//          @Override
//          public void done(ParseUser user, ParseException e) {
//
//              if(user != null)
//              {
//                  Log.i("Login", "Successful");
//              }
//              else
//              {
//                  Log.i("Login", "Failed: " + e.toString());
//              }
//
//          }
//      });

      //Logga ut en användare
//      ParseUser.logOut();
//
//      //testa om user redan är inloggad
//      if(ParseUser.getCurrentUser() != null)
//      {
//          Log.i("currentUser", "User logged in " + ParseUser.getCurrentUser().getUsername());
//      }
//      else
//      {
//          Log.i("currentUser", "User not logged in");
//      }





    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.changeSignupModetextView) //Kollar om den klickade vyn är Signup/login
        {
            Button signupButton = (Button) findViewById(R.id.signupButton);
            if(signUpModeActive)
            {
                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModetextView.setText("Or, Signup");
            }
            else
            {
                signUpModeActive = true;
                signupButton.setText("Signup");
                changeSignupModetextView.setText("Or, Login");
            }
        }
        else if (view.getId() == R.id.backgroundRelativeLayout || view.getId() == R.id.logoImageView) //Klickar man på backgrunden så ska tangentbodet försvinna
        {
            InputMethodManager inputMetodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMetodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0); //Gömmer tangentbordet
        }


    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) //Lyssnar efter ett entertryck
        {
            signUp(view); //Använder funktionen för button signup.
        }

        return false;
    }
}