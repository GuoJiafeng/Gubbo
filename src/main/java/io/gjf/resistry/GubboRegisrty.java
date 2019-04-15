package io.gjf.resistry;

import io.gjf.commons.HostAndPort;

import java.util.List;

/**
 * Create by GuoJF on 2019/4/15
 * <p>
 * 注册中心
 */
public interface GubboRegisrty {

    //注册服务
    void regisrtyService(Class targetInterface, HostAndPort hostAndPort);

    //获得服务
    List<HostAndPort> retriveService(Class targetInterface);


    // 订阅服务
    void subcribeService(Class targetInterface, List<HostAndPort> hostAndPortList);


    //关闭
    void close();


}
