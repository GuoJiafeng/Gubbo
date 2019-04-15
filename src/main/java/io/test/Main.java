package io.test;

import io.gjf.resistry.GubboRegisrty;
import io.gjf.resistry.ZookeeperRegistry;
import io.gjf.server.GubboServiceProviderExpose;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Creat by GuoJF on macx
 */
public class Main {
    public static void main(String[] args) throws Exception {


        GubboRegisrty gubboRegisrty = new ZookeeperRegistry("192.168.5.11:2181");
        GubboServiceProviderExpose gubboServiceProviderExpose = new GubboServiceProviderExpose(gubboRegisrty, 20880);

        Map<Class, Object> beans = new HashMap<>();
        beans.put(DemoService.class, new DemoServiceImpl());


        gubboServiceProviderExpose.setBeanFactory(beans);

        gubboServiceProviderExpose.start();
        System.in.read();
        gubboServiceProviderExpose.close();


    }
}
