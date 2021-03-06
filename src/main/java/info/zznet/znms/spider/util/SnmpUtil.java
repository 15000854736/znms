package info.zznet.znms.spider.util;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.spider.bean.ApProperties;
import info.zznet.znms.spider.bean.Interface;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.spider.bean.ScanItem;
import info.zznet.znms.spider.bean.ScanResult;
import info.zznet.znms.spider.constants.SnmpConstants;

import org.apache.commons.lang.StringUtils;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.AuthSHA;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by shenqilei on 2016/9/22.
 */
public class SnmpUtil {

    public static List<ScanResult> getResult(ScanHost scanHost){
        List<ScanResult> result = new ArrayList<>();
        try{
            if (null!=scanHost&&
                    !StringUtils.equals(SnmpConstants.HOST_STATUS_DOWN,scanHost.getStatus())&&
                    (null!=scanHost.getScanItems()&&!scanHost.getScanItems().isEmpty())){

                    if (StringUtils.equals(SnmpConstants.SNMP_VERSION_1,scanHost.getSnmpVersion())){
                        //TODO 实现v1 协议
                         String port = SnmpConstants.SNMP_DEFAULT_PORT;
                         if (StringUtils.isNotEmpty(scanHost.getSnmpPort())&&!scanHost.getSnmpPort().equals("null")){
                             port = scanHost.getSnmpPort();
                         }
                         Address targetAddress = GenericAddress.parse("udp:"+scanHost.getIp()+"/"+port);
                         PDU pdu = new PDU();
                         for (ScanItem scanItem:scanHost.getScanItems()){
                             pdu.add(new VariableBinding(new OID(scanItem.getOid())));
                         }
                         pdu.setType(PDU.GET);
                         result= getOIDValueV2V1(pdu, targetAddress,scanHost);

                    }else if (StringUtils.equals(SnmpConstants.SNMP_VERSION_2,scanHost.getSnmpVersion())){
                        String port = SnmpConstants.SNMP_DEFAULT_PORT;
                        if (StringUtils.isNotEmpty(scanHost.getSnmpPort())&&!scanHost.getSnmpPort().equals("null")){
                            port = scanHost.getSnmpPort();
                        }
                        Address targetAddress = GenericAddress.parse("udp:"+scanHost.getIp()+"/"+port);

                        PDU pdu = new PDU();
                        for (ScanItem scanItem:scanHost.getScanItems()){
                            pdu.add(new VariableBinding(new OID(scanItem.getOid())));
                        }
                        pdu.setType(PDU.GET);
                        result= getOIDValueV2V1(pdu, targetAddress, scanHost);

                    }else if (StringUtils.equals(SnmpConstants.SNMP_VERSION_3,scanHost.getSnmpVersion())){
                        //TODO 实现v3协议
                         String port = SnmpConstants.SNMP_DEFAULT_PORT;
                         if (StringUtils.isNotEmpty(scanHost.getSnmpPort())&&!scanHost.getSnmpPort().equals("null")){
                             port = scanHost.getSnmpPort();
                         }
                         USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
                         SecurityModels.getInstance().addSecurityModel(usm);
                         result= getOidValueV3(scanHost, result, port);
                    }else{
                        ZNMSLogger.warn("主机["+scanHost+"]，SNMP版本号有误："+scanHost.getSnmpVersion());
                    }

            }
        }catch (Exception e){
            ZNMSLogger.error("主机["+scanHost+"]，获取信息失败。",e);
        }
        return result;
    }


