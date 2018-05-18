package com.jd.fabric.manager;

import com.jd.fabric.dashboard.manager.ChaincodeManager;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.junit.Test;

import java.util.Map;


public class ChaincodeManagerTest {

    @Test
    public void query(){
        try{
            FabricManagerTest fabricManagerTest = new FabricManagerTest();
            ChaincodeManager manage = fabricManagerTest.getChaincodeManager();
            Map<String ,String> queryResult=manage.query("query",new String[] {"a"});
            System.out.println(String.valueOf(queryResult));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }

    @Test
    public  void invoke(){
        try{
            FabricManagerTest fabricManagerTest = new FabricManagerTest();
            ChaincodeManager chaincodeManager = fabricManagerTest.getChaincodeManager();
            Map<String, String> invokeResult = chaincodeManager.invoke("invoke",new String[] {"a", "b", "1"});
            System.out.println(String.valueOf(invokeResult));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Test
    public  void queryBlockByNumber(){
        try{
            FabricManagerTest fabricManagerTest = new FabricManagerTest();
            ChaincodeManager chaincodeManager = fabricManagerTest.getChaincodeManager();
            BlockInfo blockInfo = chaincodeManager.queryBlockByNumber(16L);
            System.out.println(blockInfo.getBlockNumber());
            //System.out.println(blockInfo);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }

    @Test
    public  void queryBlockchainInfo(){
        try{
            FabricManagerTest fabricManagerTest = new FabricManagerTest();
            ChaincodeManager chaincodeManager = fabricManagerTest.getChaincodeManager();
            BlockchainInfo blockchainInfo = chaincodeManager.queryBlockchainInfo();
            System.out.println(blockchainInfo.getBlockchainInfo());

        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }
}

