package inputs;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import models.Field;

public class KeyboardInput implements KeyListener {
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Field.keyboardAction(keyEvent.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