	private static List<ScanResult> getOidValueV3(ScanHost scanHost,
			List<ScanResult> result,String port) throws IOException {
		TransportMapping transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
		try{
			String userName=scanHost.getSnmpUserName();
			String password=scanHost.getSnmpPassword();
			String authProtocol= scanHost.getSnmpAuthProtocol();
			if(authProtocol.equalsIgnoreCase("MD5")){
				UsmUser user = new UsmUser(  
						new OctetString(userName),  
						AuthMD5.ID, new OctetString(password),  
						PrivDES.ID, new OctetString(password));  
				snmp.getUSM().addUser(new OctetString(userName), user);  
			}else if(authProtocol.equalsIgnoreCase("SHA")){
				UsmUser user = new UsmUser(  
						new OctetString(userName),  
						AuthSHA.ID, new OctetString(password),  
						PrivDES.ID, new OctetString(password));  
				snmp.getUSM().addUser(new OctetString(userName), user); 
			}
			 
			 UserTarget target = new UserTarget();  
			 target.setVersion(org.snmp4j.mp.SnmpConstants.version3);  
			 
			 Address targetAddress = GenericAddress.parse("udp:"+scanHost.getIp()+"/"+port);
			 target.setAddress(targetAddress);  
			 target.setSecurityLevel(SecurityLevel.AUTH_PRIV);  
			 target.setSecurityName(new OctetString(userName));  
			 target.setTimeout(SnmpConstants.SNMP_TIMEOUT);  
			 target.setRetries(SnmpConstants.SNMP_RETRIES);  
			           
			 ScopedPDU pdu = new ScopedPDU();  
			 pdu.setType(PDU.GET);  
			 
			 for (ScanItem scanItem:scanHost.getScanItems()){
			     pdu.add(new VariableBinding(new OID(scanItem.getOid())));
			 }
			 ResponseEvent responseEvent = snmp.send(pdu, target);  
			 //同步
            if (null!=responseEvent&&
                    responseEvent.getResponse().size()==scanHost.getScanItems().size()){
                int i=0;
                String[] netStreamValue = new String[]{"0","0"};
                ScanResult netStreamScanResult = null;
                for (ScanItem scanItem:scanHost.getScanItems()){
                    if ("netStream".equals(scanItem.getRrdTemplateName())){
                        if ("upStream".equals(scanItem.getRrdDS())){
                        	String upStream = responseEvent.getResponse().get(i).getVariable().toString();
                            netStreamValue[0] = String.valueOf(Long.parseLong(upStream) * 8l);
                        }else if("downStream".equals(scanItem.getRrdDS())){
                        	String downStream = responseEvent.getResponse().get(i).getVariable().toString();
                            netStreamValue[1] = String.valueOf(Long.parseLong(downStream) * 8l);
                        }
                        if (null==netStreamScanResult){
                            netStreamScanResult = new ScanResult();
                            netStreamScanResult.setRrdDataId(scanItem.getRrdDataId());
                            netStreamScanResult.setRrdTemplateName(scanItem.getRrdTemplateName());
                        }
                    }else{
                        ScanResult scanResult = new ScanResult();
                        scanResult.setValue(new String[]{responseEvent.getResponse().get(i).getVariable().toString()});
                        scanResult.setRrdFile(scanItem.getRrdFile());
                        scanResult.setRrdDS(scanItem.getRrdDS());
                        scanResult.setRrdDataId(scanItem.getRrdDataId());
                        scanResult.setRrdTemplateName(scanItem.getRrdTemplateName());
                        result.add(scanResult);
                    }
                    i++;
                }

                //有流量信息
                if (null!=netStreamScanResult){
                    netStreamScanResult.setValue(netStreamValue);
                    result.add(netStreamScanResult);
                }
            }
		}catch (Exception e){
            ZNMSLogger.error("主机["+scanHost+"]，获取V3信息失败。",e);
        }finally {
            if (null!=snmp){
                try {
                    snmp.close();
                } catch (IOException e) {
                    ZNMSLogger.error("getOidValueV3",e);
                }
            }
        }
		 return result;
	}
    
