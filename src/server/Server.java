package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collections;

public class Server {
    // Stores all the cards that have been matched
    // key = the value of the card that was matched, value = the client id who got the match
    private static Map<Integer, Integer> matchedValues = Collections.synchronizedMap(new HashMap<>());
    // The set of all the print writers for all the clients, used for broadcast.
    private static Set<PrintWriter> writers = new HashSet<>();
    private static AtomicInteger clientIds = new AtomicInteger();
    private static boolean flag = false;

    public static void main(String[] args) throws Exception {
        System.out.println("MinionMatch is running!!!");
        var pool = Executors.newFixedThreadPool(5);
        try (var listener = new ServerSocket(8080)) {
            while (true) {
                // create a handler (thread) for every client that connects, max 5 clients
                pool.execute(new Handler(listener.accept(), clientIds.getAndIncrement()));
                System.out.println("client connect");
                System.out.println(writers.size());
            }
        }
    }

    private static class Handler implements Runnable {
        private Integer id;
        private Socket socket;
        private Scanner in;
        private PrintWriter out;

        public Handler(Socket socket, int id) {
            this.socket = socket;
            this.id = id;
        }

        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                writers.add(out);
                // Displaying the client id to the client who connected
                out.println("Player " + ( id + 1));
                while (true) {
                    // Read an input from the server
                    int serverResponse = Integer.parseInt(in.nextLine());
                    // Check if the card has already been matched
                    if(!matchedValues.containsKey(serverResponse)) {
                        // Add the matched card to the matched card list
                        matchedValues.put(serverResponse, id);
                        // Broadcast to all clients
                        for (PrintWriter writer : writers) {
                            writer.println(serverResponse + "-Client " + id + " matched the cards with value " + serverResponse);
                        }
                    }
                    // Checking if the game is finished (all the cards have been matched)
                    if(matchedValues.size() == 18 && flag == false) {
                        // Check for concurrency
                        flag = true;
                        // Calculate client scores
                        int[] clientScores = new int[writers.size()];
                        for(Map.Entry<Integer, Integer> entry : matchedValues.entrySet()) {
                            clientScores[entry.getValue()]++;
                        }
                        // Write current client score to the other clients
                        for(int index = 0; index<writers.size(); index++) {
                            for (PrintWriter writer : writers) {
                                writer.println("END GAME-Player " + (index+1) + ": " + clientScores[index]);
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e);

            }
            finally {
                if (out != null) {
                    writers.remove(out);
                }

                try {
                    socket.close();
                }
                catch (IOException e) {
                }
            }
        }
    }

}