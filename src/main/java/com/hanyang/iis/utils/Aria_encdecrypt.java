package com.hanyang.iis.utils;

public class Aria_encdecrypt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ARIAProvider encaria;
        try {
            encaria = new ARIAProvider(192);
            encaria.createMasterKey("duzoniss");
            
            /*String encrypthosturl = encaria.encryptToString("192.168.123.12");
            String encryptport = encaria.encryptToString("5100");
            String encrypturl = encaria.encryptToString("jdbc:mariadb://192.168.123.12:5100/pimon?useUnicode=true&autoReconnect=true&autoReconnectForPools=true&zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false&useOldAliasMetadataBehavior=true&functionsNeverReturnBlobs=true");
            String encryptusername = encaria.encryptToString("pimon");
            String encryptpassword = encaria.encryptToString("Duzon#123");
            String encryptdbname = encaria.encryptToString("pimon");*/
            
            String encrypthosturl = encaria.encryptToString("10.102.60.212");
            String encryptport = encaria.encryptToString("5100");
            String encrypturl = encaria.encryptToString("jdbc:mariadb://10.102.60.212:5100/pimon?useUnicode=true&autoReconnect=true&autoReconnectForPools=true&zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false&useOldAliasMetadataBehavior=true&functionsNeverReturnBlobs=true");
            String encryptusername = encaria.encryptToString("pimon");
            String encryptpassword = encaria.encryptToString("Duzon#123");
            String encryptdbname = encaria.encryptToString("pimon");
            
            System.out.println("logfile=/duzon/argospimon/mariadb/data/DBConnectionManager.log");
            System.out.println("base.jdbc.driverClassName=org.mariadb.jdbc.Driver");
            System.out.println("base.jdbc.username=" + encryptusername.toUpperCase());
            System.out.println("base.jdbc.url=" + encrypturl.toUpperCase());
            System.out.println("base.jdbc.password=" + encryptpassword.toUpperCase());
            System.out.println("base.jdbc.maxconn=F7BB91498C67B6C1251D124401273F2F");		//F7BB91498C67B6C1251D124401273F2F : 150
            System.out.println("base.jdbc.hosturl=" + encrypthosturl.toUpperCase());
            System.out.println("base.jdbc.port=" + encryptport.toUpperCase());
            System.out.println("base.jdbc.dbname=" + encryptdbname.toUpperCase());
           	        
            ARIAProvider decaria = new ARIAProvider(192);
            decaria.createMasterKey("duzoniss");
            String test = decaria.decryptFromString("3B993EC64C736AFEF2B7EEBB84CBBEBC");
            String decryptpassword = decaria.decryptFromString("7C9F1FAF12311C3ECA3DA5EB94FC43FE");
            System.out.println("test = " + test);
            System.out.println("decryptpassword = " + encaria.encryptToString("250").toUpperCase());
            System.out.println("decryptpassword = " + encaria.encryptToString("100").toUpperCase());
            System.out.println("decryptpassword = " + decryptpassword);
            

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
