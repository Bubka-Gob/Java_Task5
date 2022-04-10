package models;

import graphics.Graphics;

import java.io.Serializable;

public abstract class Figure implements Serializable {
    protected float x = 0;
    protected float y = 0;
    protected float rectHeight = 1;
    protected float rectWidth = 1;
    protected float red = 1;
    protected float green = 1;
    protected float blue = 1;
    protected float rotation = 0;

    protected float animationSpeed = 60;
    protected float currentAnimationSpeed = 60;
    protected int animationUpdatesPS = 120;
    protected long animationUpdateTime = 1000000000 / animationUpdatesPS;

    public boolean isAnimating = true;

    protected float[][] vertices;
    protected int verticesNumber = 3;

    protected transient Thread animationThread = null;

    public Figure() {
        countVertices();
        startAnimation();
        animationThread.start();
    }

    public Figure(float x, float y, float height, float width, int verticesNumber, float r, float g, float b) {
        setAxes(x, y);
        setSize(height, width);
        setColor(r, g, b);
        setVerticesNumber(verticesNumber);
        countVertices();
        startAnimation();
        createAnimationThread();
    }

    public abstract void countVertices();

    public abstract void animationStep();

    public void draw() {
        Graphics.drawFigure(x, y, red, green, blue, vertices, rotation);
    }

    public void startAnimation() {
        currentAnimationSpeed = animationSpeed;
    }

    public void stopAnimation() {
        currentAnimationSpeed = 0;
    }

    public void switchAnimation() {
        if(currentAnimationSpeed == 0) {
            startAnimation();
        }
        else {
            stopAnimation();
        }
    }

    public void createAnimationThread() {
        animationThread = new Thread() {
            public void run() {
                long lastUpdateTime = System.nanoTime();
                long timeOverflow = 0;
                while(isAnimating) {
                    long startTime = System.nanoTime();

                    //Сколько обновлений пропущено
                    int updatesSkipped = (int) (((startTime - lastUpdateTime) + timeOverflow) / animationUpdateTime);

                    //Сколько прошло времени, отведенного для текущего обновления
                    long timeLeft = ((startTime - lastUpdateTime) + timeOverflow) % animationUpdateTime;

                    //Восстановить пропущенные обновления
                    for(int i = 0; i < updatesSkipped; i++) {
                        animationStep();
                    }

                    //Выполнить текущее обновление
                    animationStep();

                    long finishTime = System.nanoTime();

                    //Превышение времени, отведенного для текущего обновления
                    timeOverflow = ((finishTime - startTime) + timeLeft) - animationUpdateTime;

                    if(timeOverflow < 0) {
                        try {
                            sleep(-(timeOverflow) / 1000000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        timeOverflow = 0;
                    }

                    lastUpdateTime = System.nanoTime();
                }
            }
        };
        animationThread.start();
    }

    public void changeAnimationSpeed(float addition) {
        if((animationSpeed <= 20) && (addition < 0)) {
            return;
        }
        if((animationSpeed >= 2000)&& (addition > 0)) {
            return;
        }

        animationSpeed += addition;
        if(currentAnimationSpeed != 0) {
            currentAnimationSpeed = animationSpeed;
        }
    }

    public boolean isBelong(float x, float y) {
        return (x < this.x + this.rectWidth / 2) && (x > this.x - this.rectWidth) &&
                (y < this.y + this.rectHeight) && (y > this.y - this.rectHeight);
    }

    public void setAxes(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float height, float width) {
        rectHeight = height;
        rectWidth = width;
    }

    public void setColor(float r, float g, float b) {
        red = Math.max(0, Math.min(1, r));
        green = Math.max(0, Math.min(1, g));
        blue = Math.max(0, Math.min(1, b));
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setVerticesNumber(int number) {
        verticesNumber = number;
    }

    public float[] getAxes() {
        float[] toReturn = {x, y};
        return toReturn;
    }

    public float[] getSize() {
        float[] toReturn = {rectHeight, rectWidth};
        return toReturn;
    }

    public float[] getColor() {
        float[] toReturn = {red, green, blue};
        return toReturn;
    }

    public int getVerticesNumber() {
        return verticesNumber;
    }
}
