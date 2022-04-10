package inputs;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import graphics.EventListener;
import graphics.Renderer;
import models.Field;

public class MouseInput implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        float x = mouseEvent.getX();
        float y = mouseEvent.getY();
        x = (x / Renderer.getWindowWidth() * Renderer.unitsWide) - (Renderer.unitsWide / 2);
        y = (EventListener.unitsTall / 2) - (y / Renderer.getWindowHeight() * EventListener.unitsTall);
        int button = mouseEvent.getButton();
        Field.mouseAction(x, y, button);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseEvent mouseEvent) {
        float x = mouseEvent.getX();
        float y = mouseEvent.getY();
        x = (x / Renderer.getWindowWidth() * Renderer.unitsWide) - (Renderer.unitsWide / 2);
        y = (EventListener.unitsTall / 2) - (y / Renderer.getWindowHeight() * EventListener.unitsTall);
        Field.scrollAction(x, y, mouseEvent.getRotation()[1]);
    }
}
