package com.example.nguyenthidiemkhang_bt033;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVH> implements Filterable{

    ArrayList<Contact> contacts;
    ArrayList<Contact> contactsFilter;
    Listener listener;


    public ContactAdapter(Listener listener, ArrayList<Contact> contacts) {
        this.contacts = contacts;
        this.listener = listener;
        this.contactsFilter = contacts;
    }

    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row,parent,false);
        return new ContactVH(view);
    }

    // Cho nay ne @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, @SuppressLint("RecyclerView") int position) {

        Contact contact = contactsFilter.get(position);
        holder.txName.setText(contact.getFname() + " " + contact.getLname());
        holder.txNumber.setText(contact.getNumber());
        holder.txEmail.setText(contact.getEmail());
        if(contact.getImage() == 0){
            holder.imgContact.setImageResource(R.drawable.avt_temp);
        }else {
            holder.imgContact.setImageResource(contact.getImage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemListener(position, contact);
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactsFilter.size();
    }



    @Override
    public Filter getFilter() {
        return new ContactFilter();
    }

    class ContactVH extends RecyclerView.ViewHolder{

        CircleImageView imgContact;
        TextView txName,txNumber,txEmail;

        public ContactVH(@NonNull View itemView) {
            super(itemView);
            imgContact = itemView.findViewById(R.id.imgContact);
            txName = itemView.findViewById(R.id.txName);
            txNumber = itemView.findViewById(R.id.txNumber);
            txEmail = itemView.findViewById(R.id.txEmail);
        }
    }

    class ContactFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                contactsFilter = contacts;
            } else {
                List<Contact> filteredList = new ArrayList<>();
                for (Contact row : contacts) {
                    if (row.getFname().toLowerCase().contains(charString.toLowerCase()) || row.getNumber().contains(charString) || row.getLname().contains(charString)) {
                        filteredList.add(row);
                    }
                }

                contactsFilter = (ArrayList<Contact>) filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = contactsFilter;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            contactsFilter= (ArrayList<Contact>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public void addContact(Contact contact){
        contactsFilter.add(contact);
        notifyDataSetChanged();
    }

    public void editContact(Contact contact, int pos){
        contactsFilter.set(pos, contact);
        notifyDataSetChanged();
    }

    public void deleteContact(int pos){
        contactsFilter.remove(pos);
        notifyDataSetChanged();
    }

    public void deleteContact(Contact contact){
        contactsFilter.remove(contact);
        notifyDataSetChanged();
    }

    interface Listener {
        default void onItemListener(int pos, Contact contact) {
        }
    }}


