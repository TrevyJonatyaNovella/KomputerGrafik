package planets;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Planets implements GLEventListener, MouseListener,MouseMotionListener {

    public static void main(String[] args) {
        Frame frame = new Frame("Planet");
        GLCanvas canvas = new GLCanvas();
        final Animator animator = new Animator(canvas);
        canvas.addGLEventListener(new Planets(animator));
        frame.add(canvas);
        frame.setSize(500, 400);

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }
    
    // rotating the scene
    private float view_rotx = 30.0f;
    private float view_roty = 30.0f;

    // remember last mouse position
    private int oldMouseX;
    private int oldMouseY;

    private float m_Vx=0.0f;
    private float m_Vz=0.0f;
    private float m_pV=0.0f;
    private float m_mV=0.0f;
    private float m_pV2=0.0f;
    private float m_mV2=0.0f;

    private Animator m_animator=null;;
    
    private int textureId;
    
    public Planets(Animator a)
    {
        m_animator=a;
    }

    public void init(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
       // prepare ligthsource
        float ambient[] = {0.20f,0.10f,0.4f,1.0f };
        float diffuse[] = {1.0f,1.0f,1.0f,1.0f };
        float position[] = {1.0f,1.0f,1.0f,0.0f };
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient,0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse,0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position,0);

        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_LIGHTING);

        //Set material, yellowish
        float amb[]={0.3f,0.3f,0.0f,1.0f};
        float diff[]={1.0f,1.0f,0.5f,1.0f};
        float spec[]={0.6f,0.6f,0.5f,1.0f};
        float shine=0.50f;
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL.GL_FRONT,GL.GL_SHININESS,shine*128.0f);

        // smooth the drawing
        gl.glShadeModel(GL.GL_SMOOTH);

        // depth sorting
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LESS);

        gl.glEnable(GL.GL_NORMALIZE);

        drawable.addMouseListener(this);
        drawable.addMouseMotionListener(this);
    }

    public void reshape(GLAutoDrawable drawable,int x, int y, int width, int height){
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0)
            height = 1;

        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 200.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    public void display(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_ACCUM_BUFFER_BIT);
        gl.glLoadIdentity();
        glu.gluLookAt(20.0, 20.0, 20.0,0.0, 0.0, 0.0, 0.0, 0.0, 1.0);
        // rotate around x-axis
        gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
        // rotate around z-axis
        gl.glRotatef(view_roty, 0.0f, 0.0f, 1.0f);
        // draw planets
        drawBackGround(gl);
        drawPlanets(gl);
        gl.glFlush();
    }
    
    public void drawBackGround(GL gl) {
        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glDisable(GL.GL_CULL_FACE);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(0, 1, 0, 1, 0, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glDepthMask(false);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureId);
        try {
            File in2 = new File("src/planets/space.jpeg");
            Texture t2 = TextureIO.newTexture(in2, true);
            textureId = t2.getTextureObject();
        } catch(IOException e) {
            e.printStackTrace();
        }
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0f, 0f); gl.glVertex2f(0, 0);
            gl.glTexCoord2f(0f, 1f); gl.glVertex2f(0, 1);
            gl.glTexCoord2f(1f, 1f); gl.glVertex2f(1, 1);
            gl.glTexCoord2f(1f, 0f); gl.glVertex2f(1, 0);
        gl.glEnd();
        
        gl.glDepthMask(true);
        gl.glPopMatrix();
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glDisable(GL.GL_TEXTURE_2D);
    }
    
    // planets
   public void drawPlanets(GL gl)
    {
        GLU glu = new GLU(); // spheres
        GLUquadric glpQ=glu.gluNewQuadric();
        /** the sun in origo */
        /** Material of the sun*/
        someMaterials.SetMaterialGoldenSun(gl);
        /** distance 7 */
        gl.glTranslatef(0.0f,0.5f,0.0f);
        /** radius 4 */
        glu.gluSphere(glpQ,3.0f,20,20);

        gl.glPushMatrix();

        /** a planet */
        /** This planets material */
        someMaterials.setMaterialBluePlanet(gl);
        /** in XY-plane */
        gl.glRotatef(m_pV,1.0f,0.0f,0.0f);
        /** distance 7 */
        gl.glTranslatef(0.0f,7.0f,0.0f);
        /** radius 1 */
        glu.gluSphere(glpQ,2.0f,20,20);

        /** with its only moon */
        /** This moons material */
        someMaterials.setMaterialSilverMoon(gl);
        /** in YZ-pane (of planet) */
        gl.glRotatef(m_mV,1.0f,0.0f,0.0f);
        /** distance 1.5 */
        gl.glTranslatef(0.0f,3.5f,0.0f);
        /** radius 0.5 */
        glu.gluSphere(glpQ,0.5f,20,20);

        gl.glPopMatrix();
        gl.glPushMatrix();

        /** another planet */
        /** This planets material */
        someMaterials.setMaterialObsidianPlanet(gl);
        /** in XZ-plane */
        gl.glRotatef(m_pV2,0.0f,1.0f,0.0f);
        /** distance 10 */
        gl.glTranslatef(10.0f,0.0f,0.0f);
        /** radius 1 */
        glu.gluSphere(glpQ,1.0f,20,20);

        gl.glPushMatrix();

        /** with its first moon */
        /** These moons material */
        someMaterials.setMaterialWhiteMoon(gl);
        /** in XZ-pane (of planet) */
        gl.glRotatef(m_mV2,0.0f,1.0f,0.0f);
        /** distance 3 */
        gl.glTranslatef(3.0f,0.0f,0.0f);
        /** radius 0.5 */
        glu.gluSphere(glpQ,0.5f,20,20);

        gl.glPopMatrix();
        gl.glPushMatrix();

        /** in XY-pane (of planet) */
        gl.glRotatef(m_mV2,0.0f,0.0f,1.0f);
        /** distance 2 */
        gl.glTranslatef(2.0f,0.0f,0.0f);
        /** radius 0.5 */
        glu.gluSphere(glpQ,0.5f,20,20);

        gl.glPopMatrix();

        gl.glPopMatrix();
        glu.gluDeleteQuadric(glpQ);
        // increment model movement
        m_Vz += 2.5f;  if (m_Vz > 360.0f) m_Vz = 0.5f;
        m_Vx += 2.1f;  if (m_Vx > 360.0f) m_Vx = 0.1f;
        m_pV += 2.1f;  if (m_pV > 360.0f) m_pV = 0.1f;
        m_mV += 2.9f;  if (m_mV > 360.0f) m_mV = 0.9f;
        m_pV2 += 2.2f; if (m_pV2 > 360.0f) m_pV2 = 0.2f;
        m_mV2 += 2.9f; if (m_mV2 > 360.0f) m_mV2 = 0.0f;
        
    }

    public void displayChanged(GLAutoDrawable drawable,boolean modeChanged, boolean deviceChanged){ 
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) { }

    public void mousePressed(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON3)
        {
            if(m_animator.isAnimating())
                m_animator.stop();
            else
                m_animator.start();
        }
        oldMouseX = e.getX();
        oldMouseY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();

        float thetaY = 360.0f * ( (float)(x-oldMouseX)/(float)size.width);
        float thetaX = 360.0f * ( (float)(oldMouseY-y)/(float)size.height);

        oldMouseX = x;
        oldMouseY = y;

        view_rotx += thetaX;
        view_roty += thetaY;
    }

    public void mouseMoved(MouseEvent e) {}
    
  
}
