package com.college.snmp;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SnmpUtility {

    Snmp snmp;
    String ip, community;

    public SnmpUtility(String ip, String community) throws IOException {

        this.ip = ip;
        this.community = community;

        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }

    public Map<String, String> snmpWalk(String startOid) throws IOException{

        String oid = startOid;
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        while(true) {
            PDU pdu = getVariableBinding(new OID(oid), PDU.GETNEXT);
            if (pdu == null || pdu.size() == 0) return null;

            VariableBinding var = pdu.get(0);
            if (var == null) return null;

            oid = var.getOid().toString();
            if ((var.getVariable().toString()).compareTo("endOfMibView")!=0) {
                map.put(oid, var.getVariable().toString());
            } else {
                break;
            }
        }
        return  map;
    }

    private PDU getVariableBinding(OID oid, int type) throws IOException {

        ResponseEvent event = get(new OID[] { oid }, type);

        if (event == null || event.getResponse() == null) {
            return null;
        }
        return event.getResponse();
    }

    private ResponseEvent get(OID oids[], int type) throws IOException {

        PDU pdu = new PDU();
        for (OID oi : oids) {
            pdu.add(new VariableBinding(oi));
        }
        pdu.setType(type);

        ResponseEvent event = snmp.send(pdu, getTarget(), null);
        if (event != null) {
            return event;
        }
        throw new RuntimeException("GET timed out");
    }

    private Target getTarget() {

        Address targetAddress = GenericAddress.parse("udp:" + ip + "/161");
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(3000);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    public static void main(String[] args) throws IOException {

        SnmpUtility client = new SnmpUtility("127.0.0.1", "public");
        Map<String, String> snmpDataMap = client.snmpWalk(".");
        for (String key : snmpDataMap.keySet()) {
            System.out.println(key + " = " + snmpDataMap.get(key));
        }
    }
}
