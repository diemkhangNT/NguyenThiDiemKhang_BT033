package com.example.nguyenthidiemkhang_bt033;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Random;

public class AddEditActivity extends AppCompatActivity {
    TextInputLayout tifName, tilName, tiEmail, tiPhone, tiBirthday;
    TextInputEditText edfName, edlName, edEmail, edPhone, edBirthday;
    int mYear, mMonth, mDay;
    int flag;
    Contact contactEdit;
//    ImageView imImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_editinfo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        imImage = findViewById(R.id.imageView);

        tifName = findViewById(R.id.textinput_firstname);
        tilName = findViewById(R.id.textinput_lastname);
        tiEmail = findViewById(R.id.textinput_email);
        tiPhone = findViewById(R.id.textinput_phone);
        tiBirthday = findViewById(R.id.textinput_birthday);

        edfName = findViewById(R.id.ed_firstname);
        edlName = findViewById(R.id.ed_lastname);
        edEmail = findViewById(R.id.ed_email);
        edPhone = findViewById(R.id.ed_phone);
        edBirthday = findViewById(R.id.ed_birthday);

        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        if(flag == 1){
            getSupportActionBar().setTitle(R.string.add);
//            imImage.setImageResource(R.drawable.avt_temp);
        }else {
            getSupportActionBar().setTitle(R.string.edit);
            contactEdit = (Contact) intent.getSerializableExtra("contact");
//            imImage.setImageResource(contactEdit.getImage());
            edfName.setText(contactEdit.getFname());
            edlName.setText(contactEdit.getLname());
            edEmail.setText(contactEdit.getEmail());
            edPhone.setText(contactEdit.getNumber());
            edBirthday.setText(contactEdit.getBirthday());
        }


        edBirthday.setOnClickListener(view -> {
            if (view == edBirthday) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                //show dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog ( AddEditActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        edBirthday.setText ( dayOfMonth + "/" + String.format("%02d",month+1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });
    }
    // Create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnusave){
            if(edfName.getText().toString().isEmpty()
                    ||edlName.getText().toString().isEmpty()
                    ||edEmail.getText().toString().isEmpty()
                    ||edPhone.getText().toString().isEmpty()
                    ||edBirthday.getText().toString().isEmpty()){
                tifName.setError("Not null");
                tilName.setError("Not null");
                tiEmail.setError("Not null");
                tiPhone.setError("Not null");
                tiBirthday.setError("Not null");
                return false;
            }else {
                if(flag == 1){
                    Contact contact = new Contact(new Random().nextInt(9999),
                            edfName.getText().toString(),
                            edlName.getText().toString(),
                            0,
                            edPhone.getText().toString(),
                            edEmail.getText().toString(),
                            edBirthday.getText().toString());

                    Intent intent = new Intent();
                    intent.putExtra("contact", contact);
                    intent.putExtra("flag", 1);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    Contact contact = new Contact(contactEdit.getId(),
                            edfName.getText().toString(),
                            edlName.getText().toString(),
                            contactEdit.getImage(),
                            edPhone.getText().toString(),
                            edEmail.getText().toString(),
                            edBirthday.getText().toString());

                    Intent intent = new Intent();
                    intent.putExtra("contact", contact);
                    intent.putExtra("flag", 2);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

