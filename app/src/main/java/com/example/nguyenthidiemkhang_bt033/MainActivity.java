package com.example.nguyenthidiemkhang_bt033;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.Listener {

    RecyclerView rvContacts;
    ArrayList<Contact> contacts;
    ContactAdapter contactAdapter;
    FloatingActionButton faAddContact;

    int position;

    ActivityResultLauncher<Intent> mLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==RESULT_OK) {
                        if (result.getData().getIntExtra("flag", 0) == 1) {
                            Contact contact = (Contact) result.getData().getSerializableExtra("contact");
                            contactAdapter.addContact(contact);
                        } else if(result.getData().getIntExtra("flag", 0) == 2){
                            Contact contact = (Contact) result.getData().getSerializableExtra("contact");
                            contactAdapter.editContact(contact,position);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Contact");
        rvContacts = findViewById(R.id.rvContacts);

        contacts = App.initDataForContact();
        contactAdapter = new ContactAdapter(MainActivity.this, contacts);
        rvContacts.setAdapter(contactAdapter);

        rvContacts.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        faAddContact = findViewById(R.id.faAdd);
        faAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("flag",1);
                mLauncher.launch(intent);
            }
        });
    }


    @Override
    public void onItemListener(int pos, Contact contact) {
        position = pos;

        InfoDialogBottomSheet dialogBottomSheet = new InfoDialogBottomSheet(MainActivity.this, contact, mLauncher, contactAdapter);
        dialogBottomSheet.findView();
        dialogBottomSheet.show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle(contact.getName());
//        builder.setIcon(getDrawable(contact.getImage()));
//        builder.setMessage(contact.getDescription());
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnuSort){
            Collections.sort(contacts);
            contactAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_sort_menu,menu); // Khai báo hiển thị menu
        MenuItem menuItem = menu.findItem(R.id.mnuSearch);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Kỹ thuật tìm chính xác
                contactAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Kỹ thuật tìm gần đúng
                contactAdapter.getFilter().filter(newText);
                if (newText.isEmpty()){
                    faAddContact.setVisibility(View.VISIBLE);
                }
                else {
                    faAddContact.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        return true;
    }

}