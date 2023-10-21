package com.example.navigation_screen.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.navigation_screen.R;

import org.java_websocket.handshake.ServerHandshake;

public class ChatFragment extends Fragment implements WebSocketListener {

    private String BASE_URL = "ws://10.0.2.2:8080/chat/";

    private Button connectBtn, sendBtn;
    private EditText usernameEtx, msgEtx;
    private TextView msgTv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        /* initialize UI elements */
        connectBtn = root.findViewById(R.id.bt1);
        sendBtn = root.findViewById(R.id.bt2);
        usernameEtx = root.findViewById(R.id.et1);
        msgEtx = root.findViewById(R.id.et2);
        msgTv = root.findViewById(R.id.tx1);

        /* connect button listener */
        connectBtn.setOnClickListener(view -> {
            String serverUrl = BASE_URL + usernameEtx.getText().toString();
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(ChatFragment.this);
        });

        /* send button listener */
        sendBtn.setOnClickListener(v -> {
            try {
                WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return root;
    }

    @Override
    public void onWebSocketMessage(String message) {
        getActivity().runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "\n" + message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        getActivity().runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "---\nconnection closed by " + (remote ? "server" : "local") + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}
