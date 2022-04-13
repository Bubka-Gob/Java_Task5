package client;

import models.Figure;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static Socket clientSocket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static List<Figure> figuresBuffer = new ArrayList<>();
    private static Thread connectionThread;

    public static void connect() {
        try {
            clientSocket = new Socket("localhost", 4004);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in  = new ObjectInputStream(clientSocket.getInputStream());


            connectionThread = new Thread() {
                public void run() {
                    while(true) {
                        try {
                            figuresBuffer = (List<Figure>) in.readObject();
                            System.out.println("got from server");
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            connectionThread.start();
            System.out.println("Connected to server");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(List<Figure> list) {
        try {
            out.writeObject(list);
            out.flush();
            System.out.println("Sent to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void flush() {
        figuresBuffer = null;
    }
}
