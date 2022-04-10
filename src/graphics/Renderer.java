package graphics;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import inputs.KeyboardInput;
import inputs.MouseInput;

public class Renderer {
    private static GLWindow window = null;
    public static int screenWidth = 640;
    public static int screenHeight = 360;
    public static float unitsWide = 10;

    public static void init() {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        window = GLWindow.create(capabilities);
        window.setSize(screenWidth, screenHeight);
        window.setResizable(true);
        window.addGLEventListener(new EventListener());
        window.addMouseListener(new MouseInput());
        window.addKeyListener(new KeyboardInput());

        //FPSAnimator animator = new FPSAnimator(window, 60);
        //animator.start();
        window.setVisible(true);
        System.out.println("Renderer initialized");
    }

    public static void render() {
        if(window == null) {
            return;
        }
        window.display();
    }

    public static int getWindowWidth() {
        return window.getWidth();
    }

    public static int getWindowHeight() {
        return window.getHeight();
    }
}
