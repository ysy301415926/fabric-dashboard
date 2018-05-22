package com.jd.fabric.manager;

import com.google.protobuf.InvalidProtocolBufferException;
import com.jd.fabric.dashboard.manager.ChaincodeManager;
import org.apache.commons.codec.binary.Hex;
import org.hyperledger.fabric.protos.ledger.rwset.kvrwset.KvRwset;
import org.hyperledger.fabric.sdk.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ChaincodeManagerTest {


    //chaincode------------------------------------------------------------------------------------
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
            Map<String, String> invokeResult = chaincodeManager.invoke("invoke",new String[] {"a", "b", "5"});
            System.out.println(String.valueOf(invokeResult));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //chaincode------------------------------------------------------------------------------------


    //channel------------------------------------------------------------------------------------

    //获取所有channels的名字
    @Test
    public  void queryChannels(){
        try{
            FabricManagerTest fabricManagerTest = new FabricManagerTest();
            ChaincodeManager chaincodeManager = fabricManagerTest.getChaincodeManager();
            Set<String> channels = chaincodeManager.queryChannels();

            for(String channel:channels){
                System.out.println(channel);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    //获取当前channel信息
    @Test
    public  void getChannel(){
        try{
            FabricManagerTest fabricManagerTest = new FabricManagerTest();
            ChaincodeManager chaincodeManager = fabricManagerTest.getChaincodeManager();
            Channel channel = chaincodeManager.getChannel("mychannel");
            System.out.print(channel.getName());
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //channel------------------------------------------------------------------------------------


    //blockchain------------------------------------------------------------------------------------

    //获取区块链高度
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
    //blockchain------------------------------------------------------------------------------------


    //block------------------------------------------------------------------------------------

    //根据高度获取块信息
    @Test
    public  void queryBlockByNumber(){
        try{
            FabricManagerTest fabricManagerTest = new FabricManagerTest();
            ChaincodeManager chaincodeManager = fabricManagerTest.getChaincodeManager();
            BlockInfo blockInfo = chaincodeManager.queryBlockByNumber(20L);
            System.out.println("getNumber---------------------------------------");
            System.out.println(blockInfo.getBlock().getHeader().getNumber());
            System.out.println("getNumber---------------------------------------");

            System.out.println("getPreviousHash---------------------------------------");
            System.out.println(Hex.encodeHexString(blockInfo.getPreviousHash()));
            System.out.println("getPreviousHash---------------------------------------");

            System.out.println("getDataHash---------------------------------------");
            System.out.println(Hex.encodeHexString(blockInfo.getDataHash()));
            System.out.println("getDataHash---------------------------------------");
            Iterator<BlockInfo.EnvelopeInfo> iterable = blockInfo.getEnvelopeInfos().iterator();
            while(iterable.hasNext()) {
                BlockInfo.EnvelopeInfo envelopeInfo = iterable.next();
                String txID = envelopeInfo.getTransactionID();
                System.out.println(txID);
                BlockInfo.TransactionEnvelopeInfo transactionEnvelopeInfo = (BlockInfo.TransactionEnvelopeInfo) envelopeInfo;
                for (BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo transactionActionInfo : transactionEnvelopeInfo.getTransactionActionInfos()) {
                    TxReadWriteSetInfo rwsetInfo = transactionActionInfo.getTxReadWriteSet();
                    if (null != rwsetInfo) {

                        for (TxReadWriteSetInfo.NsRwsetInfo nsRwsetInfo : rwsetInfo.getNsRwsetInfos()) {
                            final String namespace = nsRwsetInfo.getNamespace();
                            KvRwset.KVRWSet rws = null;
                            try {
                                rws = nsRwsetInfo.getRwset();
                            } catch (InvalidProtocolBufferException e1) {
                                e1.printStackTrace();
                            }

                            int rs = -1;
                            for (KvRwset.KVRead readList : rws.getReadsList()) {
                                rs++;
                                System.out.println("readList---------------------------------------");
                                System.out.println(namespace);
                                System.out.println(rs);
                                System.out.println(readList.getKey());
                                System.out.println(readList.getVersion().getBlockNum());
                                System.out.println(readList.getVersion().getTxNum());
                                System.out.println("readList---------------------------------------");
                            }

                            rs = -1;
                            for (KvRwset.KVWrite writeList : rws.getWritesList()) {
                                rs++;
                                System.out.println("writeList---------------------------------------");
                                System.out.println(namespace);
                                System.out.println(rs);
                                System.out.println(writeList.getKey());
                                System.out.println(new String(writeList.getValue().toByteArray(), "UTF-8"));
                                System.out.println("writeList---------------------------------------");
                            }
                        }
                    }
                }
            }





        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }

    //block------------------------------------------------------------------------------------

    //根据txid获取事物
    @Test
    public  void queryTransactionByID(){
        try{
            FabricManagerTest fabricManagerTest = new FabricManagerTest();
            ChaincodeManager chaincodeManager = fabricManagerTest.getChaincodeManager();
            TransactionInfo transactionInfo = chaincodeManager.queryTransactionByID("2129d1123d27852607d52c6ef44a0088dd9da1b1d6f204fab837ca12fa065a85");
            System.out.println(transactionInfo.getEnvelope());

        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }




}

