package org.example.Server;

import org.example.Model.DBConn;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
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
            System.out.println("Server started...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Add the client to the manager
                ClientManager.addClient(clientSocket);

                // Delegate client handling to the thread pool
                clientThreadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientThreadPool.shutdown();
        }
    }

    // Handles individual client communication
    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

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
                        System.out.println("Invalid message format.");
                    }
                }
            } catch (IOException e) {
                System.out.println("Client disconnected: " + socket.getInetAddress());
            } finally {
                ClientManager.removeClient(socket);
            }
        }



        private void broadcastMessage(String messagePayload, int senderId) {
            for (Socket client : ClientManager.getClients()) {
                if (!client.equals(socket)) { // Avoid sending the message back to the sender
                    try (DataOutputStream clientOut = new DataOutputStream(client.getOutputStream())) {
                        clientOut.writeUTF(messagePayload);
                        clientOut.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Manages connected clients
    static class ClientManager {
        private static final Set<Socket> clients = new HashSet<>();

        public static synchronized void addClient(Socket client) {
            clients.add(client);
        }

        public static synchronized void removeClient(Socket client) {
            clients.remove(client);
        }

        public static synchronized Set<Socket> getClients() {
            return new HashSet<>(clients);
        }
    }
}
