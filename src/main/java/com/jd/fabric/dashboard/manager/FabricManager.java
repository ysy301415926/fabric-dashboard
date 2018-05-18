package com.jd.fabric.dashboard.manager;

import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;


public class FabricManager {

    private ChaincodeManager chaincodeManager;

    private static Map<String, Object> configMap;

    private static FabricManager instance = null;

    public static FabricManager obtain(Map<String, Object> map)
            throws CryptoException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, TransactionException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (null == instance) {
            synchronized (FabricManager.class) {
                if (null == instance) {
                    configMap = map;
                    instance = new FabricManager();
                }
            }
        }
        return instance;
    }

    private FabricManager()
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
        config.setChaincode(getChaincode(String.valueOf(configMap.get("channelName")), String.valueOf(configMap.get("chaincodeName")),
                String.valueOf(configMap.get("chaincodePath")),
                String.valueOf(configMap.get("chaincodeVersion"))));
        config.setChannelArtifactsPath(getChannleArtifactsPath());
        config.setCryptoConfigPath(getCryptoConfigPath());
        return config;
    }

    private Orderers getOrderers() {
        Orderers orderer = new Orderers();
        orderer.setOrdererDomainName(String.valueOf(configMap.get("ordererDomainName")));
        orderer.addOrderer(String.valueOf(configMap.get("ordererName")), String.valueOf(configMap.get("ordererLocation")));
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
        peers.setOrgName(String.valueOf(configMap.get("orgName")));
        peers.setOrgMSPID(String.valueOf(configMap.get("orgName")) + "MSP");
        peers.setOrgDomainName(String.valueOf(configMap.get("orgDomainname")));
        peers.addPeer(String.valueOf(configMap.get("peerName")), String.valueOf(configMap.get("peerEventHubName")), String.valueOf(configMap.get("peerLocation")),
                String.valueOf(configMap.get("peerEventHubLocation")), String.valueOf(configMap.get("caLocation")));
//        peers.addPeer("peer0.org1.example.com", "peer0.org1.example.com", "grpc://116.196.64.241:7051",
//                "grpc://116.196.64.241:7053", "http://116.196.64.241:7054");
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
        chaincode.setInvokeWatiTime(Integer.parseInt(configMap.get("invokeWatiTime").toString()));
        chaincode.setDeployWatiTime(Integer.parseInt(configMap.get("deployWatiTime").toString()));
        return chaincode;
    }

    /**
     * 获取channel-artifacts配置路径
     *
     * @return /WEB-INF/classes/fabric/channel-artifacts/
     */
    private String getChannleArtifactsPath() {
        String path = FabricManager.class.getClassLoader().getResource("").getPath();
        //String path = "/usr/local/pandora-sdk/pandorasdk-sdk/src/main/resources";
        return path + "/fabric/channel-artifacts/";
    }

    /**
     * 获取crypto-config配置路径
     *
     * @return /WEB-INF/classes/fabric/crypto-config/
     */
    private String getCryptoConfigPath() {
       // String directorys = FabricManager.class.getClassLoader().getResource("fabric").getFile();
        String path = FabricManager.class.getClassLoader().getResource("").getPath();
        //String path = "/usr/local/pandora-sdk/pandorasdk-sdk/src/main/resources";

        return path + "/fabric/crypto-config/";
    }

}