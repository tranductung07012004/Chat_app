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
                    if (parts.length == 4) {
                        int currentUserId = Integer.parseInt(parts[0]);
                        int targetUserId = Integer.parseInt(parts[1]);
                        boolean isGroup = Boolean.parseBoolean(parts[2]);
                        String message = parts[3];

                        // Broadcast the message to other clients
                        broadcastMessage(messagePayload, currentUserId);
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

        private void broadcastMessage(String messagePayload, int senderId) {
            Set<Socket> clients = ClientManager.getClients();
            for (Socket client : clients) {
                if (!client.equals(socket)) { // Avoid sending the message back to the sender
                    try {
                        DataOutputStream clientOut = new DataOutputStream(client.getOutputStream());
                        clientOut.writeUTF(messagePayload);
                        clientOut.flush();
                    } catch (IOException e) {
                        System.err.println("Failed to send message to client " + client.getInetAddress() + ": " + e.getMessage());
                        ClientManager.removeClient(client); // Remove faulty clients
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
