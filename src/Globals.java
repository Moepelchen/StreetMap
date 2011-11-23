/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 11/22/11
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */


public class Globals {
	private Configuration fConfig;

	private StreetFactory fStreetFactory;


	public Globals(){
		fConfig = new Configuration();

		fStreetFactory = new StreetFactory();

	}

	public Configuration getConfig() {
		return fConfig;
	}


	public StreetFactory getStreetFactory() {
		return fStreetFactory;
	}


}
