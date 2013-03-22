package streetmap.Interfaces2;

import streetmap.map.street.Lane;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 16.01.12
 * Time: 22:43
 * To change this template use File | Settings | File Templates.
 */
public interface IPathFindingAlgorithm
{

	public LinkedList getPath(Lane start, Lane end);
}
