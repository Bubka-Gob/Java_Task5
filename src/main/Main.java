package main;

import client.Client;
import graphics.Renderer;

public class Main {
    public static void main(String[] args) {
        Client.connect();
        Renderer.init();
        MainLoop.start();
    }
}
