package org.example.Server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatClient {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private volatile boolean running; // Flag to manage listener thread
    private final ExecutorService executorService; // For managing threads
    private final MessageListener messageListener; // Callback for GUI updates

    // Callback interface for message updates
    public interface MessageListener {
        void onMessageReceived(String message);
        void onDeleteMessage(String deleteMessage); // New callback for delete events
        void onLoginMessage(String Message);
    }


    public ChatClient(String serverAddress, int serverPort, MessageListener listener) {
        this.messageListener = listener;
        this.executorService = Executors.newSingleThreadExecutor(); // Manages listener thread
        connectToServer(serverAddress, serverPort);
    }

    private void connectToServer(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            running = true;

            System.out.println("Connected to the chat server.");
            startListening(); // Begin listening for messages
        } catch (IOException e) {
            System.err.println("Failed to connect to the server: " + e.getMessage());
            reconnect(serverAddress, serverPort); // Attempt reconnection
        }
    }

    public void sendMessage(String message, boolean isGroup, int targetId, int currentUserId, long messageId) {
        try {
            // Include messageId in the payload
            // MESSAGE|currentUserId|targetUserId|isGroup|messageId|message
            String payload = String.format("MESSAGE|%d|%d|%b|%d|%s", currentUserId, targetId, isGroup, messageId, message);
            out.writeUTF(payload); // Send the message to the server
            out.flush();
            System.out.println("Message sent: " + payload);
        } catch (IOException e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    public void deleteMessage(long messageId,int currentUserId, int targetId, boolean isGroup) {
        try {
            String payload = String.format("DELETE|%d|%d|%d|%b", messageId,currentUserId, targetId, isGroup);
            out.writeUTF(payload);
            out.flush();
        } catch (IOException e) {
            System.err.println("Failed to send delete request: " + e.getMessage());
        }
    }
    public void notifyLogin(int currentUserId) {
        try {
            // LOGIN|currentUserId
            String payload = String.format("LOGIN|%d", currentUserId);
            out.writeUTF(payload);
            out.flush();
            System.out.println("Login notification sent: " + payload);
        } catch (IOException e) {
            System.err.println("Failed to notify server about login: " + e.getMessage());
        }
    }



    private void startListening() {
        executorService.execute(() -> {
            try {
                while (running) {
                    String message = in.readUTF(); // Receive message from the server
                    System.out.println("Received message: " + message);

                    if (messageListener != null) {
                        // Check if the message is a delete operation
                        if (message.startsWith("DELETE|")) {
                            messageListener.onDeleteMessage(message); // Notify for deletion
                        } else if ((message.startsWith("MESSAGE"))) {
                            messageListener.onMessageReceived(message); // Notify for regular messages
                        } else if(message.startsWith("LOGIN")) {
                            messageListener.onLoginMessage(message);
                        }
                    }

                }
            } catch (IOException e) {
                if (running) {
                    System.err.println("Connection lost: " + e.getMessage());
                    reconnect(socket.getInetAddress().getHostName(), socket.getPort());
                }
            }
        });
    }


    private void reconnect(String serverAddress, int serverPort) {
        try {
            System.out.println("Attempting to reconnect...");
            Thread.sleep(3000); // Wait before retrying
            connectToServer(serverAddress, serverPort); // Attempt reconnection
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Reconnection interrupted.");
        }
    }

    public void close() {
        try {
            running = false;
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            executorService.shutdownNow(); // Shut down the listener thread
            System.out.println("Disconnected from the server.");
        } catch (IOException e) {
            System.err.println("Failed to close the client: " + e.getMessage());
        }
    }
}