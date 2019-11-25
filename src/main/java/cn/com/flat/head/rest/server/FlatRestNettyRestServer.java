package cn.com.flat.head.rest.server;

import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.util.Collection;

public class FlatRestNettyRestServer implements RestServer {

    private Logger logger = LoggerFactory.getLogger(FlatRestNettyRestServer.class);

    private String rootResourcePath = "/";
    private String hostName;
    private int port = 8341;
    private boolean clientAuth = false;
    private int ioWorkerCount = Runtime.getRuntime().availableProcessors() * 2;
    private int executorThreadCount = 16;
    private SSLContext sslContext = null;
    private final int maxRequestSize = 10485760;
    private NettyJaxrsServer nettyServer;
    private ResteasyDeployment deployment = new ResteasyDeployment();

    public FlatRestNettyRestServer() {
    }

    public void addResources(Collection<Object> instances) {
        if (instances != null && !instances.isEmpty()) {
            this.deployment.getResources().addAll(instances);
        }
    }

    public void addProviders(Collection<Object> instances) {
        if (instances != null && !instances.isEmpty()) {
            this.deployment.getProviders().addAll(instances);
        }
    }

    @Override
    public void start() {
        this.nettyServer = new NettyJaxrsServer();
        this.nettyServer.setDeployment(this.deployment);
        this.nettyServer.setPort(this.port);
        this.nettyServer.setRootResourcePath(this.rootResourcePath);
        this.nettyServer.setIoWorkerCount(this.ioWorkerCount);
        this.nettyServer.setExecutorThreadCount(this.executorThreadCount);
        this.nettyServer.setMaxRequestSize(maxRequestSize);
        this.nettyServer.setSSLContext(this.sslContext);
        if (this.hostName != null && !"".equals(this.hostName.trim())) {
            this.nettyServer.setHostname(this.hostName);
        }

        this.nettyServer.setSecurityDomain(null);
        this.nettyServer.start();
        this.logger.info("flat rest server started on port(s): " + this.port);
    }

    @Override
    public void stop() {
        this.nettyServer.stop();
    }


    public String getRootResourcePath() {
        return this.rootResourcePath;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public int getExecutorThreadCount() {
        return this.executorThreadCount;
    }

    public void setExecutorThreadCount(int executorThreadCount) {
        this.executorThreadCount = executorThreadCount;
    }

    public int getIoWorkerCount() {
        return this.ioWorkerCount;
    }

    public void setIoWorkerCount(int ioWorkerCount) {
        this.ioWorkerCount = ioWorkerCount;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public void setClientAuth(boolean clientAuth) {
        this.clientAuth = clientAuth;
    }
}
