package com.educaciontacna.drednot.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.educaciontacna.drednot.MainActivity;
import com.educaciontacna.drednot.R;
import com.educaciontacna.drednot.ui.helpers.FirebaseManager;
import com.educaciontacna.drednot.ui.helpers.MyUtilsApp;
import com.educaciontacna.drednot.ui.utils.MyConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mukesh.OtpView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.educaciontacna.drednot.ui.utils.MyConstants.PREFERENCE_CODE_SESSION;
import static com.educaciontacna.drednot.ui.utils.MyConstants.USER_STATUS_CONNECTED;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private final FirebaseFirestore firebaseFirestoreDB = new FirebaseManager().db;
    public String codeSent;
    OtpView verificationcodeet;
    EditText input_phonenumber, _passwordText, _nameText, _emailText;
    Button _signupButton;
    ImageView backbtn;
    String emailst, passst, namest, phonenumber;
    TextView phonenumbertxtll, titletxt, desctxt;
    LinearLayout userinfo_tab, verification_tab, _loginLink, namell, emailll, passwordll, phonenumberll;
    int step = 1;
    //name strings
    String lastName = "";
    String firstName = "";
    Context mcontext;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent = s;

        }
    };
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        mcontext = this;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        _passwordText = findViewById(R.id.input_password);
        _nameText = findViewById(R.id.input_name);
        _emailText = findViewById(R.id.input_email);
        _signupButton = findViewById(R.id.btn_signup);
        input_phonenumber = findViewById(R.id.input_phonenumber);
        _loginLink = findViewById(R.id.link_login);
        namell = findViewById(R.id.namell);
        emailll = findViewById(R.id.emailll);
        passwordll = findViewById(R.id.passwordll);
        phonenumberll = findViewById(R.id.phonenumberll);
        phonenumbertxtll = findViewById(R.id.phonenumbertextll);


        titletxt = findViewById(R.id.titletxt);
        desctxt = findViewById(R.id.desctxt);


        userinfo_tab = findViewById(R.id.userinfo_tab);
        verification_tab = findViewById(R.id.verification_tab);
        verification_tab.setVisibility(View.GONE);

        verificationcodeet = findViewById(R.id.verificationcodeet);
        Button btn = findViewById(R.id.verificationcodebtn);

        backbtn = findViewById(R.id.backbtn);


        hideall();
        namell.setVisibility(View.VISIBLE);

        /*Setting on click listeners*/
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                onBackPressed();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyCode();

            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (step) {
                    case 1:
                        namest = _nameText.getText().toString();

                        if (namest.isEmpty() || havelastname(namest)) {
                            if (namest.isEmpty()) {
                                Toast.makeText(SignUpActivity.this, "Please write your name!", Toast.LENGTH_SHORT).show();
                            } else if (havelastname(namest)) {
                                Toast.makeText(SignUpActivity.this, "Please add full name", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            namell.setVisibility(View.GONE);
                            emailll.setVisibility(View.VISIBLE);
                            titletxt.setText("Choose email");
                            desctxt.setText("enter your email address below.");
                            step++;

                        }
                        break;
                    case 2:
                        emailst = _emailText.getText().toString();
                        if (emailst.isEmpty()) {
                            Toast.makeText(SignUpActivity.this, "Please write your email!", Toast.LENGTH_SHORT).show();
                        } else {
                            emailll.setVisibility(View.GONE);
                            passwordll.setVisibility(View.VISIBLE);
                            titletxt.setText("Create Password");
                            desctxt.setText("Your password must have atleast one symbol & 4 or more characters.");
                            step++;
                        }

                        break;
                    case 3:
                        passst = _passwordText.getText().toString();
                        if (passst.isEmpty()) {

                        } else {
                            passwordll.setVisibility(View.GONE);
                            phonenumberll.setVisibility(View.VISIBLE);
                            phonenumbertxtll.setVisibility(View.VISIBLE);
                            titletxt.setText("Let's Get Started");
                            desctxt.setText("Enter your mobile number to enable 2-step verification.");
                            step++;
                        }
                        break;
                    case 4:
                        phonenumber = input_phonenumber.getText().toString();
                        if (phonenumber.isEmpty()) {

                        } else {
                            phonenumberll.setVisibility(View.GONE);
                            phonenumbertxtll.setVisibility(View.GONE);
                            titletxt.setText("Verification");
                            desctxt.setText("We texted you a code to verify your phone number.");
                            signup();
                            step++;
                        }
                        break;
                }

            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        mAuth.useAppLanguage();

    }

    private void hideall() {
        //removing all the edit text will be opened one by one
        namell.setVisibility(View.GONE);
        emailll.setVisibility(View.GONE);
        passwordll.setVisibility(View.GONE);
        phonenumberll.setVisibility(View.GONE);
        phonenumbertxtll.setVisibility(View.GONE);
    }

    private void startPhoneNumberVerification(String phoneNumber) {

//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode() {

        String code = verificationcodeet.getText().toString();

       progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creando Cuenta...");
        progressDialog.show();

        if (code.isEmpty()) {
            Toast.makeText(this, "Code Required", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            try {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
                signInWithPhoneAuthCredential(credential);

            } catch (Exception e) {
                Toast toast = Toast.makeText(this, "Verification Code is wrong " + e, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            updateUI(user);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });

    }


    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {

            // as we already have the user id you can add this code to signup the user with email because we can reset password with email
            mAuth.createUserWithEmailAndPassword(emailst, passst)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);

                                String firestoreUserId = user.getUid();


                                SharedPreferences.Editor editor;
                                SharedPreferences sharedPreferences = mcontext.getSharedPreferences(PREFERENCE_CODE_SESSION, 0);
                                editor = sharedPreferences.edit();
                                editor.putInt("isLogged", USER_STATUS_CONNECTED);
                                editor.putString("firestoreUserId", firestoreUserId);
                                editor.putString("phone", phonenumber);
                                editor.putString("email", emailst);
                                editor.putString("userName", namest);
//                                editor.putInt("cqPoints", ((Long) document.get("score")).intValue());

                                editor.apply();

                                // Authenticate to app MainSection


                                createUserAccount(firestoreUserId, emailst, phonenumber, namest);


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });

        } else {

            onSignupFailed();
        }
    }


    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        } else {
            userinfo_tab.setVisibility(View.GONE);
            verification_tab.setVisibility(View.VISIBLE);
            startPhoneNumberVerification("+51" + input_phonenumber.getText().toString());
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending Verification Code...");
        progressDialog.show();


        String name = _nameText.getText().toString();

        // TODO: Implement your own signup logic here.


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    public void createUserAccount(String firestoreUserId, String email, String phone,
                                  String username) {

        ArrayList<Integer> empty_num_array = new ArrayList<>();
        ArrayList<String> dataList = new ArrayList<>();

        // Create a new user with a first, middle, and last name
        Map<String, Object> user = new HashMap<>();
        user.put("dateCreated", System.currentTimeMillis());

        user.put("date", new Timestamp(new Date()));
        user.put("name", username);
//        user.put("password", password);
        user.put("username", email);
        user.put("phone", phone);
        user.put("completed_quests", empty_num_array);
//        user.put("completed_quests",empty_num_array);

        // Add a new document with a generated ID

        firebaseFirestoreDB.collection(MyConstants.USER_COLLECTION_FIRE)
                .document(firestoreUserId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        MyUtilsApp.showLogError(TAG,"usuario creado");
                        onSignupSuccess();

                        progressDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        MyUtilsApp.showLogError(TAG,"algo ocurrio");
                        progressDialog.dismiss();
                    }
                });

//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
////                        Log.d("BRUNO", "DocumentSnapshot added with ID: " + documentReference.getId());
//                        Toast.makeText(mcontext, "User Account Created " + email, Toast.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
////                        Log.d("BRUNO", "Something Wrong Happened: " + e);
//
//                        Toast.makeText(mcontext, "Something wrong happened - Try it again", Toast.LENGTH_LONG).show();
//                    }
//                });

    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            _emailText.setFocusable(true);
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


    private boolean havelastname(String UserFullName) {

        Log.e(TAG, "i am here iin have last name function");
        try {
            int firstSpace = UserFullName.indexOf(" "); // detect the first space character
            firstName = UserFullName.substring(0, firstSpace);  // get everything upto the first space character
            lastName = UserFullName.substring(firstSpace).trim(); // get everything after the first space, trimming the spaces off
            Log.e(TAG, "i am here i got first name and last name and firstname is " + firstName);

            return false;
        } catch (Exception e) {
            Log.e(TAG, "i am here i did not got first name and last name");

            return true;

        }

    }


}
