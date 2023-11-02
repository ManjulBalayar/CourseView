package com.example.navigation_screen.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation_screen.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messages;

    private static final int VIEW_TYPE_SENT = 0;

    private static final int VIEW_TYPE_IGNORE = 2;

    private static final int VIEW_TYPE_RECEIVED = 1;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_recieved, parent, false);
            return new ReceivedMessageHolder(view);
        } else {
            return null; // Return null for invalid type
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_SENT) {
            ((SentMessageHolder) holder).bind(message);
        } else if (holder.getItemViewType() == VIEW_TYPE_RECEIVED) {
            ((ReceivedMessageHolder) holder).bind(message);
        }
        // Do nothing for invalid type (-1)
    }


    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSender().equals("user")) {
            return VIEW_TYPE_SENT;
        } else if (!message.getSender().contains(":")) { // Check if sender is not a user (doesn't contain colon)
            return VIEW_TYPE_RECEIVED;
        } else {
            return -1; // Invalid type
        }
    }


    @Override
    public int getItemCount() {
        int count = 0;
        for (Message message : messages) {
            if (!message.getSender().contains(":")) {
                count++;
            }
        }
        return count;
    }


    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.tvMessage);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.tvMessage);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());
        }
    }
}
