package streetmap;

import streetmap.Interfaces.ILaneTypes;
import streetmap.config.StreetConfig;
import streetmap.xml.jaxb.LaneTemplate;
import streetmap.xml.jaxb.LaneTemplates;
import streetmap.xml.jaxb.StreetTemplate;

public class StreetFactory
{
	/**
	 * the Globals
	 */
	private SSGlobals fGlobals;

	/**
	 * the StreetConfiguration
	 */
	private StreetConfig fStreetConfig;

	/**
	 * Creates a StreetFactory
	 *
	 * @param glob the Globals
	 */

	public StreetFactory(SSGlobals glob)
	{
		fGlobals = glob;
		fStreetConfig = glob.getStreetConfig();

	}

	/**
	 * Creates a Street with the given Name for the given Tile
	 *
	 * @param tile
	 * @param streetName Name of the StreetTemplate
	 * @return
	 */
	public Street createStreet(Tile tile, String streetName)
	{
		StreetTemplate template = fStreetConfig.getTemplate(streetName);

		Street street = null;
		if (template != null)
		{
			street = new Street(streetName);
			generateLanes(street, template.getLaneTemplates(), tile);
			tile.setStreet(street);
		}
		return street;

	}

	/**
	 * This method generates the Lanes for a given template and a given Tile
	 *
	 * @param street        Street to generate the lanes for
	 * @param laneTemplates Configuration of all lanes
	 * @param tile          tile to generate the street for
	 */
	private void generateLanes(Street street, LaneTemplates laneTemplates, Tile tile)
	{
		Lane lane = null;
		for (LaneTemplate laneTemplate : laneTemplates.getLaneTemplate())
		{
			lane = new Lane();
			String from = laneTemplate.getFrom();
			String to = laneTemplate.getTo();


			Side start = tile.getSide(from);
			Side dest = tile.getSide(to);

			lane.setType(getLaneType(from, to));

			if (lane.getType() == ILaneTypes.STRAIGHT)
			{
				createStraight(lane, start, dest, from, to);
			}
			if (lane.getType() == ILaneTypes.BEND)
			{
				createBend(lane, start, dest, from, to);
			}

			// add this lane as a starting point to the compassPoint "to" from the starting anchor of the lane
			lane.getStart().addLane(to, lane);

			street.addLane(lane);

		}
	}

	private void createBend(Lane lane, Side start, Side dest, String from, String to)
	{
		if ((from.equals("E") && to.equals("N")) || (from.equals("S") && to.equals("W")))
		{
			lane.setStart(start.getAnchorTwo());
			lane.setEnd(dest.getAnchorTwo());
		} else if ((from.equals("N") && to.equals("E")) || (from.equals("W") && to.equals("S")))
		{
			lane.setStart(start.getAnchorOne());
			lane.setEnd(dest.getAnchorOne());
		} else if ((from.equals("S") && to.equals("E")) || (from.equals("E") && to.equals("S")))
		{
			lane.setStart(start.getAnchorTwo());
			lane.setEnd(dest.getAnchorOne());
		} else if ((from.equals("W") && to.equals("N")) || (from.equals("N") && to.equals("W")))
		{
			lane.setStart(start.getAnchorOne());
			lane.setEnd(dest.getAnchorTwo());
		}
	}

	private void createStraight(Lane lane, Side start, Side dest, String from, String to)
	{
		if (from.equals("S") || from.equals("E"))
		{
			lane.setStart(start.getAnchorTwo());
			lane.setEnd(dest.getAnchorTwo());
		} else
		{
			lane.setStart(start.getAnchorOne());
			lane.setEnd(dest.getAnchorOne());
		}
	}

	private String getDirection(String from, String to)
	{
		return null;
	}

	private ILaneTypes determineType(Tile tile)
	{
		return null;
	}

	public void connectAnchors(Street street, ILaneTypes type)
	{
	}


	private int getLaneType(String from, String to)
	{
		if (sameAxis(from, to))
		{
			return ILaneTypes.STRAIGHT;
		} else
			return ILaneTypes.BEND;
	}

	private boolean sameAxis(String from, String to)
	{
		if (((from.equals("E") || from.equals("W")) && (to.equals("W") || to.equals("E"))) || ((from.equals("S") || from.equals("N")) && (to.equals("S") || to.equals("N"))))
		{
			return true;
		}

		return false;
	}

}