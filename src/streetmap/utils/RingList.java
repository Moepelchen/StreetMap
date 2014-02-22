/*
 * Copyright (C) Ulrich Tewes GmbH 2010-2014.
 */

package streetmap.utils;

import java.util.ArrayList;

/**
 * Short description in a complete sentence.
 * <p/>
 * Purpose of this class / interface
 * Say how it should be and should'nt be used
 * <p/>
 * Known bugs: None
 * Historical remarks: None
 *
 * @author Ulrich Tewes
 * @version 1.0
 * @since Release
 */
public class RingList<E> extends ArrayList<E>
{

// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------

	@Override
	public E get(int index)
	{
		if(index<0)
		{
			index = (this.size()-(index*(-1) % this.size()))%this.size();
		}
		else
		{
			index = index % this.size();
		}
		return super.get(index);
	}

} //RingList
