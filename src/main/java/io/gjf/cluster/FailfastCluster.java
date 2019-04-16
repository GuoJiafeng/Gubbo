package io.gjf.cluster;

import io.gjf.commons.HostAndPort;
import io.gjf.commons.Transport;
import io.gjf.protocol.MethodInvokeMetaWrap;
import io.gjf.protocol.ResultWrap;

import java.util.List;

/**
 * Create by GuoJF on 2019/4/16
 */
public class FailfastCluster implements Cluster {
    @Override
    public ResultWrap invoker(List<HostAndPort> hostAndPortList, ClusterLoadBalancer loadBalancer, Transport transport, MethodInvokeMetaWrap methodInvokeMetaWrap) {


        HostAndPort hostAndPort = loadBalancer.select(hostAndPortList);

        try {
            return transport.transfer(hostAndPort, methodInvokeMetaWrap);
        } catch (Exception e) {


            throw new RuntimeException(e.getCause());
        }


    }
}
