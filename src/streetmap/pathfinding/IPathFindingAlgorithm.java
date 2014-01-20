package streetmap.pathfinding;

import streetmap.map.street.Lane;
import streetmap.map.street.Street;

/**
 * Created by IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 16.01.12
 * Time: 22:43
 * To change this template use File | Settings | File Templates.
 */
public interface IPathFindingAlgorithm
{
    Lane getNextLane();

    Lane getDestination();

	boolean containsStreet(Street street);
}
