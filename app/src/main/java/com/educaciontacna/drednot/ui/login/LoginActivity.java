package com.educaciontacna.drednot.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.educaciontacna.drednot.ui.main.MainActivity;
import com.educaciontacna.drednot.R;
import com.educaciontacna.drednot.ui.utils.MyUtilsApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import static com.educaciontacna.drednot.ui.utils.MyConstants.PREFERENCE_CODE_SESSION;
import static com.educaciontacna.drednot.ui.utils.MyConstants.USER_STATUS_CONNECTED;
import static com.educaciontacna.drednot.ui.utils.MyConstants.USER_STATUS_DISCONNECTED;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    EditText _passwordText;
    Button btn_login;
    TextView link_forgotpassword;
    LinearLayout _signupLink;
    ImageView backbtn;
    //
    HashMap<String, String> user;
    String categoriest;
    //name strings
    String lastName = "";
    String firstName = "";
    private FirebaseAuth mAuth;

    private SharedPreferences.Editor editor;
    private Context context;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);
        context=this;
        /*black icons on top bar like battery etc*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //categoriest = getIntent().getStringExtra("categorie");
        // ButterKnife.inject(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        _signupLink = (LinearLayout) findViewById(R.id.link_signup);
        link_forgotpassword = (TextView) findViewById(R.id.link_forgotpassword);
        backbtn = findViewById(R.id.backbtn);

        /*Setting up the clicklsteners*/
        backbtn.setOnClickListener(this);
        link_forgotpassword.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        _signupLink.setOnClickListener(this);


        // Load User Session Shared Preference

        sharedPreferences = getSharedPreferences(PREFERENCE_CODE_SESSION, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // If log out function from main section was activated - Sign user off the app
        // If user previously connected - Go straight to Main Section
        int user_status = sharedPreferences.getInt("isLogged",USER_STATUS_DISCONNECTED);

        if (user_status == USER_STATUS_CONNECTED){
//            enterMainSection();
            onSignupSuccess();
            MyUtilsApp.showLogError(TAG,"Sesion GUARDADA");
        }else {
            MyUtilsApp.showLogError(TAG,"Sesion no guardada");
        }




    }

    private void signOut(String auth_method) {
        if (auth_method.equals("STANDARD_AUTH")){
            editor.putInt("isLogged",USER_STATUS_DISCONNECTED);
            editor.apply();
        }

    }

    public void login() {
        if (!validate()) {
            onLoginFailed("Ingrese los datos correctamente");
            return;
        }
        //_loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("verificando...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        MyUtilsApp.showLogError(TAG,"error: "+e.getMessage().toString());
                        progressDialog.dismiss();
                    }
                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            MyUtilsApp.showLogError(TAG,"signInWithEmail:failure "+ task.getException());
                            onLoginFailed("Autenticacion Fallida");
                            progressDialog.dismiss();

                            //updateUI(null, "Autenticacion Incorrecta");
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.dismiss();
//                            editor = sharedPreferences.edit();
                            editor.putInt("isLogged", USER_STATUS_CONNECTED);
                            editor.apply();
                            MyUtilsApp.showLogError(TAG,"Sesion INICIADA");

//                            onSignupSuccess();

                            updateUI(user, "Success");

                        }

                        // ...
                    }
                });

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        // onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }

    private void validateFirebaseAuth() {

    }

    public void onSignupSuccess() {
//        _signupButton.setEnabled(true);
        btn_login.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void updateUI(FirebaseUser user, String mensaje) {
        if (user != null) {

            /*so we have succesfully logged in the user */
            Log.e(TAG, "we are in update UI");
            onSignupSuccess();

        }
    }


    private String getfirstname(String name) {

        if (name.split("\\w+").length > 1) {

            lastName = name.substring(name.lastIndexOf(" ") + 1);
            firstName = name.substring(0, name.lastIndexOf(' '));
            return firstName;
        } else {
            firstName = name;
            return firstName;
        }
    }

    private boolean havelastname(String name) {

        if (name.split("\\w+").length > 1) {

            lastName = name.substring(name.lastIndexOf(" ") + 1);
            firstName = name.substring(0, name.lastIndexOf(' '));
            return true;
        } else {
            firstName = name;
            return false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivityStudent
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btn_login.setEnabled(true);
        finish();
    }

    public void onLoginFailed(String menssageError) {
        Toast.makeText(LoginActivity.this, menssageError, Toast.LENGTH_LONG).show();

        btn_login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.backbtn:
                finish();
                break;
            case R.id.link_forgotpassword:
//                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
//                startActivity(intent);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.link_signup:
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                //intent.putExtra("categorie", categoriest);

                startActivityForResult(intent, REQUEST_SIGNUP);
//                startActivity(intent);

                //finish();
                break;
            default:
                break;
        }

    }
}
