package com.example.navigation_screen.ui.chat;

/**
 * Represents a message in the chat system.
 * This class holds the data for a single chat message, including its content and sender information.
 */
public class Message {

    // The text of the message.
    String message;

    // Identifier for the sender of the message.
    String sender;

    /**
     * Constructs a new Message instance.
     *
     * @param message
     * @param sender
     */
    public Message(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    /**
     * Retrieves the message text
     *
     * @return The text of the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the sender's identifier.
     *
     * @return The identifier of the sender of the message
     */
    public String getSender() {
        return sender;
    }
}
