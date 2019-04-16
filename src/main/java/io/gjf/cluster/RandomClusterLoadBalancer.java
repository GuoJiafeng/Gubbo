package io.gjf.cluster;

import io.gjf.commons.HostAndPort;

import java.util.List;
import java.util.Random;

/**
 * Create by GuoJF on 2019/4/15
 */
public class RandomClusterLoadBalancer implements ClusterLoadBalancer {


    @Override
    public HostAndPort select(List<HostAndPort> hostAndPortList) {

        int randowIndex = new Random().nextInt(hostAndPortList.size());

        return hostAndPortList.get(randowIndex);


    }
}
