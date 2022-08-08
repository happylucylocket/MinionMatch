package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Server {

    private static HashSet<Integer> matchedValues = new HashSet<Integer>();

    // The set of all the print writers for all the clients, used for broadcast.
    private static Set<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running...");
        var pool = Executors.newFixedThreadPool(4);
        try (var listener = new ServerSocket(8080)) {
            while (true) {
                pool.execute(new Handler(listener.accept()));
                System.out.println("client connect");
                System.out.println(writers.size());

            }
        }
    }

    private static class Handler implements Runnable {
        private String name;
        private Socket socket;
        private Scanner in;
        private PrintWriter out;

        /**
         * Constructs a handler thread, squirreling away the socket. All the interesting
         * work is done in the run method. Remember the constructor is called from the
         * server's main method, so this has to be as short as possible.
         */
        public Handler(Socket socket) {
            this.socket = socket;
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

                while (true) {
                    int serverResponse = Integer.parseInt(in.nextLine());
                    if(!matchedValues.contains(serverResponse)) {
                        matchedValues.add(serverResponse);
                        //broadcast to all
                        for (PrintWriter writer : writers) {
                            writer.println(serverResponse);
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