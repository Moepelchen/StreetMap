package streetmap.utils;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.MissingResourceException;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

/**
 * Copyright Ulrich Tewes
 * Created by ulrichtewes on 02.03.14.
 */
public class TextureCache
{
    private static final HashMap<String, Texture> gImageStore = new HashMap<>();


    public static int getTextureId(String path) throws MissingResourceException
    {
        Texture texture = null;
        if (path != null)
        {
            texture = gImageStore.get(path);
            if (texture == null)
            {
                try
                {
                    texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path), GL_NEAREST);
                } catch (IOException e)
                {
                    throw new MissingResourceException("Texture with the path '" + path+"' could not be found", TextureCache.class.toString(),path);
                }

                gImageStore.put(path, texture);
            }
        }
        if (texture == null)
        {
            throw new MissingResourceException("Texture with the path '" + path+"' could not be found", TextureCache.class.toString(),path);
        }

        return texture.getTextureID();
    }

    public static void releaseTextures()
    {
        for (String s : gImageStore.keySet())
        {
            gImageStore.get(s).release();
        }
    }
}
