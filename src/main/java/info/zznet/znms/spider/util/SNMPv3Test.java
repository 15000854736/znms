package info.zznet.znms.spider.util;
import java.io.IOException;  
import java.util.List;  
import java.util.Vector;  
  

import org.snmp4j.PDU;  
import org.snmp4j.ScopedPDU;  
import org.snmp4j.Snmp;  
import org.snmp4j.Target;  
import org.snmp4j.UserTarget;  
import org.snmp4j.event.ResponseEvent;  
import org.snmp4j.mp.MPv3;  
import org.snmp4j.mp.SnmpConstants;  
import org.snmp4j.security.AuthMD5;  
import org.snmp4j.security.AuthSHA;
import org.snmp4j.security.PrivDES;  
import org.snmp4j.security.SecurityLevel;  
import org.snmp4j.security.SecurityModels;  
import org.snmp4j.security.SecurityProtocols;  
import org.snmp4j.security.USM;  
import org.snmp4j.security.UsmUser;  
import org.snmp4j.smi.OID;  
import org.snmp4j.smi.OctetString;  
import org.snmp4j.smi.UdpAddress;  
import org.snmp4j.smi.VariableBinding;  
import org.snmp4j.transport.DefaultUdpTransportMapping;  
import org.snmp4j.util.DefaultPDUFactory;  
import org.snmp4j.util.TableEvent;  
import org.snmp4j.util.TableUtils;  
  
public class SNMPv3Test {  
    public static void main(String[] args) throws IOException, InterruptedException {  
          
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());  
        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);  
        SecurityModels.getInstance().addSecurityModel(usm);  
        snmp.listen();  
        
        String userName="sxismd5";
        // Add User  
        UsmUser user = new UsmUser(  
                new OctetString(userName),  
                AuthMD5.ID, new OctetString("sxis@sxis.com"),  
                PrivDES.ID, new OctetString("sxis@sxis.com"));  
        //If the specified SNMP engine id is specified, this user can only be used with the specified engine ID  
        //So if it's not correct, will get an error that can't find a user from the user table.  
        //snmp.getUSM().addUser(new OctetString("nmsAdmin"), new OctetString("0002651100"), user);  
        snmp.getUSM().addUser(new OctetString(userName), user);  
          
        UserTarget target = new UserTarget();  
        target.setVersion(SnmpConstants.version3);  
        target.setAddress(new UdpAddress("192.168.1.16/161"));  
        target.setSecurityLevel(SecurityLevel.AUTH_PRIV);  
        target.setSecurityName(new OctetString(userName));  
        target.setTimeout(1500);    //1.5s  
        target.setRetries(3);  
                  
        //OctetString contextEngineId = new OctetString("0002651100[02]");  
        sendRequest(snmp, createGetPdu(), target);  
        //snmpWalk(snmp, target, contextEngineId);  
    }  
      
    private static PDU createGetPdu() {  
        ScopedPDU pdu = new ScopedPDU();  
        pdu.setType(PDU.GET);  
        //pdu.setContextEngineID(contextEngineId);    //if not set, will be SNMP engine id  
        //pdu.setContextName(contextName);  //must be same as SNMP agent  
          
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.3.0"))); //sysUpTime  
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.5.0"))); //sysName  
        pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.2021.11.9.0")));   //expect an no_such_instance error  
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.31.1.1.1.10.1")));  
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.31.1.1.1.6.2")));   
        return pdu;  
    }  
      
    private static void sendRequest(Snmp snmp, PDU pdu, UserTarget target)  
    throws IOException {  
        ResponseEvent responseEvent = snmp.send(pdu, target);  
        PDU response = responseEvent.getResponse();  
          
        if (response == null) {  
            System.out.println("TimeOut...");  
        } else {  
            if (response.getErrorStatus() == PDU.noError) {  
                Vector<? extends VariableBinding> vbs = response.getVariableBindings();  
                for (VariableBinding vb : vbs) {  
                    System.out.println(vb + " ," + vb.getVariable().getSyntaxString());  
                }  
            } else {  
                System.out.println("Error:" + response.getErrorStatusText());  
            }  
        }  
    }  
      
    private static void snmpWalk(Snmp snmp, UserTarget target, OctetString contextEngineId) {  
        TableUtils utils = new TableUtils(snmp,  
                new MyDefaultPDUFactory(PDU.GETNEXT, //GETNEXT or GETBULK)  
                                        contextEngineId));  
        utils.setMaxNumRowsPerPDU(5);   //only for GETBULK, set max-repetitions, default is 10  
        OID[] columnOids = new OID[] {  
        		 new OID("1.3.6.1.2.1.1.9.1.2"), //sysORID  
                 new OID("1.3.6.1.2.1.1.9.1.3")  //sysORDescr  
        };  
        // If not null, all returned rows have an index in a range (lowerBoundIndex, upperBoundIndex]  
        List<TableEvent> l = utils.getTable(target, columnOids, new OID("1"), new OID("1"));  
        for (TableEvent e : l) {  
            System.out.println(e);  
        }  
    }  
      
    private static class MyDefaultPDUFactory extends DefaultPDUFactory {  
        private OctetString contextEngineId = null;  
          
        public MyDefaultPDUFactory(int pduType, OctetString contextEngineId) {  
            super(pduType);  
            this.contextEngineId = contextEngineId;  
        }  
  
        @Override  
        public PDU createPDU(Target target) {  
            PDU pdu = super.createPDU(target);  
            if (target.getVersion() == SnmpConstants.version3) {  
                ((ScopedPDU)pdu).setContextEngineID(contextEngineId);  
            }  
            return pdu;  
        }         
    }  
}  