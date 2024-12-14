package org.example.Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT = 12343;
    private static final int MAX_CLIENTS = 10; // Limit for client connections
    private static ServerSocket serverSocket;
    private static ExecutorService clientThreadPool = Executors.newFixedThreadPool(MAX_CLIENTS);

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Add the client to the manager
                ClientManager.addClient(clientSocket);

                // Delegate client handling to the thread pool
                clientThreadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Failed to close the server socket: " + e.getMessage());
            }
            clientThreadPool.shutdown();
        }
    }

    // Handles individual client communication
    static class ClientHandler implements Runnable {
        private final Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                String messagePayload;
                while ((messagePayload = in.readUTF()) != null) {
                    System.out.println("Received: " + messagePayload);

                    // Parse the incoming payload
                    String[] parts = messagePayload.split("\\|");
                    if (parts.length > 0) {
                        String command = parts[0]; // First part of the payload is the command
                        switch (command) {
                            case "LOGIN":
                                handleLogin(parts);
                                break;
                            case "MESSAGE":
                                handleIncomingMessage(parts);
                                break;
                            case "DELETE":
                                handleDeleteMessage(parts);
                                break;
                            default:
                                System.err.println("Unknown command: " + command);
                        }
                    } else {
                        System.err.println("Invalid message format: " + messagePayload);
                    }
                }
            } catch (IOException e) {
                System.out.println("Client disconnected: " + socket.getInetAddress());
            } finally {
                cleanup();
            }
        }

        private void handleIncomingMessage(String[] parts) {
            if (parts.length == 6) { // MESSAGE|currentUserId|targetUserId|isGroup|messageId|message
                int currentUserId = Integer.parseInt(parts[1]);


                String formattedPayload = String.join("|", parts);
                broadcastMessage(formattedPayload, currentUserId);
            } else {
                System.err.println("Invalid message payload for MESSAGE.");
            }
        }

        private void handleDeleteMessage(String[] parts) {
            if (parts.length == 5) { // Validate number of parts
                try {
                    int messageId = Integer.parseInt(parts[1]);
                    int currentUserId = Integer.parseInt(parts[2]);
                    int targetId = Integer.parseInt(parts[3]);

                    boolean isGroup = Boolean.parseBoolean(parts[4]);

                    // Notify all clients about the deletion
                    String deletePayload = String.format("DELETE|%d|%d|%d|%b", messageId,currentUserId, targetId, isGroup);
                    broadcastMessage(deletePayload, currentUserId);
                    System.out.println("Broadcasted delete for message ID: " + messageId);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in delete message payload.");
                }
            } else {
                System.err.println("Invalid delete payload format: " + Arrays.toString(parts));
            }
        }
        private void handleLogin(String[] parts) {
            if (parts.length == 2) { // LOGIN|currentUserId
                try {
                    int currentUserId = Integer.parseInt(parts[1]);
                    System.out.println("User logged in: " + currentUserId);

                    // Notify other clients about the new login
                    String loginNotification = String.format("LOGIN|%d", currentUserId);
                    broadcastMessage(loginNotification, currentUserId);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid user ID format in login payload.");
                }
            } else {
                System.err.println("Invalid login payload format.");
            }
        }

        private void broadcastMessage(String messagePayload, int senderId) {
            synchronized (ClientManager.getClients()) {
                for (Socket client : ClientManager.getClients()) {
                    if (!client.equals(socket)) {
                        try {
                            DataOutputStream clientOut = new DataOutputStream(client.getOutputStream());
                            clientOut.writeUTF(messagePayload);
                            clientOut.flush();
                        } catch (IOException e) {
                            System.err.println("Failed to send message to client: " + e.getMessage());
                            ClientManager.removeClient(client);
                        }
                    }
                }
            }
        }


        private void cleanup() {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                ClientManager.removeClient(socket);
            } catch (IOException e) {
                System.err.println("Failed to clean up client resources: " + e.getMessage());
            }
        }
    }

    // Manages connected clients
    static class ClientManager {
        private static final Set<Socket> clients = Collections.synchronizedSet(new HashSet<>());

        public static synchronized void addClient(Socket client) {
            clients.add(client);
            System.out.println("Client added. Total clients: " + clients.size());
        }

        public static synchronized void removeClient(Socket client) {
            try {
                if (client != null && !client.isClosed()) {
                    client.close();
                }
                clients.remove(client);
                System.out.println("Client removed. Total clients: " + clients.size());
            } catch (IOException e) {
                System.err.println("Error removing client: " + e.getMessage());
            }
        }

        public static synchronized Set<Socket> getClients() {
            return new HashSet<>(clients);
        }
    }
}
