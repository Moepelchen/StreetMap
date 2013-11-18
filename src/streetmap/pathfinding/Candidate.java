package streetmap.pathfinding;

import streetmap.map.street.Lane;

/**
 * Created with IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 01.11.13
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class Candidate implements Comparable
{
    public Lane candidate;
    public double fDistanceToGoal = 0;
	private Candidate fPrevious;
	public double fDistanceToStart = 0;

	@Override
    public int compareTo(Object o)
    {
        Candidate candidate = (Candidate) o;
        if((this.fDistanceToGoal + fDistanceToStart) < (candidate.fDistanceToGoal+ candidate.fDistanceToStart))
        {
            return -1;

        }else if ((this.fDistanceToGoal + fDistanceToStart) > candidate.fDistanceToGoal+ candidate.fDistanceToStart)
        {
            return  +1;
        }
        else if ((this.fDistanceToGoal + fDistanceToStart) == candidate.fDistanceToGoal+ candidate.fDistanceToStart);
        {
            return 0;
        }

    }

	public Candidate previous()
	{
		return fPrevious;
	}

	public void setPrevious(Candidate previous)
	{
		fPrevious = previous;
	}

	public void setDistanceToStart(double distanceToStart)
	{
		fDistanceToStart = distanceToStart;
	}

	public Candidate(Lane start)
	{
		candidate = start;
	}

	@Override
	public int hashCode()
	{
		return candidate.hashCode();
	}
}
