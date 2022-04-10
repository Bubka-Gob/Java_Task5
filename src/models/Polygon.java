package models;

import graphics.Graphics;

public class Polygon extends Figure{
    public Polygon() {
        super();
    }

    public Polygon(float x, float y, int verticesNumber, float height, float width, float r, float g, float b) {
        super(x, y, height, width, verticesNumber, r, g, b);
    }

    public void countVertices() {
        vertices = new float[verticesNumber][2];
        for(int i = 0; i < verticesNumber; i++) {
            double radian = Math.toRadians(360d/verticesNumber*i);
            float[] vertex  = {(float)Math.sin(radian) * rectWidth, (float)Math.cos(radian) * rectHeight};
            vertices[i] = vertex;
        }
    }

    public void animationStep() {
        rotation += currentAnimationSpeed/animationUpdatesPS;
    }

}
