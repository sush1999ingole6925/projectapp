package com.c.projectapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText fname, lname;
    EditText DOB;
    DatePickerDialog datePickerDialog;
    EditText email;
    EditText password, cpassword;
    EditText phone;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    AwesomeValidation awesomeValidation;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        fname = findViewById(R.id.etFname);
        lname = findViewById(R.id.etLname);
        DOB = findViewById(R.id.etDob);
        phone = findViewById(R.id.etPhone);
        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passwordId);
        cpassword = findViewById(R.id.confirm_passwordId);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

//        mDatabaseReference.setValue("Ingole");

//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c= Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR);
//                int mMonth = c.get(Calendar.MONTH);
//                int mDay = c.get(Calendar.DAY_OF_MONTH);
//                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
//                    }
//                }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("User");
//        user = new User();

        if(mFirebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
//        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

        awesomeValidation.addValidation(this, R.id.etFname, "[a-z,A-Z]*", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.etLname, "[a-z,A-Z]*", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.etDob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.birtherror);
        awesomeValidation.addValidation(this, R.id.etPhone, "[5-9]{1}[0-9]{9}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.emailId, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.passwordId, ".{6,}", R.string.passerror);
        awesomeValidation.addValidation(this, R.id.confirm_passwordId, R.id.passwordId, R.string.confirmpasserror);

//        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 568025136000L);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String FirstName = fname.getText().toString().trim();
                final String LastName = lname.getText().toString().trim();
                final String Dob = DOB.getText().toString().trim();
                final String Email = email.getText().toString().trim();
                final String pwd = password.getText().toString().trim();
                final String Phone = phone.getText().toString().trim();
                String checkspaces = "(?=\\s+$)";

////                mDatabase.getReference().child("User");
//                mDatabaseReference.setValue(user);
//                mDatabaseReference.setValue(FirstName);

//                String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

//                mDatabaseReference = mDatabase.getReference().child("user");
//                mDatabaseReference.setValue(user);

                if((FirstName.length() > 20) && (!(FirstName.matches(checkspaces)))) {
                    fname.setError("Please enter valid name");
                    fname.requestFocus();
                }
                else if(Dob.isEmpty()) {
                    DOB.setError("Please enter valid date");
                    DOB.requestFocus();
                }
                else if((LastName.length() > 20) && (!(LastName.matches(checkspaces)))) {
                    lname.setError("Please enter valid name");
                    lname.requestFocus();
                }
                else if(Email.isEmpty()) {
                    email.setError("Please enter Email Id");
                    email.requestFocus();
                }
//                else if((Email.matches(checkEmail))) {
//                    email.setError("Please enter valid Email Id");
//                    email.requestFocus();
//                }
//                else if(pwd.isEmpty()) {
//                    password.setError("Please enter your password");
//                    password.requestFocus();
//                }
//                else if(pwd.length() < 6) {
//                    password.setError("Password must be greater than 6 characters");
//                    password.requestFocus();
//                }
//                else if(FirstName.isEmpty()) {
//                    fname.setError("Please enter your first name");
//                    fname.requestFocus();
//                }
//                else if(LastName.isEmpty()) {
//                    lname.setError("Please enter your last name");
//                    lname.requestFocus();
//                }
                else if(Phone.isEmpty()) {
                    phone.setError("Please enter your phone number");
                    phone.requestFocus();
                }
//                else if(!(Phone.isEmpty())) {
//                    awesomeValidation.addValidation(this, R.id.etPhone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
//                }
                else if( FirstName.isEmpty() && LastName.isEmpty() && Dob.isEmpty() && Phone.isEmpty() && Email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                }
                else if((!(FirstName.isEmpty() && LastName.isEmpty() && Dob.isEmpty() && Phone.isEmpty() && Email.isEmpty() && pwd.isEmpty())) && awesomeValidation.validate()) {
                    mFirebaseAuth.createUserWithEmailAndPassword(Email, pwd) .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String uid = task.getResult().getUser().getUid();
                                User user = new User(uid, FirstName, LastName, Dob, Phone, Email, pwd);
                                mDatabase.getReference().child("Users").child(uid).setValue(user);
//                                addDataToFirebase(uid, FirstName, LastName, Dob, Phone, Email, pwd);
                                Toast.makeText(MainActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
//    private void addDataToFirebase(String Uid, String FirstName, String LastName, String Dob, String Phone, String Email, String pwd) {
//        user.getUid(Uid);
//        user.setFirstName(FirstName);
//        user.setLastName(LastName);
//        user.setDob(Dob);
//        user.setPhone(Phone);
//        user.setEmail(Email);
//        user.setPwd(pwd);
//
//        mDatabaseReference.child("Users").child(Uid).push().setValue(user);
//        mDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mDatabaseReference.setValue(user);
////                Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "Fail to add data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}