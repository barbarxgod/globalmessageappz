package com.example.myapplication.ui.sendmessage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MessageModel;
import com.example.myapplication.OnClickItemEventListener;
import com.example.myapplication.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    List<MessageModel> messageModelList;
    OnClickItemEventListener onClickItemEventListener;

    public MessageAdapter(List<MessageModel> groupModelList, OnClickItemEventListener onClickItemEventListener) {
        this.messageModelList = groupModelList;
        this.onClickItemEventListener = onClickItemEventListener;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessageAdapter.MessageViewHolder holder = new MessageAdapter.MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_createmessage_message, parent, false), onClickItemEventListener);
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

    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView msgNameTxt, msgDescText;
        OnClickItemEventListener onClickItemEventListener;

        public MessageViewHolder(View inflate, OnClickItemEventListener onClickItemEventListener) {
            super(inflate);

            msgNameTxt = inflate.findViewById(R.id.item_createmessage_message_nameTextView);
            msgDescText = inflate.findViewById(R.id.item_createmessage_message_descriptionTextView);
            this.onClickItemEventListener = onClickItemEventListener;

            itemView.setOnClickListener(this);
        }

        public void setData(MessageModel model) {
            msgNameTxt.setText(model.getName());
            msgDescText.setText(model.getDescripton());
        }

        @Override
        public void onClick(View v) {
            onClickItemEventListener.onClickItemEvent(getAdapterPosition());
        }
    }
}
