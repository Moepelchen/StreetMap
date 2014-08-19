package streetmap.utils;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.MissingResourceException;

/**
 * Copyright Ulrich Tewes
 * Created by ulrichtewes on 02.03.14.
 */
public class TextureCache
{
	private static final HashMap<String, Integer> gImageStore = new HashMap<>();

	public static int getTextureId(String path) throws MissingResourceException
	{

		Integer texId = 0;
		if (path != null)
		{
			texId = gImageStore.get(path);
			if (texId == null)
			{
				ByteBuffer buf = null;
				int tWidth = 0;
				int tHeight = 0;

				try
				{
					// Open the PNG file as an InputStream
					InputStream in = new FileInputStream(path);
					// Link the PNG decoder to this stream
					texId = TextureLoader.getTexture("RGBA", in,true).getTextureID();
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.exit(-1);
				}



				gImageStore.put(path, texId);
			}
		}
		if (texId == null || texId == 0)
		{
			throw new MissingResourceException("Texture with the path '" + path + "' could not be found", TextureCache.class.toString(), path);
		}

		return texId;
	}

	public static void releaseTextures()
	{
		for (String s : gImageStore.keySet())
		{
			GL11.glDeleteTextures(gImageStore.get(s));
		}
	}
}
