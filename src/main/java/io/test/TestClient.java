package io.test;

import io.gjf.commons.HostAndPort;
import io.gjf.commons.NettyTransport;
import io.gjf.commons.Transport;
import io.gjf.protocol.MethodInvokeMeta;
import io.gjf.protocol.MethodInvokeMetaWrap;
import io.gjf.protocol.Result;
import io.gjf.protocol.ResultWrap;

/**
 * Creat by GuoJF on mac
 */
public class TestClient {
    public static void main(String[] args) throws InterruptedException {

        Transport transport = new NettyTransport();

        MethodInvokeMeta methodInvokeMeta = new MethodInvokeMeta(DemoService.class, "sum", new Class[]{Integer.class, Integer.class}, new Object[]{1, 2});


        MethodInvokeMetaWrap methodInvokeMetaWrap = new MethodInvokeMetaWrap(methodInvokeMeta);


        ResultWrap resultWrap = transport.transfer(new HostAndPort("192.168.5.208", 20880), methodInvokeMetaWrap);

        Result result = resultWrap.getResult();

        Object value = result.getReturnValue();

        if (result.getException()!=null){

            System.out.println("12312");
        }



        System.out.println(value.toString());

    }
}
