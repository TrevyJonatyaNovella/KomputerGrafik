package planets;

import javax.media.opengl.GL;

public class someMaterials {

    static void SetMaterialGoldenSun(GL gl)
    {
        float[] amb ={ 0.329412f, 0.223529f, 0.027451f,1.0f };
        float[] diff ={ 0.780392f, 0.568627f, 0.113725f, 1.0f };
        float[] spec ={ 0.992157f, 0.941176f, 0.807843f, 1.0f };
        float shine = 27.8974f;
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL.GL_FRONT,GL.GL_SHININESS,shine*128.0f);
    }

    static void setMaterialSilverMoon(GL gl)
    {
        float[] amb ={ 0.2295f, 0.08825f, 0.0275f, 1.0f };
        float[] diff ={0.5508f, 0.2118f, 0.066f, 1.0f };
        float[] spec ={0.580594f, 0.223257f, 0.0695701f, 1.0f };
        float shine =51.2f ;
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL.GL_FRONT,GL.GL_SHININESS,shine*128.0f);
    }

    static void setMaterialBluePlanet(GL gl)
    {
        float amb[]={0.0f, 0.0f, 1.0f,2.0f};
        float diff[]={0.0f, 1.0f, 3.0f,5.0f};
        float spec[]={0.45f, 0.55f, 0.45f, 1.0f};
        float shine=0.25f;
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL.GL_FRONT,GL.GL_SHININESS,shine*128.0f);
    }

    static void setMaterialObsidianPlanet(GL gl)
    {
        float amb[]={0.05375f, 0.05f, 0.06625f, 1.0f};
        float diff[]={0.18275f, 0.17f, 0.22525f,1.0f};
        float spec[]={0.332741f, 0.328634f, 0.346435f, 1.0f};
        float shine=0.3f;
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL.GL_FRONT,GL.GL_SHININESS,shine*128.0f);
    }

    static void setMaterialWhiteMoon(GL gl)
    {
        float amb[]={0.3f, 0.2f, 0.2f,  1.0f};
        float diff[]={1.0f, 0.9f, 0.8f,1.0f};
        float spec[]={0.4f, 0.2f, 0.2f,  1.0f};
        float shine=0.35f;
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_AMBIENT,amb,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_DIFFUSE,diff,0);
        gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR,spec,0);
        gl.glMaterialf(GL.GL_FRONT,GL.GL_SHININESS,shine*128.0f);
    }

}