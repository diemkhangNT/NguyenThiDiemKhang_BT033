package com.example.nguyenthidiemkhang_bt033;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.Contract;
public class InfoDialogBottomSheet extends BottomSheetDialog {

    TextView txFullName, txPhone, txEmail, txBirthday;
    ImageView imImage;
    ImageButton btnClose, btnEdit, btnDelete;
    Contact contact;
    ActivityResultLauncher mLauncher;
    ContactAdapter contactAdapter;

    public InfoDialogBottomSheet(@NonNull Context context, Contact contact,ActivityResultLauncher mLauncher,ContactAdapter contactAdapter ){
        super(context);
        this.contact = contact;
        this.mLauncher = mLauncher;
        this.contactAdapter = contactAdapter;
    }
    public void findView(){
        View view = getLayoutInflater().inflate(R.layout.activity_info, null);

        txFullName = view.findViewById(R.id.tv_name);
        txPhone = view.findViewById(R.id.tv_phone);
        txEmail = view.findViewById(R.id.tv_email);
        txBirthday = view.findViewById(R.id.tv_birthday);
        imImage = view.findViewById(R.id.img_avt);
        btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener( v -> {
            this.dismiss();
        });
        btnEdit = view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddEditActivity.class);
                intent.putExtra("contact", contact);
                intent.putExtra("flag", 2);
                mLauncher.launch(intent);
                dismiss();
            }
        });

        btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Contacts");
                builder.setMessage("Delete ".concat(contact.getFname() + " " + contact.getLname()).concat(" ?"));
                builder.setNegativeButton("No", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                });
                builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    contactAdapter.deleteContact(contact);
                    dialogInterface.dismiss();
                    dismiss();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        txFullName.setText(contact.getFname() + " " + contact.getLname());
        txEmail.setText(contact.getEmail());
        txPhone.setText(contact.getNumber());
        txBirthday.setText(contact.getBirthday());
        imImage.setImageResource(contact.getImage());

        setContentView(view);
    }

}
