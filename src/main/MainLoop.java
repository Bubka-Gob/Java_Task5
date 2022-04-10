package main;

import graphics.Renderer;
import models.Field;

public class MainLoop {
    private static boolean isRunning = false;

    private static int targetFPS = 120;
    private static int targetTime = 1000000000 / targetFPS;

    public static void start() {
        Thread thread = new Thread() {
            public void run() {
                isRunning = true;

                while(isRunning) {
                    long startTime = System.nanoTime();

                    Field.update();
                    Renderer.render();

                    long timeTaken = System.nanoTime() - startTime;
                    if(timeTaken < targetTime) {
                        try {
                            Thread.sleep((targetTime - timeTaken)/1000000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.setName("MainLoop");
        thread.start();
        System.out.println("Main loop started");
    }

}
