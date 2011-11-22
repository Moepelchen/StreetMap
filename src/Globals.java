/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 11/22/11
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */


public class Globals {
	private Configuration fConfig;

	public Globals(){
		fConfig = new Configuration();
	}

	public Configuration getfConfig() {
		return fConfig;
	}

	public void setfConfig(Configuration fConfig) {
		this.fConfig = fConfig;
	}
}
