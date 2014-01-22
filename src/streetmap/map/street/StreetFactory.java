package streetmap.map.street;

import streetmap.SSGlobals;
import streetmap.config.StreetConfig;
import streetmap.map.side.HorizontalSide;
import streetmap.map.side.Side;
import streetmap.map.tile.Tile;
import streetmap.xml.jaxb.LaneTemplate;
import streetmap.xml.jaxb.LaneTemplates;
import streetmap.xml.jaxb.StreetTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
     * @param tile       tile to generate a street for
     * @param streetName Name of the StreetTemplate
     * @return
     */
    public Street createStreet(Tile tile, String streetName)
    {
        return createStreet(tile, streetName, false);
    }

    /**
     * Creates a Street with the given Name for the given Tile
     *
     * @param tile              tile to generate a street for
     * @param streetName        Name of the StreetTemplate
     * @param chooseIntelligent
     * @return
     */
    public Street createStreet(Tile tile, String streetName, boolean chooseIntelligent)
    {

        if (tile.getStreet() != null)
        {
            for (Lane lane : tile.getStreet().getLanes())
            {
                tile.getMap().removeStart(lane);
                tile.getMap().removeEnd(lane);
                lane.getStart().removeOutputLane(lane);
                lane.getEnd().removeInputLane(lane);
            }

        }
        StreetTemplate template = fStreetConfig.getTemplate(streetName);
        if (chooseIntelligent && !template.isIsSpecial())
        {
            template = getStreetTemplate(tile, streetName);
        }
        Street street = null;
        if (template != null)
        {
            street = new Street(fGlobals, tile, template.getName(), false);
            generateLanes(street, template.getLaneTemplates(), tile);
            tile.setStreet(street);

        }
        return street;

    }


    private StreetTemplate getStreetTemplate(Tile tile, String streetName)
    {
        Collection<StreetTemplate> templateList = fStreetConfig.getTemplates();
        boolean hasNorthConnection = hasConnections(tile.getNorthSide());
        boolean hasEastConnection = hasConnections(tile.getEastSide());
        boolean hasSouthConnection = hasConnections(tile.getSouthSide());
        boolean hasWestConnection = hasConnections(tile.getWestSide());

        List<StreetTemplate> candidates = new ArrayList<>();
        List<StreetTemplate> bendCandidates = new ArrayList<>();

        if (hasNorthConnection || hasEastConnection || hasSouthConnection || hasWestConnection)
        {
            for (StreetTemplate streetTemplate : templateList)
            {
                boolean tempHasEastConnection = false;


                for (LaneTemplate template : streetTemplate.getLaneTemplates().getLaneTemplate())
                {
                    if (template.getTo().equals("E") || template.getFrom().equals("E"))
                    {
                        tempHasEastConnection = true;
                    }
                }

                boolean tempHasSouthConnection = false;


                for (LaneTemplate template : streetTemplate.getLaneTemplates().getLaneTemplate())
                {
                    if (template.getTo().equals("S") || template.getFrom().equals("S"))
                    {
                        tempHasSouthConnection = true;
                    }
                }

                boolean tempHasWestConnection = false;


                for (LaneTemplate template : streetTemplate.getLaneTemplates().getLaneTemplate())
                {
                    if (template.getTo().equals("W") || template.getFrom().equals("W"))
                    {
                        tempHasWestConnection = true;
                    }
                }

                boolean tempHasNorthConnection = false;


                for (LaneTemplate template : streetTemplate.getLaneTemplates().getLaneTemplate())
                {
                    if (template.getTo().equals("N") || template.getFrom().equals("N"))
                    {
                        tempHasNorthConnection = true;
                    }
                }


                boolean doesEastFit = doesFit(hasEastConnection, tempHasEastConnection);
                boolean doesSouthFit = doesFit(hasSouthConnection, tempHasSouthConnection);
                boolean doesWestFit = doesFit(hasWestConnection, tempHasWestConnection);
                boolean doesNorthFit = doesFit(hasNorthConnection, tempHasNorthConnection);
                if (doesEastFit && doesSouthFit && doesWestFit && doesNorthFit)
                {
                    if (hasOnlyBends(streetTemplate))
                    {
                        bendCandidates.add(streetTemplate);
                    } else
                    {
                        candidates.add(streetTemplate);
                    }
                }
            }

        }
        StreetTemplate bestMatch = null;
        int minCon = 100;
        for (StreetTemplate candidate : candidates)
        {
            int size = candidate.getLaneTemplates().getLaneTemplate().size();
            if (size < minCon && size >= 2 && !candidate.isIsSpecial())
            {
                minCon = size;
                bestMatch = candidate;
            }

        }
        if (bestMatch == null)
        {
            for (StreetTemplate bendCandidate : bendCandidates)
            {
                int size = bendCandidate.getLaneTemplates().getLaneTemplate().size();
                if (size < minCon && size >= 2 && !bendCandidate.isIsSpecial())
                {
                    minCon = size;
                    bestMatch = bendCandidate;
                }
            }
        }

        if (bestMatch != null)
        {
            return bestMatch;
        }

        return fStreetConfig.getTemplate(streetName);
    }

    private boolean hasOnlyBends(StreetTemplate streetTemplate)
    {
        for (LaneTemplate laneTemplate : streetTemplate.getLaneTemplates().getLaneTemplate())
        {
            if (!(getLaneType(laneTemplate.getFrom(), laneTemplate.getTo()) == ILaneTypes.BEND))
            {
                return false;
            }
        }
        return true;
    }

    private boolean hasStartOrEnd(StreetTemplate candidate)
    {
        for (LaneTemplate laneTemplate : candidate.getLaneTemplates().getLaneTemplate())
        {
            if (laneTemplate.isIsEndPoint() || laneTemplate.isIsStartPoint())
                return true;
        }
        return false;
    }


    private boolean doesFit(boolean hasEastConnection, boolean tempHasEastConnection)
    {
        boolean toReturn = false;
        if (hasEastConnection && tempHasEastConnection)
        {
            toReturn = true;
        } else if (!hasEastConnection && !tempHasEastConnection)
        {
            return true;
        }

        return toReturn;
    }

    private boolean hasConnections(Side northSide)
    {
        return northSide.getAnchorOne().getInputLanes().size() > 0 || northSide.getAnchorTwo().getOutputLanes().size() > 0 || northSide.getAnchorOne().getOutputLanes().size() > 0 || northSide.getAnchorTwo().getInputLanes().size() > 0;
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
            lane = new Lane(fGlobals, street);
            String from = laneTemplate.getFrom();
            String to = laneTemplate.getTo();

            Side start = tile.getSide(from);
            Side dest = tile.getSide(to);

            lane.setType(getLaneType(from, to));
            lane.setIsEndLane(laneTemplate.isIsEndPoint());
            lane.setIsStartLane(laneTemplate.isIsStartPoint());
            lane.setTo(to);
            lane.setFrom(from);
            if (lane.getType() == ILaneTypes.STRAIGHT)
            {
                createStraight(lane, start, dest, from, to);
            }
            if (lane.getType() == ILaneTypes.BEND)
            {
                createBend(lane, start, dest, from, to);
            }

            // add this lane as a starting point to the compassPoint "to" from the starting anchor of the lane
            lane.getStart().addOutputLane(lane);
            lane.getEnd().addInputLane(lane);

            lane.init();

            street.addLane(lane);
            if (tile.getMap() != null)
            {
                if (lane.isStartLane())
                {
                    tile.getMap().addStart(lane);
                } else if (lane.isEndLane())
                {

                    tile.getMap().addEnd(lane);
                }
            }

        }
    }

    /**
     * Creates a Bend between the given sides
     *
     * @param lane  the bend
     * @param start starting side of this bend
     * @param dest  destination side for this bend
     * @param from  compass point to start from
     * @param to    compass point which the lane connects as end
     */
    private void createBend(Lane lane, Side start, Side dest, String from, String to)
    {
        if ((from.equals("E") && to.equals("N")) || (from.equals("S") && to.equals("W")))
        {
            lane.setStart(start.getAnchorTwo(), from);
            lane.setEnd(dest.getAnchorTwo(), to);
        } else if ((from.equals("N") && to.equals("E")) || (from.equals("W") && to.equals("S")))
        {
            lane.setStart(start.getAnchorOne(), from);
            lane.setEnd(dest.getAnchorOne(), to);
        } else if ((from.equals("S") && to.equals("E")) || (from.equals("E") && to.equals("S")))
        {
            lane.setStart(start.getAnchorTwo(), from);
            lane.setEnd(dest.getAnchorOne(), to);
        } else if ((from.equals("W") && to.equals("N")) || (from.equals("N") && to.equals("W")))
        {
            lane.setStart(start.getAnchorOne(), from);
            lane.setEnd(dest.getAnchorTwo(), to);
        }
    }

    /**
     * Creates a straight between the given sides
     *
     * @param lane
     * @param start
     * @param dest
     * @param from
     * @param to
     */
    private void createStraight(Lane lane, Side start, Side dest, String from, String to)
    {
        if (from.equals("S") || from.equals("E"))
        {
            lane.setStart(start.getAnchorTwo(), from);
            lane.setEnd(dest.getAnchorTwo(), to);
        } else
        {
            lane.setStart(start.getAnchorOne(), from);
            lane.setEnd(dest.getAnchorOne(), to);
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

    /**
     * determines the lane type depending on the start and end compass point
     *
     * @param from starting compass point
     * @param to   end compass point
     * @return
     */
    private int getLaneType(String from, String to)
    {
        if (sameAxis(from, to))
        {
            return ILaneTypes.STRAIGHT;
        } else
        {
            return ILaneTypes.BEND;
        }
    }

    /**
     * Determines wether the given compass points are located on the same axis
     *
     * @param from starting compass point
     * @param to   destination compass point
     * @return true for (W,E)and (N,S) else false
     */
    private boolean sameAxis(String from, String to)
    {
        return ((from.equals("E") || from.equals("W")) && (to.equals("W") || to.equals("E"))) || ((from.equals("S") || from.equals("N")) && (to.equals("S") || to.equals("N")));

    }

}