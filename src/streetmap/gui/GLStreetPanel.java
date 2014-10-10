package streetmap.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import streetmap.SSGlobals;
import streetmap.interfaces.IPrintable;
import streetmap.map.street.IStreetNames;
import streetmap.map.tile.Tile;
import streetmap.utils.DrawHelper;
import streetmap.utils.PrintableRenderBuffer;
import streetmap.utils.RenderStuff;
import streetmap.utils.TextureCache;
import streetmap.xml.jaxb.streets.StreetTemplate;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
			{
				templates.add(streetTemplate);
			}
		}

		int numberOfTemplates = templates.size();
		fTileWidth = fGlobals.getGame().getHeight() / numberOfTemplates;
		for (int i = 0; i < templates.size(); i++)
		{
			Tile tile = new Tile(fGlobals, fGlobals.getMap(), new Point2D.Double(0, i), fTileWidth);
			tile.setPlaceable(fGlobals.getStreetFactory().createStreet(tile, (templates.get(i)).getName()));
			fTiles.add(tile);
		}
		addTilesToNifty();
	}

	private void addTilesToNifty()
	{
		Nifty nifty = fGlobals.getNifty();
		if (nifty != null)
		{
			Element placeables;
			Screen currentScreen = nifty.getCurrentScreen();
			if (currentScreen != null)
			{
				placeables = currentScreen.findElementById("Placeables");
                placeables.setWidth(fTileWidth);
				if (placeables != null)
				{
					for (Tile tile : fTiles)
					{
						String imagePath = tile.getPlaceable().getMenuImagePath();
						final File image = new File(imagePath);

						Element panel = new PanelBuilder()
						{{
								childLayout(ChildLayoutType.Center);
								image(new ImageBuilder()
								{{
										filename(image.getAbsolutePath());
										width(String.valueOf(fTileWidth)+"px");
										height(String.valueOf(fTileWidth)+"px");
										onClickEffect(new EffectBuilder("border")
										{{
												effectParameter("timeType ", "infinite");
												effectParameter("length", "infinite");
												effectParameter("width", "50px");
												effectParameter("color", "#00EF13");

											}});
										visibleToMouse(true);
									}});
							}}.build(nifty, currentScreen, placeables);
						System.out.println(panel.getChildren().size());

					}
				}
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
			{
				rect = new Rectangle2D.Double(xF, yF, width, height);
			}
			else
			{
				rect = new Rectangle2D.Double(xF, yC, width, height);
			}

		}
		else
		{
			if (yF < yC)
			{
				rect = new Rectangle2D.Double(xC, yF, width, height);
			}
			else
			{
				rect = new Rectangle2D.Double(xC, yC, width, height);
			}
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
				if (fSelectedStreet != null && fSelectedStreet.equals(IStreetNames.DELETE_STREET))
				{
					if (rect.intersects(tile1.getRect()))
					{
						intersectionTiles.add(tile1);
					}
				}
				else if (fSelectedStreet != null)
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
			}
			else
			{

				//y = fGlobals.getGame().getHeight()-y;
				Vector2f pos = fGlobals.getGame().getTranslatedCoords(x, y);

				if (fFirstClicked == null)
				{
					fFirstClicked = new Point2D.Double(pos.getX(), pos.getY());
				}
				fCurrentClick = new Point2D.Double(pos.getX(), pos.getY());
			}
		}
		else
		{
			if (fFirstClicked != null && fCurrentClick != null && fSelectedStreet != null)
			{
				Line2D line = new Line2D.Double(fFirstClicked, fCurrentClick);
				Tile[][] tiles = fGlobals.getMap().getTiles();
				Rectangle2D rect = getSelectionRectangle();
				ArrayList<Tile> intersectionTiles = getSelectedTiles(line, rect, tiles);

				for (Tile intersectionTile : intersectionTiles)
				{
					fGlobals.getMap().handleAddition(intersectionTile.getPlaceable());
					fGlobals.getStreetFactory().createStreet(intersectionTile, fSelectedStreet, true, true);
				}

			}
			fFirstClicked = null;
			fCurrentClick = null;
		}

	}

	public void draw()
   {

       if (fFirstClicked != null && fCurrentClick != null && fSelectedStreet != null)
       {
           Line2D line = new Line2D.Double(fFirstClicked, fCurrentClick);
           Rectangle2D rect = getSelectionRectangle();

           Tile[][] tiles = fGlobals.getMap().getTiles();
           ArrayList<Tile> intersectionTiles = getSelectedTiles(line, rect, tiles);

	       List<IPrintable> list = new ArrayList<>();
	       for (Tile intersectionTile : intersectionTiles)
	       {
				list.add(intersectionTile);
	       }
	       RenderStuff stuff = PrintableRenderBuffer.initBuffers(fGlobals,list);

	       if(stuff != null)
	       {
		       DrawHelper.drawCars(stuff, TextureCache.getTextureId("./images/streets/bulldozer.png"));
		       stuff.release();
	       }
       }
   }


	private String getSelected(int x, int y)
	{

		if (fSelectedStreet != null && !fGlobals.getStreetConfig().getTemplate(fSelectedStreet).isIsSpecial() && fCurrentClick != null)
		{
			double distY = Math.abs(fCurrentClick.getX() / fGlobals.getGame().getWidth());
			double distX = Math.abs(fCurrentClick.getY() / fGlobals.getGame().getHeight());
			if (distY > 0 || distX > 0)
			{
				if (distY > distX)
				{
					fSelectedStreet = IStreetNames.WEST_EAST;
				}
				else
				{
					fSelectedStreet = IStreetNames.SOUTH_NORTH;
				}
			}
		}

		return fSelectedStreet;
	}

	private void handlePanelClick(int y)
	{
		int index = fTiles.size() - 1 - (y / fTileWidth);
		fSelectedStreet = fTiles.get(index).getPlaceable().getName();
		System.out.println("fSelectedStreet = " + fSelectedStreet);

	}
}
