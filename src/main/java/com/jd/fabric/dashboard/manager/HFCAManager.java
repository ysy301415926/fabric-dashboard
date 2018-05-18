package com.jd.fabric.dashboard.manager;

import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Properties;


public class HFCAManager {

    private static HFCAManager instance = null;

    private FabricUser fabricUser ;

    private static ChaincodeManager manage ;

    private FabricStore fabricStore;

    private static HFCAClient client;

    private static FabricUser admin;

    private static Map<String,Object> configMap;

    public static HFCAManager obtain(Map<String,Object> map)
            throws CryptoException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, TransactionException, IOException {
        if (null == instance) {
            synchronized (HFCAManager.class) {
                if (null == instance) {
                    configMap= map;
                    instance = new HFCAManager();
                }
            }
        }
        return instance;
    }

    public void setConfig(){
        File fabricStoreFile = new File(System.getProperty("java.io.tmpdir") + "/HFCASample.properties");
        if(!fabricStoreFile.exists()){
            try {
                fabricStoreFile.createNewFile();
            } catch (IOException e) {
                 e.printStackTrace();
            }
        }
        fabricStore = new FabricStore(fabricStoreFile);
        try{
            CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
            //TODO v1.1
            //cryptoSuite.init();
            client = HFCAClient.createNewInstance(
                    String.valueOf(configMap.get("caLocation")),
                    getCAProperties(String.valueOf(configMap.get("orgDomainname"))));

            client.setCryptoSuite(cryptoSuite);

            // SampleUser can be any implementation that implements org.hyperledger.fabric.sdk.User Interface
            admin = fabricStore.getMember(String.valueOf(configMap.get("adminName")), String.valueOf(configMap.get("adminOrgName")));
            if (!admin.isEnrolled()) { // Preregistered admin only needs to be enrolled with Fabric CA.
                admin.setEnrollment(client.enroll(admin.getName(), String.valueOf(configMap.get("adminPWD"))));
            }
        }catch(Exception e){
            e.printStackTrace();;
        }
    }

    /**d
     *
     * @return /WEB-INF/classes/fabric/crypto-config/
     */
    private String getCAConfigPath() {
        // String directorys = FabricManager.class.getClassLoader().getResource("fabric").getFile();
        String directorys = "/usr/local/fabric-sdk-java/src/resources/fabric";
        // String directorys = "E:\\demo\\pandora-sdk\\fabric-sdk-java\\src\\resources\\fabric";
        System.out.println("directorys = " + directorys);
        File directory = new File(directorys);
        System.out.println("directory = " + directory.getPath());

        return directory.getPath() + "/crypto-config/peerOrganizations/";
    }

    public Properties getCAProperties(String domainName){
        Properties properties = new Properties();
        properties.setProperty("pemFile", getCAConfigPath()+"/DNAME/ca/ca.DNAME-cert.pem".replaceAll("DNAME", domainName));
        return properties;
    }


    public static void main(String[] a){

//        try{
//            manage = FabricManager.obtain().getManager();
//            HFCAManager caManage = HFCAManager.obtain();
//            caManage.setConfig();
//            RegistrationRequest rr = new RegistrationRequest("zhanglei", "org1.department1");
//            String password = "1234567654321";
//            rr.setSecret(password);
//            String secret = client.register(rr, admin);
//
//            System.out.println("secret = " + secret);
//            Enrollment enrollment = client.enroll("zhanglei",secret);
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }


    }

}