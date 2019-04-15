package io.gjf.commons;

import io.gjf.protocol.MethodInvokeMeta;
import io.gjf.protocol.ResultWrap;

/**
 * Create by GuoJF on 2019/4/15
 *  实现网络通信
 *
 */
public interface Transport {

   ResultWrap transfer(HostAndPort hostAndPort, MethodInvokeMeta methodInvokeMeta);

}
