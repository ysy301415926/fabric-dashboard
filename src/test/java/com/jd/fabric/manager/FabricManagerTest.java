package com.jd.fabric.manager;

import com.jd.fabric.dashboard.manager.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;


public class FabricManagerTest {

    private ChaincodeManager chaincodeManager;

    public FabricManagerTest()
            throws CryptoException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, TransactionException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        chaincodeManager = new ChaincodeManager(getConfig());
    }

    /**
     * 获取节点服务器管理器
     *
     * @return 节点服务器管理器
     */
    public ChaincodeManager getChaincodeManager() {
        return chaincodeManager;
    }

    /**
     * 根据节点作用类型获取节点服务器配
     *            服务器作用类型（1、执行；2、查询）
     * @return 节点服务器配置
     */
    private FabricConfig getConfig() {
        FabricConfig config = new FabricConfig();
        config.setOrderers(getOrderers());
        config.setPeers(getPeers());
//        config.setChaincode(getChaincode("foo", "example_cc_go",
//                "/github.com/example_cc", "1"));d
        config.setChaincode(getChaincode("mychannel", "pandoracc",
                "/root/go/src/github.com/hyperledger/pandora-stack-1.1.0/chaincode/chaincode_example02/go",
                "1.0"));
        config.setChannelArtifactsPath(getChannleArtifactsPath());
        config.setCryptoConfigPath(getCryptoConfigPath());
        return config;
    }

    private Orderers getOrderers() {
        Orderers orderer = new Orderers();
        orderer.setOrdererDomainName("pandora.com");
        orderer.addOrderer("orderer0.pandora.com", "grpc://125.94.44.250:7050");
        //orderer.addOrderer("orderer.example.com", "grpc://116.196.64.240:7050");
        return orderer;
    }

    /**
     * 获取节点服务器集
     *d
     * @return 节点服务器集
     */
    private Peers getPeers() {
        Peers peers = new Peers();
        peers.setOrgName("Org1");
        peers.setOrgMSPID("Org1MSP");
        peers.setOrgDomainName("org1.pandora.com");
        peers.addPeer("peer0.org1.pandora.com", "peer0.org1.pandora.com", "grpc://59.37.137.167:7051",
                "grpc://59.37.137.167:7053", "grpc://59.37.137.167:7054");
//        peers.addPeer("peer0.org2.example.com", "peer0.org2.example.com", "grpc://116.196.125.231:9051",
//                "grpc://116.196.125.231:9053", "http://116.196.125.231:9054");
        return peers;
    }

    /**
     * 获取智能合约
     *
     * @param channelName
     *            频道名称
     * @param chaincodeName
     *            智能合约名称
     * @param chaincodePath
     *            智能合约路径
     * @param chaincodeVersion
     *            智能合约版本
     * @return 智能合约
     */
    private Chaincode getChaincode(String channelName, String chaincodeName, String chaincodePath, String chaincodeVersion) {
        Chaincode chaincode = new Chaincode();
        chaincode.setChannelName(channelName);
        chaincode.setChaincodeName(chaincodeName);
        chaincode.setChaincodePath(chaincodePath);
        chaincode.setChaincodeVersion(chaincodeVersion);
        chaincode.setInvokeWatiTime(10);
        chaincode.setDeployWatiTime(12);
        return chaincode;
    }

    /**
     * 获取channel-artifacts配置路径
     *
     * @return /WEB-INF/classes/fabric/channel-artifacts/
     */
    private String getChannleArtifactsPath() {
      //  String directorys = FabricManager.class.getClassLoader().getResource("fabric").getFile();
        //String directorys = "/usr/local/pandora-sdk/pandorasdk-sdk/src/main/resources/fabric";
        String directorys = "C:/WorkSpaces/Projects/project/fabric-man/fabric-dashboard/src/main/resources/fabric";
        // String directorys = "E:\\demo\\pandora-sdk\\fabric-sdk-java\\src\\resources\\fabric";
        System.out.println("directorys = " + directorys);
        File directory = new File(directorys);
        System.out.println("directory = " + directory.getPath());
        return directory.getPath() + "/channel-artifacts/";
    }

    /**
     * 获取crypto-config配置路径
     *
     * @return /WEB-INF/classes/fabric/crypto-config/
     */
    private String getCryptoConfigPath() {
       // String directorys = FabricManager.class.getClassLoader().getResource("fabric").getFile();
       //String directorys = "/usr/local/pandora-sdk/pandorasdk-sdk/src/main/resources/fabric";
        String directorys = "C:/WorkSpaces/Projects/project/fabric-man/fabric-dashboard/src/main/resources/fabric";

        // String directorys = "E:\\demo\\pandora-sdk\\fabric-sdk-java\\src\\resources\\fabric";
        System.out.println("directorys = " + directorys);
        File directory = new File(directorys);
        System.out.println("directory = " + directory.getPath());

        return directory.getPath() + "/crypto-config/";
    }

}

