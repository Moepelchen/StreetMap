package streetmap.Handlers.Filter;

import streetmap.Utils.FileUtils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/5/12
 * Time: 9:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class TextFileFilter extends FileFilter
{
	@Override
	public boolean accept(File f)
	{
		if (f.isDirectory())
		{
			return true;
		}

		String extension = FileUtils.getExtension(f);
		if (extension != null)
		{
			if (extension.equals(FileUtils.xml))
			{
				return true;
			} else
			{
				return false;
			}
		}

		return false;
	}

	@Override
	public String getDescription()
	{
		return "Text files only";  //To change body of implemented methods use File | Settings | File Templates.
	}
}
