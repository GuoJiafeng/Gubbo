package io.gjf.commons;

/**
 * Create by GuoJF on 2019/4/15
 */
public class HostAndPort {

    private String host;
    private Integer port;

    public HostAndPort(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public HostAndPort() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
