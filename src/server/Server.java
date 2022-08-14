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
    private static Map<Integer, Integer> matchedValues = Collections.synchronizedMap(new HashMap<>());
    private static Boolean[] cardList = new Boolean[36];
    // The set of all the print writers for all the clients, used for broadcast.
    private static Set<PrintWriter> writers = new HashSet<>();
    private static AtomicInteger clientIds = new AtomicInteger();
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running...");
        var pool = Executors.newFixedThreadPool(4);
        try (var listener = new ServerSocket(8080)) {
            while (true) {
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

        /**
         * Constructs a handler thread, squirreling away the socket. All the interesting
         * work is done in the run method. Remember the constructor is called from the
         * server's main method, so this has to be as short as possible.
         */
        public Handler(Socket socket, int id) {
            this.socket = socket;
            this.id = id;
        }

        /**
         * Services this thread's client by repeatedly requesting a screen name until a
         * unique one has been submitted, then acknowledges the name and registers the
         * output stream for the client in a global set, then repeatedly gets inputs and
         * broadcasts them.
         */
        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                writers.add(out);
                out.println("You are Client " + id);
                while (true) {
                    int serverResponse = Integer.parseInt(in.nextLine());
//                    System.out.println("Client sent " + serverResponse);
                    if(!matchedValues.containsKey(serverResponse)) {
                        matchedValues.put(serverResponse, id);
                        //broadcast to all
                        for (PrintWriter writer : writers) {
//                            writer.println(serverResponse);
                            writer.println(serverResponse + ",Client " + id + " matched the cards with value " + serverResponse);
                        }
                    }
                    if(matchedValues.size() == 18) {
                        int numClients = writers.size();
                        // calculate client scores
                        int[] clientScores = new int[numClients];
                        for(Map.Entry<Integer, Integer> entry : matchedValues.entrySet()) {
                            clientScores[entry.getValue()]++;
                        }
                        for(int index = 0; index<numClients; index++) {
                            for (PrintWriter writer : writers) {
                                writer.println("Client " + index + " got a score of " + clientScores[index]);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);

            } finally {
                if (out != null) {
                    writers.remove(out);
                }

                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }

}