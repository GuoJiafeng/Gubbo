package io.test;

import io.gjf.cluster.FailfastCluster;
import io.gjf.cluster.RandomClusterLoadBalancer;
import io.gjf.commons.NettyTransport;
import io.gjf.proxy.JDKProxy;
import io.gjf.resistry.ZookeeperRegistry;

/**
 * Create by GuoJF on 2019/4/16
 */
public class TestCousumer {
    public static void main(String[] args) {


        JDKProxy jdkProxy = new JDKProxy();


        jdkProxy.setTargetInterface(DemoService.class);
        jdkProxy.setCluster(new FailfastCluster());
        jdkProxy.setClusterLoadBalancer(new RandomClusterLoadBalancer());
        jdkProxy.setTransport(new NettyTransport());
        jdkProxy.setGubboRegisrty(new ZookeeperRegistry("192.168.134.99:2181"));


        DemoService demoService = (DemoService) jdkProxy.createProxy();

        Integer sum = demoService.sum(11, 2);

        System.out.println(sum);


    }
}
