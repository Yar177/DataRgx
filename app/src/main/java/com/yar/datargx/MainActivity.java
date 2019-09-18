package com.yar.datargx;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String pickedDate;

    TextInputEditText usernameInputText;
    TextInputEditText passwordInputText;
    TextInputEditText parentAgeInputText;
    TextInputEditText birthdateTV;

    TextInputLayout bdLabel;

    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();

    }

    private void initUi() {
        usernameInputText = findViewById(R.id.signup_input_email);
        passwordInputText = findViewById(R.id.signup_input_password);
        parentAgeInputText = findViewById(R.id.signup_input_parent_age);
        birthdateTV = findViewById(R.id.birth_date_text_view);
        bdLabel = findViewById(R.id.birth_date_label);

        birthdateTV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){

                 //   birthdateTV.setHint("Enter birth date MM-DD-YYYY");
                    bdLabel.setHint("Enter birth date MM-DD-YYYY");
                   // birthdateTV.setText("Enter birth date MM-DD-YYYY");
//                    if (birthdateTV.getText().toString().isEmpty()){
//                        birthdateTV.setTextColor(getResources().getColor(R.color.gray));
//                        birthdateTV.setText("MM/DD/YYYY");
//
//                    }else {
//                        return;
//                    }
                } else {

                    bdLabel.setHint("Enter birth date");

                    if (birthdateTV.getText().toString().contains("Y")){
                        birthdateTV.setText("");
                    }
                }
            }
        });


        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String mmddyyyy = "MMDDYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    //birthdateTV.setTextColor(getResources().getColor(R.color.black));
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + mmddyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int mon  = Integer.parseInt(clean.substring(0,2));
                        int day  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",mon, day, year);
                    }

                    clean = String.format("%s-%s-%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    birthdateTV.setText(current);
                    birthdateTV.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("+++", "afterTextChanged: =======>" + s.toString());
//                if (s.toString().equalsIgnoreCase("mm-dd-yyyy")){
//                    birthdateTV.setText("");
//                }

                if (birthdateTV.getText().toString().contains("Y")){
                    birthdateTV.setText("");
                }


            }
        };

        birthdateTV.addTextChangedListener(tw);


        signupBtn = findViewById(R.id.btn_signup);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });

//        birthdateTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                datePicker();
//            }
//        });
    }

    private void signUp(View v) {
        return;
    }
}
