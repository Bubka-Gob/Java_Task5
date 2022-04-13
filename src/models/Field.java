package models;
import client.Client;
import com.jogamp.newt.event.KeyEvent;
import fileWorkers.Saver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Field {
    private static List<Figure> figures = new ArrayList<>();

    //Буфер для действий пользователя
    private static Figure newFigure = null;
    private static float deleteX = -10000;
    private static float deleteY = -10000;
    private static float switchX = -10000;
    private static float switchY = -10000;
    private static boolean isStopAnimation = false;
    private static boolean isStartAnimation = false;
    private static float changeSpeedX = -10000;
    private static float changeSpeedY = -10000;
    private static float speedChanges = 0;
    private static String saveName = null;
    private static String loadName = null;

    public static float red = 1;
    public static float green = 1;
    public static float blue = 1;
    public static float width = 1;
    public static float height = 1;
    public static int verticesNumber = 5;
    public static int type = 1;

    public static void display() {
        for(Figure figure: figures) {
            figure.draw();
        }
    }

    public static void update() {
        if(Client.figuresBuffer != null) {
            // Получить данные с сервера
            for(Figure figure: figures) {
                figure.isAnimating = false;
            }
            figures = Client.figuresBuffer;
            for(Figure figure: figures) {
                figure.createAnimationThread();
            }

            Client.flush();
        }

        if(newFigure != null) {
            figures.add(newFigure);
            newFigure = null;
            Client.send(figures);
        }
        if(deleteX != -10000 && deleteY != -10000) {
            for(int i = figures.size() - 1; i > -1; i--) {
                if(figures.get(i).isBelong(deleteX, deleteY)) {
                    figures.get(i).isAnimating = false;
                    figures.remove(i);
                    Client.send(figures);
                    break;
                }
            }
            deleteX = -10000;
            deleteY = -10000;

        }
        if(switchX != -10000 && switchY != -10000) {
            for(int i = figures.size() - 1; i > -1; i--) {
                if(figures.get(i).isBelong(switchX, switchY)) {
                    figures.get(i).switchAnimation();
                    Client.send(figures);
                    break;
                }
            }
            switchX = -10000;
            switchY = -10000;
        }
        if(changeSpeedX != -10000 && changeSpeedY != -10000) {
            for(int i = figures.size() - 1; i > -1; i--) {
                if(figures.get(i).isBelong(changeSpeedX, changeSpeedY)) {
                    figures.get(i).changeAnimationSpeed(speedChanges);
                    Client.send(figures);
                    break;
                }
            }
            speedChanges = 0;
            changeSpeedX = -10000;
            changeSpeedY = -10000;
        }
        if(isStopAnimation) {
            for(int i = figures.size() - 1; i > -1; i--) {
                figures.get(i).stopAnimation();
            }
            Client.send(figures);
            isStopAnimation = false;
        }
        if(isStartAnimation) {
            for(int i = figures.size() - 1; i > -1; i--) {
                figures.get(i).startAnimation();
            }
            Client.send(figures);
            isStartAnimation = false;
        }
        if(saveName != null) {
            try {
                Saver.saveBinary(saveName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveName = null;
        }
        if(loadName != null) {
            try {
                for(Figure figure: figures) {
                    figure.isAnimating = false;
                }
                figures = Saver.loadBinary(loadName);
                for(Figure figure: figures) {
                    figure.createAnimationThread();
                }
            } catch (IOException e) {
                for(Figure figure: figures) {
                    figure.isAnimating = true;
                }
                e.printStackTrace();
            }
            loadName = null;
        }

        for(Figure figure: figures) {
            figure.countVertices();
        }

    }

    public static void mouseAction(float x, float y, int button) {
        switch(button) {
            // Левая кнопка
            case 1: {
                switch (type) {
                    case 1 -> newFigure = new Polygon(x, y, verticesNumber, height, width, red, green, blue);
                    case 2 -> newFigure = new Star(x, y, verticesNumber, height, width, red, green, blue);
                }
                break;
            }

            //Средняя кнопка
            case 2: {
                switchX = x;
                switchY = y;
                break;
            }

            //Правая кнопка
            case 3: {
                deleteX = x;
                deleteY = y;
                break;
            }
        }
    }

    public static void scrollAction(float x, float y, float direction) {
        changeSpeedX = x;
        changeSpeedY = y;
        speedChanges = direction * 20;
    }

    public static void keyboardAction(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_3 -> verticesNumber = 3;
            case KeyEvent.VK_4 -> verticesNumber = 4;
            case KeyEvent.VK_5 -> verticesNumber = 5;
            case KeyEvent.VK_6 -> verticesNumber = 6;
            case KeyEvent.VK_7 -> verticesNumber = 7;
            case KeyEvent.VK_8 -> verticesNumber = 8;
            case KeyEvent.VK_9 -> verticesNumber = 9;
            case 45 -> decreaseX(); // Минус
            case 61 -> increaseX(); // Плюс
            case 140 -> decreaseY(); // Num Минус
            case 139 -> increaseY(); // Num Плюс
            case KeyEvent.VK_1 -> type = 1;
            case KeyEvent.VK_2 -> type = 2;
            case KeyEvent.VK_W -> setWhite();
            case KeyEvent.VK_G -> setGreen();
            case KeyEvent.VK_HOME -> isStartAnimation = true;
            case KeyEvent.VK_END -> isStopAnimation = true;
            case KeyEvent.VK_S -> saveName = "binary";
            case KeyEvent.VK_L -> loadName = "binary";
        }
    }

    public static void increaseX() {
        if(width < 2) {
            width += 0.1f;
        }
    }

    public static void decreaseX() {
        if(width > 0.1f) {
            width -= 0.1f;
        }
    }

    public static void increaseY() {
        if(height < 2) {
            height += 0.1f;
        }
    }

    public static void decreaseY() {
        if(height > 0.1f) {
            height -= 0.1f;
        }
    }

    public static void setWhite() {
        red = 1;
        blue = 1;
        green = 1;
    }

    public static void setGreen() {
        red = 0;
        blue = 0;
        green = 1;
    }

    public static List<Figure> getFigures() {
        return figures;
    }
}
