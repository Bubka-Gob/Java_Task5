package models;

import graphics.Graphics;

public class Star extends Figure{
    private boolean isUpscaling = true;
    private boolean isDownScaling = false;
    private float origHeight = 1;
    private float origWidth = 1;

    public Star() {
        super();
        origHeight = rectHeight;
        origWidth = rectWidth;
    }

    public Star(float x, float y, int verticesNumber, float height, float width, float r, float g, float b) {
        super(x, y, height, width, verticesNumber, r, g, b);
        origHeight = rectHeight;
        origWidth = rectWidth;
    }

    @Override
    public void countVertices() {
        vertices = new float[verticesNumber*2][2];
        for(int i = 0; i < verticesNumber*2; i += 2) {
            double radian = Math.toRadians(360d/verticesNumber*i/2);
            float[] vertex  = {(float)Math.sin(radian) * rectWidth, (float)Math.cos(radian) * rectHeight};
            vertices[i] = vertex;
            double radian2 = Math.toRadians(360d/verticesNumber*i/2 + 360d/verticesNumber/2);
            float[] vertex2 = {(float)Math.sin(radian2) * rectWidth * 0.5f, (float)Math.cos(radian2) * rectHeight * 0.5f};
            vertices[i+1] = vertex2;
        }
    }

    public void animationStep() {
        if(isUpscaling) {
            rectHeight += currentAnimationSpeed/animationUpdatesPS/600;
            rectWidth += currentAnimationSpeed/animationUpdatesPS/600;
            if(rectHeight >= origHeight * 1.5) {
                isUpscaling = false;
                isDownScaling = true;
            }
        } else {
            rectHeight -= currentAnimationSpeed/animationUpdatesPS/600;
            rectWidth -= currentAnimationSpeed/animationUpdatesPS/600;
            if(rectHeight <= origHeight * 0.5) {
                isUpscaling = true;
                isDownScaling = false;
            }
        }
    }

}
