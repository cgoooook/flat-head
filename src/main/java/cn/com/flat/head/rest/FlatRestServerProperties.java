package cn.com.flat.head.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.InetAddress;

@ConfigurationProperties(
        prefix = "flat.rest.server"
)
public class FlatRestServerProperties {

    private int port = 8341;

    private InetAddress address;

    private int ioWorkerCount = Runtime.getRuntime().availableProcessors() * 4;

    private int executorThreadCount = 16;

    private String serverConfig;

    private String serverKeyPath;

    private String clientCertPath;

    private String keyStorePassword;


    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
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

    public String getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(String serverConfig) {
        this.serverConfig = serverConfig;
    }

    public String getServerKeyPath() {
        return serverKeyPath;
    }

    public void setServerKeyPath(String serverKeyPath) {
        this.serverKeyPath = serverKeyPath;
    }

    public String getClientCertPath() {
        return clientCertPath;
    }

    public void setClientCertPath(String clientCertPath) {
        this.clientCertPath = clientCertPath;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }
}
