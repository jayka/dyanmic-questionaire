package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class LocalServer {

	public static void main(String[] args) throws Exception {
		ClassLoader cl1 = Thread.currentThread().getContextClassLoader();
//		cl1.getResource("META-INF/c.tld");
		Server server = new Server(8080);
		WebAppContext root = new WebAppContext("src/main/webapp/WEB-INF/web.xml","/");
		root.setResourceBase("src/main/webapp");
		// jetty will resolve all libs from classpath
		root.setClassLoader(Thread.currentThread().getContextClassLoader());
		server.setHandler(root);
		server.start();
		server.join();
	}
}
