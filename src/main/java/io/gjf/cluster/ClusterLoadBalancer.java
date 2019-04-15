package io.gjf.cluster;

import io.gjf.commons.HostAndPort;

import java.util.List;

/**
 * Create by GuoJF on 2019/4/15
 * <p>
 * 负载均衡
 */
public interface ClusterLoadBalancer {

    HostAndPort select(List<HostAndPort> hostAndPortList);

}
