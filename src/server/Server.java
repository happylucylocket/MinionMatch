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
    // stores all the cards that have been matched
    // key = the value of the card that was matched, value = the client id who got the match
    private static Map<Integer, Integer> matchedValues = Collections.synchronizedMap(new HashMap<>());
    // the set of all the print writers for all the clients, used for broadcast.
    private static Set<PrintWriter> writers = new HashSet<>();
    private static AtomicInteger clientIds = new AtomicInteger();
    private static boolean flag = false;


    public static void main(String[] args) throws Exception {
        System.out.println("MinionMatch is running!!!");
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

        public Handler(Socket socket, int id) {
            this.socket = socket;
            this.id = id;
        }

        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                writers.add(out);
                // displaying the client id to the client who connected
                out.println("You are Client " + id);
                while (true) {
                    int serverResponse = Integer.parseInt(in.nextLine());
                    if(!matchedValues.containsKey(serverResponse)) {
                        matchedValues.put(serverResponse, id);
                        // broadcast to all clients
                        for (PrintWriter writer : writers) {
                            writer.println(serverResponse + "-Client " + id + " matched the cards with value " + serverResponse);
                        }
                    }
                    // checking if the game is finished (all the cards have been matched)
                    if(matchedValues.size() == 18 && flag == false) {
                        // check for concurrency
                        flag = true;
                        // calculate client scores
                        int[] clientScores = new int[writers.size()];
                        for(Map.Entry<Integer, Integer> entry : matchedValues.entrySet()) {
                            clientScores[entry.getValue()]++;
                        }
                        for(int index = 0; index<writers.size(); index++) {
                            for (PrintWriter writer : writers) {
                                writer.println("END GAME-Client " + index + ": " + clientScores[index]);
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