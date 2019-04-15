package io.gjf.cluster;

import io.gjf.commons.HostAndPort;
import io.gjf.commons.Transport;
import io.gjf.protocol.MethodInvokeMeta;
import io.gjf.protocol.ResultWrap;

import java.util.List;

/**
 * Create by GuoJF on 2019/4/15
 * DisasterRecovery
 */
public interface Cluster {

ResultWrap invoker(List<HostAndPort> hostAndPortList, ClusterLoadBalancer loadBalancer, Transport transport, MethodInvokeMeta methodInvokeMeta);



}
