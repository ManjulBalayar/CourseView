package com.example.navigation_screen.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation_screen.R;

import java.util.List;

/**
 * Adapter for the RecyclerView in a chat interface.
 * This adapter manages message views depending on whether they are sent or received.
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // List of messages to be displayed in the RecyclerView.
    private List<Message> messages;

    // View type constants for sent and received messages.
    private static final int VIEW_TYPE_SENT = 0;
    private static final int VIEW_TYPE_IGNORE = 2;
    private static final int VIEW_TYPE_RECEIVED = 1;

    /**
     * Constructor for MessageAdapter.
     *
     * @param messages List of Message objects to be displayed in the RecyclerView
     */
    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    /**
     *
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
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

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
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

    /**
     * Returns the view type of the item at position for the purposes of view recycling.
     *
     * @param position position to query
     * @return The view type (sent or received) of the item at position.
     */
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

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
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


    /**
     * ViewHolder for sent messages.
     */
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

    /**
     * ViewHolder for received messages.
     */
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
