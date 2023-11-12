package com.example.navigation_screen.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation_screen.R;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment representing a chat interface in the application.
 * This fragment allows users to connect to a WebSocket server for chat functionality,
 * send messages, and view chat messages from others in real-time.
 */
public class ChatFragment extends Fragment implements WebSocketListener {

    // Base URL for the WebSocket connection.
    private String BASE_URL = "ws://coms-309-030.class.las.iastate.edu:8080/chat/";

    // UI elements for connecting and sending messages.
    private Button connectBtn, sendBtn;
    private EditText usernameEtx, msgEtx;
   // private TextView msgTv;
   private LinearLayout usernameLayout;


    // RecyclerView and adapter for displaying messages.
    private RecyclerView rvMessages;
    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();


    /**
     * Called to have the fragment instantiate its user interface view.
     * Sets up UI components for the chat functionality, including input fields
     * and buttons.
     *
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        /* initialize UI elements */
        connectBtn = root.findViewById(R.id.bt1);
        sendBtn = root.findViewById(R.id.bt2);
        usernameEtx = root.findViewById(R.id.et1);
        msgEtx = root.findViewById(R.id.et2);
      //  msgTv = root.findViewById(R.id.tx1);

        usernameLayout = root.findViewById(R.id.usernameLayout);

        rvMessages = root.findViewById(R.id.rvMessages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvMessages.setLayoutManager(layoutManager);

        messageAdapter = new MessageAdapter(messageList);
        rvMessages.setAdapter(messageAdapter);


        /* connect button listener */
        connectBtn.setOnClickListener(view -> {
            String serverUrl = BASE_URL + usernameEtx.getText().toString();
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(ChatFragment.this);
            usernameLayout.setVisibility(View.GONE);
        });

        /* send button listener */
        sendBtn.setOnClickListener(v -> {
            try {
                String messageToSend = msgEtx.getText().toString();
                WebSocketManager.getInstance().sendMessage(messageToSend);
                msgEtx.setText(""); // Clear the input field after sending

                Message sentMessage = new Message(messageToSend, "user");
                messageList.add(sentMessage);
                messageAdapter.notifyDataSetChanged();
                rvMessages.scrollToPosition(messageList.size() - 1); // Scroll to the bottom
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        return root;
    }

    /**
     * Callback method triggered on receiving a WebSocket message.
     * Updates the chat interface with the received message.
     *
     * @param message The received WebSocket message.
     */
    @Override
    public void onWebSocketMessage(String message) {
        getActivity().runOnUiThread(() -> {
            Message newMessage = new Message(message, "other");
            messageList.add(newMessage);
            messageAdapter.notifyDataSetChanged();
            rvMessages.scrollToPosition(messageList.size() - 1); // Scroll to the bottom
        });
    }


    /**
     *  Callback method triggered when the WebSocket connection is closed.
     *  Logs the reason for closure and updates the UI accordingly.
     *
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        getActivity().runOnUiThread(() -> {
            Message closeMessage = new Message("---\nconnection closed by " + (remote ? "server" : "local") + "\nreason: " + reason, "system");
            messageList.add(closeMessage);
            messageAdapter.notifyDataSetChanged();
            rvMessages.scrollToPosition(messageList.size() - 1);
        });
    }


    /**
     * Callback method triggered when the WebSocket connection is opened.
     *
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    /**
     * Callback method triggered on a WebSocket error.
     *
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {}
}
