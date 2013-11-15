package streetmap.interfaces;

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
    public double distance = 0;
	private Candidate fPrevious;
	private double fDistanceToGoal;

	@Override
    public int compareTo(Object o)
    {
        Candidate candidate = (Candidate) o;
        if(this.distance < candidate.distance)
        {
            return -1;

        }else if (this.distance > candidate.distance)
        {
            return  +1;
        }
        else if (this.distance == candidate.distance);
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

	public void setDistanceToGoal(double distanceToGoal)
	{
		fDistanceToGoal = distanceToGoal;
	}

	public Candidate(Lane start)
	{
		candidate = start;
	}
}
