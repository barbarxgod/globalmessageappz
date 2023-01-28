package com.example.myapplication.ui.createmessage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GroupModel;
import com.example.myapplication.MessageModel;
import com.example.myapplication.R;
import com.example.myapplication.ui.creategroup.GroupAdapter;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    List<MessageModel> messageModelList;

    public MessageAdapter(List<MessageModel> groupModelList) {
        this.messageModelList = groupModelList;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessageViewHolder holder = new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_createmessage_message, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        MessageModel model = messageModelList.get(position);
        holder.setData(model);
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView msgNameTxt, msgDescText;
        public MessageViewHolder(View inflate) {
            super(inflate);

            msgNameTxt = inflate.findViewById(R.id.item_createmessage_message_nameTextView);
            msgDescText = inflate.findViewById(R.id.item_createmessage_message_descriptionTextView);
        }

        public void setData(MessageModel model) {
            msgNameTxt.setText(model.getName());
            msgDescText.setText(model.getDescripton());
        }
    }
}
