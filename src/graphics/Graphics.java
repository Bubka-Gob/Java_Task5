package graphics;

import com.jogamp.opengl.GL2;

import java.util.List;

public class Graphics {
    public static GL2 gl = EventListener.gl;

    public static void drawFigure(float x, float y, float r, float g, float b, float[][] vertices, float rotation) {
        gl.glTranslatef(x, y, 0);
        gl.glRotatef(rotation, 0, 0, 1);
        gl.glColor3f(r, g, b);

        for(int i = 0; i < vertices.length - 1; i++) {
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(vertices[i][0], vertices[i][1]);
            gl.glVertex2f(vertices[i+1][0], vertices[i+1][1]);
            gl.glEnd();
        }
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(vertices[0][0], vertices[0][1]);
        gl.glVertex2f(vertices[vertices.length-1][0], vertices[vertices.length-1][1]);
        gl.glEnd();

        gl.glRotatef( -rotation, 0, 0, 1);
        gl.glTranslatef(-x, -y, 0);
    }


}
