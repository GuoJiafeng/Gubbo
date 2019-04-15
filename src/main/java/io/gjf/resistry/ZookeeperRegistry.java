package io.gjf.resistry;

import io.gjf.commons.HostAndPort;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.Vector;

/**
 * Creat by GuoJF on
 */
public class ZookeeperRegistry implements GubboRegisrty {

    private ZkClient zkClient;


    public ZookeeperRegistry(String servers) {
        zkClient = new ZkClient(servers);
    }


    /*
     * 注册服务
     * */
    public void regisrtyService(Class targetInterface, HostAndPort hostAndPort) {

        String basePath = PREFIX + "/" + targetInterface.getName() + SUFFIX;
        if (!zkClient.exists(basePath)) {

            zkClient.createPersistent(basePath, true);

        }

        String enode = basePath + "/" + hostAndPort.getHost() + ":" + hostAndPort.getPort();

        if (zkClient.exists(enode)) {

            zkClient.delete(enode);
        }

        zkClient.createEphemeral(enode);


    }

    public List<HostAndPort> retriveService(Class targetInterface) {

        String basePath = PREFIX + "/" + targetInterface.getName() + SUFFIX;
        if (!zkClient.exists(basePath)) {

            zkClient.createPersistent(basePath, true);

        }
        List<String> enodes = zkClient.getChildren(basePath);
        List<HostAndPort> hostAndPorts = new Vector<>();
        enodes.forEach((enode) -> {


            String host = enode.split(":")[0];
            Integer port = Integer.valueOf(enode.split(":")[1]);

            hostAndPorts.add(new HostAndPort(host, port));

        });


        return null;
    }

    public void subcribeService(Class targetInterface, List<HostAndPort> hostAndPortList) {


        String basePath = PREFIX + "/" + targetInterface.getName() + SUFFIX;
        if (!zkClient.exists(basePath)) {

            zkClient.createPersistent(basePath, true);

        }

        zkClient.subscribeChildChanges(basePath, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {

                hostAndPortList.clear();
                list.forEach((enode) -> {


                    String host = enode.split(":")[0];
                    Integer port = Integer.valueOf(enode.split(":")[1]);

                    hostAndPortList.add(new HostAndPort(host, port));


                });


            }
        });

    }

    public void close() {

        zkClient.close();

    }
}