    /**
	 * 获取对应的v2v1版本的oid对应的值
	 * @param oid
	 * @param address
	 */
	private static List<ScanResult> getOIDValueV2V1(PDU pdu, Address address,ScanHost scanHost) throws Exception{
		List<ScanResult> result = new ArrayList<>();
		TransportMapping transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
		try{
			snmp.listen();
			String community = SnmpConstants.SNMP_DEFAULT_COMMUNITY;
	        if (StringUtils.isNotEmpty(scanHost.getCommunity())){
	             community = scanHost.getCommunity();
	        }
			// target
			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(community));
			target.setAddress(address);
			target.setRetries(SnmpConstants.SNMP_RETRIES);
			target.setTimeout(SnmpConstants.SNMP_TIMEOUT);
			if (StringUtils.equals(SnmpConstants.SNMP_VERSION_2,scanHost.getSnmpVersion())) {
				target.setVersion(org.snmp4j.mp.SnmpConstants.version2c);
			} else if (StringUtils.equals(SnmpConstants.SNMP_VERSION_1,scanHost.getSnmpVersion())) {
				target.setVersion(org.snmp4j.mp.SnmpConstants.version1);
			}
			ResponseEvent responseEvent = snmp.send(pdu, target);
			 //同步
            if (null!=responseEvent&&null!=responseEvent.getResponse()&&
                    responseEvent.getResponse().size()==scanHost.getScanItems().size()){
                int i=0;

                ScanResult netStreamScanResult = null;
                Map<String,ScanResult>netStreamResultMap = new HashMap<>();
                for (ScanItem scanItem:scanHost.getScanItems()){
                    if ("netStream".equals(scanItem.getRrdTemplateName())){

                        String index = StringUtils.substringAfterLast(scanItem.getOid(),".");
                        netStreamScanResult =   netStreamResultMap.get(index);
                        if(null==netStreamScanResult){
                            netStreamScanResult = new ScanResult();
                            netStreamScanResult.setRrdDataId(scanItem.getRrdDataId());
                            netStreamScanResult.setRrdTemplateName(scanItem.getRrdTemplateName());
                            netStreamScanResult.setRrdFile(scanItem.getRrdFile());
                            netStreamResultMap.put(index,netStreamScanResult);
                        }

                        String[] netStreamValue = netStreamScanResult.getValue();
                        if(null==netStreamValue){
                            netStreamValue = new String[]{"0","0"};
                            netStreamScanResult.setValue(netStreamValue);
                        }

                        if ("upStream".equals(scanItem.getRrdDS())){
                        	String upStream = responseEvent.getResponse().get(i).getVariable().toString();
                            netStreamValue[0] = String.valueOf(Long.parseLong(upStream) * 8l);
                        }else if("downStream".equals(scanItem.getRrdDS())){
                        	String downStream = responseEvent.getResponse().get(i).getVariable().toString();
                            netStreamValue[1] = String.valueOf(Long.parseLong(downStream) * 8l);
                        }

                    }else{
                        ScanResult scanResult = new ScanResult();
                        scanResult.setValue(new String[]{responseEvent.getResponse().get(i).getVariable().toString()});
                        scanResult.setRrdFile(scanItem.getRrdFile());
                        scanResult.setRrdDS(scanItem.getRrdDS());
                        scanResult.setRrdDataId(scanItem.getRrdDataId());
                        scanResult.setRrdTemplateName(scanItem.getRrdTemplateName());
                        result.add(scanResult);
                    }
                    i++;
                }

                //有流量信息
                if(!netStreamResultMap.isEmpty()){
                    result.addAll(netStreamResultMap.values());
                }
            }
		}catch (Exception e){
            ZNMSLogger.error("主机["+scanHost+"]，获取V1/V2信息失败。",e);
        }finally {
            if (null!=snmp){
                try {
                    snmp.close();
                } catch (IOException e) {
                    ZNMSLogger.error("getOIDValueV2V1",e);
                }
            }
        }
		return result;
	}

	
    /**
     * 获取指定设备接口信息
     * @return
     */
    public static List<Interface> getInterfaces(ScanHost scanHost){
        List<Interface> result = new ArrayList<>();
        Snmp snmp =null;
        if(null!=scanHost){
            try{
                if (StringUtils.equals(SnmpConstants.SNMP_VERSION_1,scanHost.getSnmpVersion())){
                    //TODO 实现v1协议
                	String port = SnmpConstants.SNMP_DEFAULT_PORT;
                    if (StringUtils.isNotEmpty(scanHost.getSnmpPort())){
                        port = scanHost.getSnmpPort();
                    }
                    String community = SnmpConstants.SNMP_DEFAULT_COMMUNITY;
                    if (StringUtils.isNotEmpty(scanHost.getCommunity())){
                        community = scanHost.getCommunity();
                    }
                    Address targetAddress = GenericAddress.parse("udp:"+scanHost.getIp()+"/"+port);
                    CommunityTarget communityTarget = new CommunityTarget();
                    communityTarget.setCommunity(new OctetString(community));
                    communityTarget.setAddress(targetAddress);
                    communityTarget.setRetries(SnmpConstants.SNMP_RETRIES);
                    communityTarget.setTimeout(SnmpConstants.SNMP_TIMEOUT);
                    communityTarget.setVersion(org.snmp4j.mp.SnmpConstants.version1);

                    snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    OID[] columns =new OID[4];
                    //ifName
                    columns[0] = new OID ("1.3.6.1.2.1.31.1.1.1.1");
                    //ifLinkUpDownTrapEnable
                    columns[1] = new OID("1.3.6.1.2.1.31.1.1.1.14");
                    //ifHighSpeed
                    columns[2] = new OID("1.3.6.1.2.1.31.1.1.1.15");
                    //ifAlias
                    columns[3] = new OID("1.3.6.1.2.1.31.1.1.1.18");

                    TableUtils tableUtils = new TableUtils(snmp,new DefaultPDUFactory(PDU.GETNEXT));
                    List<TableEvent> tableEvents = tableUtils.getTable(communityTarget,columns,null,null);

                    for (TableEvent tableEvent:tableEvents){

                        if (!tableEvent.isError()){
                            VariableBinding[] variableBindings = tableEvent.getColumns();
                            if (null!=variableBindings&&
                                    variableBindings.length==4){
                                Interface intf = new Interface();
                                intf.setIndex(tableEvent.getIndex().getValue()[0]);
                                intf.setName(variableBindings[0].toValueString());
                                intf.setStatus(variableBindings[1].toValueString());
                                intf.setAlias(variableBindings[3].toValueString());
                                result.add(intf);
                            }
                        }
                    }
                    
                }else if(StringUtils.equals(SnmpConstants.SNMP_VERSION_2,scanHost.getSnmpVersion())){
                    String port = SnmpConstants.SNMP_DEFAULT_PORT;
                    if (StringUtils.isNotEmpty(scanHost.getSnmpPort())){
                        port = scanHost.getSnmpPort();
                    }
                    String community = SnmpConstants.SNMP_DEFAULT_COMMUNITY;
                    if (StringUtils.isNotEmpty(scanHost.getCommunity())){
                        community = scanHost.getCommunity();
                    }
                    Address targetAddress = GenericAddress.parse("udp:"+scanHost.getIp()+"/"+port);
                    CommunityTarget communityTarget = new CommunityTarget();
                    communityTarget.setCommunity(new OctetString(community));
                    communityTarget.setAddress(targetAddress);
                    communityTarget.setRetries(SnmpConstants.SNMP_RETRIES);
                    communityTarget.setTimeout(SnmpConstants.SNMP_TIMEOUT);
                    communityTarget.setVersion(org.snmp4j.mp.SnmpConstants.version2c);

                    snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    OID[] columns =new OID[4];
                    //ifName
                    columns[0] = new OID ("1.3.6.1.2.1.31.1.1.1.1");
                    //ifLinkUpDownTrapEnable
                    columns[1] = new OID("1.3.6.1.2.1.31.1.1.1.14");
                    //ifHighSpeed
                    columns[2] = new OID("1.3.6.1.2.1.31.1.1.1.15");
                    //ifAlias
                    columns[3] = new OID("1.3.6.1.2.1.31.1.1.1.18");

                    TableUtils tableUtils = new TableUtils(snmp,new DefaultPDUFactory(PDU.GETNEXT));
                    List<TableEvent> tableEvents = tableUtils.getTable(communityTarget,columns,null,null);

                    for (TableEvent tableEvent:tableEvents){

                        if (!tableEvent.isError()){
                            VariableBinding[] variableBindings = tableEvent.getColumns();
                            if (null!=variableBindings&&
                                    variableBindings.length==4){
                                Interface intf = new Interface();
                                intf.setIndex(tableEvent.getIndex().getValue()[0]);
                                intf.setName(variableBindings[0].toValueString());
                                intf.setStatus(variableBindings[1].toValueString());
                                intf.setAlias(variableBindings[3].toValueString());
                                result.add(intf);
                            }
                        }
                    }

                }else if(StringUtils.equals(SnmpConstants.SNMP_VERSION_3,scanHost.getSnmpVersion())){
                    //TODO 实现v3协议
                	 String port = SnmpConstants.SNMP_DEFAULT_PORT;
                     if (StringUtils.isNotEmpty(scanHost.getSnmpPort())){
                         port = scanHost.getSnmpPort();
                     }
                     snmp = new Snmp(new DefaultUdpTransportMapping());
                     USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);  
                     SecurityModels.getInstance().addSecurityModel(usm);  
                     snmp.listen();  
                     
                    String userName=scanHost.getSnmpUserName();
             		String password=scanHost.getSnmpPassword();
             		String authProtocol= scanHost.getSnmpAuthProtocol();
             		if(authProtocol.equalsIgnoreCase("MD5")){
             			UsmUser user = new UsmUser(  
             					new OctetString(userName),  
             					AuthMD5.ID, new OctetString(password),  
             					PrivDES.ID, new OctetString(password));  
             			snmp.getUSM().addUser(new OctetString(userName), user);  
             		}else if(authProtocol.equalsIgnoreCase("SHA")){
             			UsmUser user = new UsmUser(  
             					new OctetString(userName),  
             					AuthSHA.ID, new OctetString(password),  
             					PrivDES.ID, new OctetString(password));  
             			snmp.getUSM().addUser(new OctetString(userName), user); 
             		}
                     
                     UserTarget target = new UserTarget();  
                     target.setVersion(org.snmp4j.mp.SnmpConstants.version3);  
                     Address targetAddress = GenericAddress.parse("udp:"+scanHost.getIp()+"/"+port);
                     target.setAddress(targetAddress);  
                     target.setSecurityLevel(SecurityLevel.AUTH_PRIV);  
                     target.setSecurityName(new OctetString(userName));  
                     target.setTimeout(SnmpConstants.SNMP_TIMEOUT);  
                     target.setRetries(SnmpConstants.SNMP_RETRIES);  
                     
                     OID[] columns =new OID[4];
                     //ifName
                     columns[0] = new OID ("1.3.6.1.2.1.31.1.1.1.1");
                     //ifLinkUpDownTrapEnable
                     columns[1] = new OID("1.3.6.1.2.1.31.1.1.1.14");
                     //ifHighSpeed
                     columns[2] = new OID("1.3.6.1.2.1.31.1.1.1.15");
                     //ifAlias
                     columns[3] = new OID("1.3.6.1.2.1.31.1.1.1.18");

                     TableUtils tableUtils = new TableUtils(snmp,new DefaultPDUFactory(PDU.GETNEXT));
                     List<TableEvent> tableEvents = tableUtils.getTable(target,columns,null,null);
                     
                     for (TableEvent tableEvent:tableEvents){
                         if (!tableEvent.isError()){
                             VariableBinding[] variableBindings = tableEvent.getColumns();
                             if (null!=variableBindings&&
                                     variableBindings.length==4){
                                 Interface intf = new Interface();
                                 intf.setIndex(tableEvent.getIndex().getValue()[0]);
                                 intf.setName(variableBindings[0].toValueString());
                                 intf.setStatus(variableBindings[1].toValueString());
                                 intf.setAlias(variableBindings[3].toValueString());
                                 result.add(intf);
                             }
                         }
                     }
                     
                }else{
                    ZNMSLogger.warn("主机["+scanHost+"]，SNMP版本号有误："+scanHost.getSnmpVersion());
                }
            }catch (Exception e){
                ZNMSLogger.error("主机["+scanHost+"]，获取接口信息失败。",e);
            }finally {
                if (null!=snmp){
                    try {
                        snmp.close();
                    } catch (IOException e) {
                        ZNMSLogger.error("getInterfaces",e);
                    }
                }
            }
        }
        return result;
    }
    
    
    @SuppressWarnings("rawtypes")
	public static List<ApProperties> getApInfo(ScanHost scanHost,OID[] columns){
    	List<ApProperties> list=new ArrayList<ApProperties>();
    	Snmp snmp =null;
    	try {
    		String port = SnmpConstants.SNMP_DEFAULT_PORT;
            if (StringUtils.isNotEmpty(scanHost.getSnmpPort())&&!scanHost.getSnmpPort().equals("null")){
                port = scanHost.getSnmpPort();
            }
            //Address address = GenericAddress.parse("udp:10.40.255.254/161");
            Address address = GenericAddress.parse("udp:"+scanHost.getIp()+"/"+port);
			CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(SnmpConstants.SNMP_DEFAULT_COMMUNITY));
            if(StringUtils.isNotEmpty(scanHost.getCommunity())){
                target.setCommunity(new OctetString(scanHost.getCommunity()));
            }

			target.setAddress(address);
			target.setTimeout(SnmpConstants.SNMP_TIMEOUT);
			target.setRetries(SnmpConstants.SNMP_RETRIES);
			if (StringUtils.equals(SnmpConstants.SNMP_VERSION_2,scanHost.getSnmpVersion())) {
				target.setVersion(org.snmp4j.mp.SnmpConstants.version2c);
			} else if (StringUtils.equals(SnmpConstants.SNMP_VERSION_1,scanHost.getSnmpVersion())) {
				target.setVersion(org.snmp4j.mp.SnmpConstants.version1);
			}
			snmp = new Snmp(new DefaultUdpTransportMapping());
			snmp.listen();
			TableUtils utils = new TableUtils(snmp,new DefaultPDUFactory(PDU.GETNEXT));  
		
//			  "1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.1.88.105.108.7.151.85";//AP的MAC地址
//	    	  "1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.26.88.105.108.7.151.85";//AP的管理状态
//	    	  "1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.33.88.105.108.7.151.85";//AP的IP地址
//			  "1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.2.88.105.108.7.151.85";//AP的名称
//			  "1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.32.88.105.108.7.151.85";//AP的序列号
//			  "1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.34.88.105.108.7.151.85";//当前在线人数
//			  "1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.41.88.105.108.7.151.85";//最大在线人数
			/*OID[] columns =new OID[4];
			//mac
			columns[0] = new OID ("1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.1");
			//IP
			columns[1] = new OID("1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.33");
			//当前在线人数
			columns[2] = new OID("1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.34");
			//最大在线人数
			columns[3] = new OID("1.3.6.1.4.1.4881.1.1.10.2.56.2.1.1.1.41");*/
			//   
			List<TableEvent> tableEvents = utils.getTable(target, columns, null, null);
            ZNMSLogger.debug("ADDRESS："+target.getAddress().toString()+"，version："+target.getVersion()+"，"+"，table size："+tableEvents.size());
			  for (TableEvent tableEvent:tableEvents){
                if (!tableEvent.isError()){
                    VariableBinding[] variableBindings = tableEvent.getColumns();
                    if (null!=variableBindings&&
                            variableBindings.length==columns.length){
                    	ApProperties apProperties=new ApProperties();
                    	if(columns.length==2){
                    		apProperties.setApMac(variableBindings[0].toValueString());
                    		apProperties.setOnLineNum(variableBindings[1].toValueString());
                    		list.add(apProperties);
                    	}else if(columns.length==5){
                    		apProperties.setApMac(variableBindings[0].toValueString());
                    		apProperties.setApIp(variableBindings[1].toValueString());
                    		apProperties.setOnLineNum(variableBindings[2].toValueString());
                    		apProperties.setOnLineMaxNum(variableBindings[3].toValueString());
                            apProperties.setApName(variableBindings[4].toValueString());
                    		list.add(apProperties);
                    	}
                    }
                }else{
                    ZNMSLogger.debug("error msg："+tableEvent.getErrorMessage());
                }
            }
		} catch (IOException e) {
            ZNMSLogger.error("主机["+scanHost+"]，获取AP信息失败。",e);
		}finally {
            if (null!=snmp){
                try {
                    snmp.close();
                } catch (IOException e) {
                    ZNMSLogger.error("getApInfo",e);
                }
            }
        }
    	return list;
    }
    
    
    /**
	 * 获取对应的v2v1版本的oid对应的值
	 * @param oid
	 * @param address
	 */
	public static boolean availabilityMethodV2V1(ScanHost scanHost) {
		Snmp snmp = null ;
		boolean flag=false;
		try {
			TransportMapping transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			snmp.listen();
			String port = SnmpConstants.SNMP_DEFAULT_PORT;
            if (StringUtils.isNotEmpty(scanHost.getSnmpPort())){
                port = scanHost.getSnmpPort();
            }
			// pdu
			PDU pdu = new PDU();
			//.1.3.6.1.2.1.1.1.0  获取系统基本信息  SysDesc  GET
			pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.1.0")));
			pdu.setType(PDU.GET);
			// target
			CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(SnmpConstants.SNMP_DEFAULT_COMMUNITY));
            if(StringUtils.isNotEmpty(scanHost.getCommunity())){
                target.setCommunity(new OctetString(scanHost.getCommunity()));
            }
            Address address = GenericAddress.parse("udp:"+scanHost.getIp()+"/"+port);
			target.setAddress(address);
			target.setTimeout(SnmpConstants.SNMP_TIMEOUT);
			target.setRetries(SnmpConstants.SNMP_RETRIES);
			if (StringUtils.equals(SnmpConstants.SNMP_VERSION_2,scanHost.getSnmpVersion())) {
				target.setVersion(org.snmp4j.mp.SnmpConstants.version2c);
			} else if (StringUtils.equals(SnmpConstants.SNMP_VERSION_1,scanHost.getSnmpVersion())) {
				target.setVersion(org.snmp4j.mp.SnmpConstants.version1);
			}
			ResponseEvent response = snmp.send(pdu, target);
			if(response.getResponse()==null){
				return flag;
			}else{
				flag= true;
			}
		} catch (Exception e) {
            //发生异常时，按正常处理
            flag = true;
            ZNMSLogger.error("主机["+scanHost+"]，获取可达信息（V1/V2）失败。",e);
		}finally {
            if (null!=snmp){
                try {
                    snmp.close();
                } catch (IOException e) {
                    ZNMSLogger.error("availabilityMethodV2V1",e);
                }
            }
        }
		return flag;
	}
	
	public static boolean availabilityMethodV3(ScanHost scanHost) throws IOException {
		TransportMapping transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
		boolean flag=false;
		try{
			String userName=scanHost.getSnmpUserName();
			String password=scanHost.getSnmpPassword();
			String authProtocol= scanHost.getSnmpAuthProtocol();
			if(authProtocol.equalsIgnoreCase("MD5")){
				UsmUser user = new UsmUser(  
						new OctetString(userName),  
						AuthMD5.ID, new OctetString(password),  
						PrivDES.ID, new OctetString(password));  
				snmp.getUSM().addUser(new OctetString(userName), user);  
			}else if(authProtocol.equalsIgnoreCase("SHA")){
				UsmUser user = new UsmUser(  
						new OctetString(userName),  
						AuthSHA.ID, new OctetString(password),  
						PrivDES.ID, new OctetString(password));  
				snmp.getUSM().addUser(new OctetString(userName), user); 
			}
			String port = SnmpConstants.SNMP_DEFAULT_PORT;
            if (StringUtils.isNotEmpty(scanHost.getSnmpPort())){
                port = scanHost.getSnmpPort();
            }
			 UserTarget target = new UserTarget();  
			 target.setVersion(org.snmp4j.mp.SnmpConstants.version3);  
			 Address targetAddress = GenericAddress.parse("udp:"+scanHost.getIp()+"/"+port);
			 target.setAddress(targetAddress);  
			 target.setSecurityLevel(SecurityLevel.AUTH_PRIV);  
			 target.setSecurityName(new OctetString(userName));  
			 target.setTimeout(SnmpConstants.SNMP_TIMEOUT);  
			 target.setRetries(SnmpConstants.SNMP_RETRIES);  
			           
			 ScopedPDU pdu = new ScopedPDU();  
			 pdu.setType(PDU.GET);  
			 
			 for (ScanItem scanItem:scanHost.getScanItems()){
			     pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.1.0")));
			 }
			 ResponseEvent responseEvent = snmp.send(pdu, target);  
			 //同步
            if (null!=responseEvent){
            	flag=true;
            }
		}catch(Exception e){
            ZNMSLogger.error("主机["+scanHost+"]，获取可达信息（V3）失败。",e);
        }finally {
            if (null!=snmp){
                try {
                    snmp.close();
                } catch (IOException e) {
                    ZNMSLogger.error("availabilityMethodV3:",e);
                }
            }
        }
		 return flag;
	}
}
