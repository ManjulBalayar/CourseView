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

public class ChatFragment extends Fragment implements WebSocketListener {

    private String BASE_URL = "ws://10.0.2.2:8080/chat/";

    private Button connectBtn, sendBtn;
    private EditText usernameEtx, msgEtx;
   // private TextView msgTv;
   private LinearLayout usernameLayout;


    private RecyclerView rvMessages;
    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();


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

    @Override
    public void onWebSocketMessage(String message) {
        getActivity().runOnUiThread(() -> {
            Message newMessage = new Message(message, "other");
            messageList.add(newMessage);
            messageAdapter.notifyDataSetChanged();
            rvMessages.scrollToPosition(messageList.size() - 1); // Scroll to the bottom
        });
    }


    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        getActivity().runOnUiThread(() -> {
            Message closeMessage = new Message("---\nconnection closed by " + (remote ? "server" : "local") + "\nreason: " + reason, "system");
            messageList.add(closeMessage);
            messageAdapter.notifyDataSetChanged();
            rvMessages.scrollToPosition(messageList.size() - 1);
        });
    }


    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}
