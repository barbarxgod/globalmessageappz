package com.example.myapplication.ui.addtogroup;

import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DirectoryModel;
import com.example.myapplication.OnClickItemEventListener;
import com.example.myapplication.R;

import java.util.List;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.ContactViewHolder> {
    List<DirectoryModel> directoryModelList;
    OnClickItemEventListener onContactClickListener;

    public DirectoryAdapter(List<DirectoryModel> directoryModelList,OnClickItemEventListener onContactClickListener) {
        this.directoryModelList = directoryModelList;
        this.onContactClickListener = onContactClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addtogroup_directory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DirectoryAdapter.ContactViewHolder holder, int position) {
        DirectoryModel contactModel = directoryModelList.get(position);
        holder.setData(contactModel);
    }

    @Override
    public int getItemCount() {
        return directoryModelList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageImageView;
        TextView nameTextView, numberTextView;
        public ContactViewHolder(View view) {
            super(view);

            imageImageView = view.findViewById(R.id.item_addtogroup_contact_imageImageView);
            nameTextView = view.findViewById(R.id.item_addtogroup_contact_nameTextView);
            numberTextView = view.findViewById(R.id.item_addtogroup_contact_numberTextView);

            view.setOnClickListener(this);
        }

        public void setData(DirectoryModel contactModel) {
            nameTextView.setText(contactModel.getName());
            numberTextView.setText(contactModel.getNumber());

            if(contactModel.getImage() != null) {
                imageImageView.setImageURI(Uri.parse(contactModel.getImage()));
            }
        }

        @Override
        public void onClick(View view) {
            onContactClickListener.onClickItemEvent(getAdapterPosition());
        }
    }
}
