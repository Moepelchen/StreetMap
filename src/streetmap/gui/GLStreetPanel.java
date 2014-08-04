package streetmap.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import streetmap.Player;
import streetmap.SSGlobals;
import streetmap.map.street.IStreetNames;
import streetmap.map.tile.Tile;
import streetmap.utils.DrawHelper;
import streetmap.xml.jaxb.StreetTemplate;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

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
    private String fSelectedStreet;
    private Point2D fFirstClicked;
    private Point2D fCurrentClick;

    public GLStreetPanel(SSGlobals globals)
    {
        fTiles = new ArrayList<>();
        fGlobals = globals;
        List<StreetTemplate> templates = new ArrayList<>();
        templates.add(globals.getStreetConfig().getTemplate(IStreetNames.WEST_EAST));
        templates.add(globals.getStreetConfig().getTemplate(IStreetNames.SOUTH_NORTH));

        for (StreetTemplate streetTemplate : globals.getStreetConfig().getTemplates())
        {
            if (streetTemplate.isIsSpecial())
                templates.add(streetTemplate);
        }

        int numberOfTemplates = templates.size();
        fTileWidth = fGlobals.getGame().getHeight() / numberOfTemplates;
        for (int i = 0; i < templates.size(); i++)
        {
            Tile tile = new Tile(fGlobals, fGlobals.getMap(), new Point2D.Double(0, i), fTileWidth);
            tile.setStreet(fGlobals.getStreetFactory().createStreet(tile, (templates.get(i)).getName()));
            fTiles.add(tile);
        }
    }

    public void draw()
    {
        for (Tile tile : fTiles)
        {

            DrawHelper.drawStreet(tile.getStreet(), true);
            if (fSelectedStreet != null && tile.getStreet().getName().equals(fSelectedStreet))
            {
               /* GL11.glColor4d(0, 1, 0, 0.5);
                glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glBegin(GL11.GL_QUADS);
                {
                    glVertex3d(tile.getArrayPosition().getX() * fTileWidth, tile.getArrayPosition().getY() * fTileWidth, 0);

                    glVertex3d(tile.getArrayPosition().getX() * fTileWidth + fTileWidth, tile.getArrayPosition().getY() * fTileWidth, 0);

                    glVertex3d(tile.getArrayPosition().getX() * fTileWidth + fTileWidth, tile.getArrayPosition().getY() * fTileWidth + fTileWidth, 0);

                    glVertex3d(tile.getArrayPosition().getX() * fTileWidth, tile.getArrayPosition().getY() * fTileWidth + fTileWidth, 0);
                }
                GL11.glEnd();*/
            }

        }
        if (fFirstClicked != null && fCurrentClick != null && fSelectedStreet != null)
        {
            Line2D line = new Line2D.Double(fFirstClicked, fCurrentClick);
            Rectangle2D rect = getSelectionRectangle();

            Tile[][] tiles = fGlobals.getMap().getTiles();
            ArrayList<Tile> intersectionTiles = getSelectedTiles(line, rect, tiles);


            for (Tile intersectionTile : intersectionTiles)
            {
                GL11.glPushMatrix();
                Vector2f scalePoint = fGlobals.getGame().getScalePoint();
                GL11.glTranslated(scalePoint.getX(), scalePoint.getY(), 0);
                Player fPlayer = fGlobals.getGame().getPlayer();
                GL11.glScalef(fPlayer.getZoom(), fPlayer.getZoom(), 0);
                GL11.glTranslated(-scalePoint.getX(), -scalePoint.getY(), 0);

                GL11.glTranslatef(fPlayer.getX(), fPlayer.getY(), 0);
                GL11.glColor4d(0, 1, 0, 0.5);
                glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glBegin(GL11.GL_QUADS);
                {
                    glVertex3d(intersectionTile.getArrayPosition().getX() * intersectionTile.getWidth(), intersectionTile.getArrayPosition().getY() * intersectionTile.getWidth(), 0);

                    glVertex3d(intersectionTile.getArrayPosition().getX() * intersectionTile.getWidth() + intersectionTile.getWidth(), intersectionTile.getArrayPosition().getY() * intersectionTile.getWidth(), 0);

                    glVertex3d(intersectionTile.getArrayPosition().getX() * intersectionTile.getWidth() + intersectionTile.getWidth(), intersectionTile.getArrayPosition().getY() * intersectionTile.getWidth() + intersectionTile.getWidth(), 0);

                    glVertex3d(intersectionTile.getArrayPosition().getX() * intersectionTile.getWidth(), intersectionTile.getArrayPosition().getY() * intersectionTile.getWidth() + intersectionTile.getWidth(), 0);
                }
                GL11.glEnd();
                GL11.glPopMatrix();
            }
        }
    }

    private Rectangle2D getSelectionRectangle()
    {
        double xF = fFirstClicked.getX();
        double yF = fFirstClicked.getY();
        double xC = fCurrentClick.getX();
        double yC = fCurrentClick.getY();
        Rectangle2D rect;
        double width = Math.abs(xF - xC);
        double height = Math.abs(yF - yC);
        if (xF < xC)
        {
            if (yF < yC)
                rect = new Rectangle2D.Double(xF, yF, width, height);
            else
                rect = new Rectangle2D.Double(xF, yC, width, height);

        } else
        {
            if (yF < yC)
                rect = new Rectangle2D.Double(xC, yF, width, height);
            else
                rect = new Rectangle2D.Double(xC, yC, width, height);
        }
        return rect;
    }

    private ArrayList<Tile> getSelectedTiles(Line2D line, Rectangle2D rect, Tile[][] tiles)
    {
        ArrayList<Tile> intersectionTiles = new ArrayList<>();
        for (Tile[] tile : tiles)
        {
            for (Tile tile1 : tile)
            {
                if (fSelectedStreet != null && fSelectedStreet.equals(IStreetNames.DELETE_STREET) )
                {
                    if (rect.intersects(tile1.getRect()))
                    {
                        intersectionTiles.add(tile1);
                    }
                } else if (fSelectedStreet != null)
                {
                    if (line.intersects(tile1.getRect()))
                    {
                        intersectionTiles.add(tile1);
                    }
                }
            }
        }

        return intersectionTiles;
    }

    public void handleClick()
    {
        if (Mouse.isButtonDown(0))
        {
            int x = Mouse.getX();
            int y = Mouse.getY();
            fSelectedStreet = getSelected(x, y);

            if (x < fTileWidth)
            {
                handlePanelClick(y);
            } else
            {

                y = fGlobals.getGame().getHeight() - y;
                Vector2f pos = fGlobals.getGame().getTranslatedCoords(x, y);

                float zoom = fGlobals.getGame().getPlayer().getZoom();
                if (fFirstClicked == null)
                {
                    fFirstClicked = new Point2D.Double(pos.getX() / zoom, pos.getY() / zoom);
                }
                fCurrentClick = new Point2D.Double(pos.getX() / zoom, pos.getY() / zoom);
            }
        } else
        {
            if (fFirstClicked != null && fCurrentClick != null && fSelectedStreet != null)
            {
                Line2D line = new Line2D.Double(fFirstClicked, fCurrentClick);
                Tile[][] tiles = fGlobals.getMap().getTiles();
                Rectangle2D rect = getSelectionRectangle();
                ArrayList<Tile> intersectionTiles = getSelectedTiles(line, rect, tiles);

                for (Tile intersectionTile : intersectionTiles)
                {
                    fGlobals.getMap().handleAddition(intersectionTile.getStreet());
                    fGlobals.getStreetFactory().createStreet(intersectionTile, fSelectedStreet, true, true);
                }

            }
            fFirstClicked = null;
            fCurrentClick = null;
        }

    }

    private String getSelected(int x, int y)
    {

        if (fSelectedStreet != null && !fGlobals.getStreetConfig().getTemplate(fSelectedStreet).isIsSpecial() && fCurrentClick != null)
        {
            double distY = Math.abs(fCurrentClick.getX() - x);
            double distX = Math.abs(fCurrentClick.getY() - y);
            if (distY > 0 || distX > 0)
            {
                if (distY > distX)
                    fSelectedStreet = IStreetNames.WEST_EAST;
                else
                    fSelectedStreet = IStreetNames.SOUTH_NORTH;
            }
        }

        return fSelectedStreet;
    }

    private void handlePanelClick(int y)
    {
        int index = fTiles.size() - 1 - (y / fTileWidth);
        fSelectedStreet = fTiles.get(index).getStreet().getName();
    }
}
