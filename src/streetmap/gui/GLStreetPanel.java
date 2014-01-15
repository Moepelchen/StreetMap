package streetmap.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import streetmap.SSGlobals;
import streetmap.map.tile.Tile;
import streetmap.xml.jaxb.StreetTemplate;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glVertex3d;

/**
 * Created by ulrichtewes on 07.12.13.
 */
public class GLStreetPanel
{
    private final SSGlobals fGlobals;
    private final ArrayList<Tile> fTiles;
    private final int fTileWidth;
    private Tile fSelectedTile;

    public GLStreetPanel(SSGlobals globals)
    {
        fTiles = new ArrayList<Tile>();
        fGlobals = globals;
        Object[] templates = fGlobals.getStreetConfig().getTemplates().toArray();
        int numberOfTemplates = templates.length;
        fTileWidth =fGlobals.getGame().getHeight()/numberOfTemplates;
        for (int i = 0; i < templates.length; i++)
        {
            Tile tile = new Tile(fGlobals,fGlobals.getMap(),new Point2D.Double(0,i),fTileWidth);
            tile.setStreet(fGlobals.getStreetFactory().createStreet(tile,((StreetTemplate)templates[i]).getName()));
            fTiles.add(tile);
        }
    }

    public void draw()
    {
        for (Tile tile : fTiles)
        {
            tile.print(null);
            if(fSelectedTile != null && tile.equals(fSelectedTile))
            {
                GL11.glColor4d(0,1,0,0.5);
                glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glBegin(GL11.GL_QUADS);
                {
                    glVertex3d(tile.getArrayPosition().getX() * fTileWidth, tile.getArrayPosition().getY() * fTileWidth, 0);

                    glVertex3d(tile.getArrayPosition().getX() * fTileWidth + fTileWidth, tile.getArrayPosition().getY() * fTileWidth, 0);

                    glVertex3d(tile.getArrayPosition().getX() * fTileWidth + fTileWidth, tile.getArrayPosition().getY() * fTileWidth + fTileWidth, 0);

                    glVertex3d(tile.getArrayPosition().getX() * fTileWidth, tile.getArrayPosition().getY() * fTileWidth + fTileWidth, 0);
                }
                GL11.glEnd();
            }

        }
    }

    public void handleClick()
    {
        int x = Mouse.getX();
        int y = Mouse.getY();

        if(x < fTileWidth)
        {
            handlePanelClick(x, y);
        }
        else
        {
            handleMapClick(x,y);
        }
    }

    private void handleMapClick(int x, int y)
    {
        y = (int) (fGlobals.getGame().getHeight()-y);
        Vector2f pos = fGlobals.getGame().getTranslatedCoords(x,y);
        double tileSize = fGlobals.getMap().getTileWidth()*fGlobals.getGame().getPlayer().getZoom();
        x = (int) (pos.getX() /tileSize);
        y = (int) (pos.getY() /tileSize);
        Tile tile  =fGlobals.getMap().getTile(x,y);
	    if (tile != null && fSelectedTile != null)
	    {
		    fGlobals.getMap().handleAddition(tile.getStreet());
		    fGlobals.getStreetFactory().createStreet(tile, fSelectedTile.getStreet().getName());
	    }
    }

    private void handlePanelClick(int x, int y)
    {
        int index =fTiles.size()-1- (y/fTileWidth);
        fSelectedTile = fTiles.get(index);
    }
}
