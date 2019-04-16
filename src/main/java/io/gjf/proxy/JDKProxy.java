package io.gjf.proxy;

import io.gjf.cluster.Cluster;
import io.gjf.cluster.ClusterLoadBalancer;
import io.gjf.commons.HostAndPort;
import io.gjf.commons.Transport;
import io.gjf.protocol.MethodInvokeMeta;
import io.gjf.protocol.MethodInvokeMetaWrap;
import io.gjf.protocol.ResultWrap;
import io.gjf.resistry.GubboRegisrty;
import io.test.DemoService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Create by GuoJF on 2019/4/16
 */
public class JDKProxy implements GubboProxy, InvocationHandler {

    private Class targetInterface;

    private List<HostAndPort> hostAndPorts;

    private GubboRegisrty gubboRegisrty;

    private Cluster cluster;

    private ClusterLoadBalancer clusterLoadBalancer;

    private Transport transport;

    public JDKProxy(Class<DemoService> demoServiceClass) {

    }

    @Override
    public Object createProxy() {

        hostAndPorts = gubboRegisrty.retriveService(targetInterface);

        gubboRegisrty.subcribeService(targetInterface, hostAndPorts);


        return Proxy.newProxyInstance(targetInterface.getClassLoader(), new Class[]{targetInterface}, this);

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        MethodInvokeMeta invokeMeta = new MethodInvokeMeta(targetInterface, method.getName(), method.getParameterTypes(), args);

        MethodInvokeMetaWrap methodInvokeMetaWrap = new MethodInvokeMetaWrap(invokeMeta);


        //设置附件


        ResultWrap resultWrap = cluster.invoker(hostAndPorts, clusterLoadBalancer, transport, methodInvokeMetaWrap);


        if (resultWrap.getResult().getException() != null) {
            throw resultWrap.getResult().getException();
        }


        return resultWrap.getResult().getReturnValue();


    }

    public JDKProxy() {
    }

    public JDKProxy(Class targetInterface, List<HostAndPort> hostAndPorts, GubboRegisrty gubboRegisrty, Cluster cluster, ClusterLoadBalancer clusterLoadBalancer, Transport transport) {
        this.targetInterface = targetInterface;
        this.hostAndPorts = hostAndPorts;
        this.gubboRegisrty = gubboRegisrty;
        this.cluster = cluster;
        this.clusterLoadBalancer = clusterLoadBalancer;
        this.transport = transport;
    }

    public Class getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class targetInterface) {
        this.targetInterface = targetInterface;
    }

    public List<HostAndPort> getHostAndPorts() {
        return hostAndPorts;
    }

    public void setHostAndPorts(List<HostAndPort> hostAndPorts) {
        this.hostAndPorts = hostAndPorts;
    }

    public GubboRegisrty getGubboRegisrty() {
        return gubboRegisrty;
    }

    public void setGubboRegisrty(GubboRegisrty gubboRegisrty) {
        this.gubboRegisrty = gubboRegisrty;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public ClusterLoadBalancer getClusterLoadBalancer() {
        return clusterLoadBalancer;
    }

    public void setClusterLoadBalancer(ClusterLoadBalancer clusterLoadBalancer) {
        this.clusterLoadBalancer = clusterLoadBalancer;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
