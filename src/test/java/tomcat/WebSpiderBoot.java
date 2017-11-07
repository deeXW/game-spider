package tomcat;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebSpiderBoot {
    private final Log     log           = LogFactory.getLog(getClass());
    private static String CONTEXT_PATH  = "/webspider";
    private static String PROJECT_PATH  = System.getProperty("user.dir");
    private static String WEB_APP_PATH  = PROJECT_PATH + "/src/main/webapp";
    private static String CATALINA_HOME = PROJECT_PATH + "/src/test/java/tomcat/";
    private final Tomcat        tomcat        = new Tomcat();
    private int           port;

    public WebSpiderBoot(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        tomcat.setPort(port);
        tomcat.setBaseDir(CATALINA_HOME);
        tomcat.getHost().setAppBase(WEB_APP_PATH);
        try {
            StandardServer server = (StandardServer) tomcat.getServer();
            AprLifecycleListener listener = new AprLifecycleListener();
            server.addLifecycleListener(listener);
            tomcat.addWebapp(CONTEXT_PATH, WEB_APP_PATH);
        } catch (ServletException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw e;
        }
        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw e;
        }
        log.info("Tomcat started.");
    }

    public void stop() throws Exception {
        try {
            tomcat.stop();
        } catch (LifecycleException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            throw ex;
        }
        log.info("Tomcat stoped");
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public static void main(String[] args) throws Exception {
        WebSpiderBoot embededTomcat = new WebSpiderBoot(8090);
        embededTomcat.start();
    }
}
