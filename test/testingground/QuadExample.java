package testingground;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

       
public class QuadExample {
   

           
    public void start() {
       
        try {
           
            Display.setDisplayMode(new DisplayMode(800,600));
           
            Display.create();
           
        } catch (LWJGLException e) {
           
            e.printStackTrace();
           
            System.exit(0);
           
        }
       

       
// init OpenGL
       
        GL11.glMatrixMode(GL11.GL_PROJECTION);
       
        GL11.glLoadIdentity();
       
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
       
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

float i = 0;

        while (!Display.isCloseRequested()) {

// Clear the screen and depth buffer

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);



// set the color of the quad (R,G,B,A)
            i = i+0.1f;
            i = i%360;
            GL11.glPushMatrix();
            GL11.glColor3f(0.5f, 0.5f, 1.0f);
            GL11.glTranslated(220,250,0);
            GL11.glRotated(i,0,0,1);
            GL11.glTranslated(-220, -250, 0);

// draw quad
            float x = 100;
            float y = 100;
            GL11.glTranslatef(100,100,0);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(20,50);
            GL11.glVertex2f(20+200,50);
            GL11.glVertex2f(20+200,50+200);
            GL11.glVertex2f(20,50+200);
            GL11.glEnd();
            GL11.glPopMatrix();

            GL11.glColor3f(1.0f, 0.0f, 0.0f);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(-10, -10);
            GL11.glVertex2f(10,-10);
            GL11.glVertex2f(10,10);
            GL11.glVertex2f(-10,10);
            GL11.glEnd();
            GL11.glPopMatrix();


            GL11.glPushMatrix();
            GL11.glColor3f(0.0f, 1.0f, 0.0f);
            GL11.glTranslated(0,100,0);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(-10, -10);
            GL11.glVertex2f(10,-10);
            GL11.glVertex2f(10,10);
            GL11.glVertex2f(-10,10);
            GL11.glEnd();
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslated(100,0,0);
            GL11.glColor3f(0.0f, 0.0f, 1.0f);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(-10, -10);
            GL11.glVertex2f(10,-10);
            GL11.glVertex2f(10,10);
            GL11.glVertex2f(-10,10);
            GL11.glEnd();
            GL11.glPopMatrix();
           
            Display.update();
           
        }
       

       
        Display.destroy();
       
    }
   

           
    public static void main(String[] argv) {
       
        QuadExample quadExample = new QuadExample();
       
        quadExample.start();
       
    }
   
}