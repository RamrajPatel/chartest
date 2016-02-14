package com.college.snmp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 1/20/2016.
 */
public class SnmpMapClassifier {

    private Map<String, Map<String, String>> runningProcessMap = new HashMap<>();
    private Map<String, Map<String, String>> SWInstalledMap = new HashMap<>();
    private Map<String, Map<String, String>> storageMap = new HashMap<>();
    private Map<String, Map<String, String>> deviceMap = new HashMap<>();
    private Map<String, Map<String, String>> interfaceMap = new HashMap<>();
    private Map<String,String> systemMap = new HashMap<>();

    public  Map<String, Map<String, String>> traverseOid(Map<String, String> snmpWalkData, String choice){
        for (String oid : snmpWalkData.keySet()) {

           if(choice.equalsIgnoreCase("ProcessMap")){
                if (checkIfProcessInfo(oid))
                getRunningProcessMap(snmpWalkData, oid);
               }
           else if(choice.equalsIgnoreCase("SWInstalledMap")) {
                if (checkSWInfo(oid))
                    getSWInstalledMap(snmpWalkData, oid);
                }
           else if(choice.equalsIgnoreCase("storageMap")) {
               if (checkStorageInfo(oid))
                   getStorageMap(snmpWalkData, oid);
           }

           else if(choice.equalsIgnoreCase("deviceMap")) {
                if (checkHrDeviceDescrInfo(oid))
                    getDeviceMap(snmpWalkData, oid);
                }

           else if(choice.equalsIgnoreCase("interfaceMap")) {
                if (checkInterfaceInfo(oid))
                    getInterfaceMap(snmpWalkData, oid);
                }
        }

        if(choice.equalsIgnoreCase("ProcessMap"))
            return runningProcessMap;

        else if(choice.equalsIgnoreCase("SWInstalledMap"))
            return SWInstalledMap;

        else if(choice.equalsIgnoreCase("storageMap")) {

            for (String softwareId : storageMap.keySet()) {

                Map<String, String> storageInfoFilterMap = storageMap.get(softwareId);

                long units = Long.parseLong(storageInfoFilterMap.get("allocation units"));
                long size = Long.parseLong(storageInfoFilterMap.get("size"));
                long used = Long.parseLong(storageInfoFilterMap.get("used"));
                long free = (size - used) * units;
                size = size * units;
                used = used * units;
                storageInfoFilterMap.put("size", String.valueOf(size));
                storageInfoFilterMap.put("allocation units", String.valueOf(units));
                storageInfoFilterMap.put("free", String.valueOf(free));
                storageInfoFilterMap.put("used", String.valueOf(used));
            }
            return storageMap;
        }

        else if(choice.equalsIgnoreCase("deviceMap"))
            return deviceMap;

        else if(choice.equalsIgnoreCase("interfaceMap"))
            return interfaceMap;

        return null ;
    }

    public void getRunningProcessMap(Map<String, String> snmpWalkData, String oid) {

        Map<String, String> processInfoMap;

            String id[] = oid.split("\\.");
            String processId = id[id.length - 1];

            if (!runningProcessMap.containsKey(processId)) {
                processInfoMap = new HashMap<>();
                runningProcessMap.put(processId, processInfoMap);
            } else {
                processInfoMap = runningProcessMap.get(processId);
            }

            if (oid.startsWith("1.3.6.1.2.1.25.4.2.1.2")) {
                processInfoMap.put("name", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.4.2.1.4")) {
                processInfoMap.put("runPath", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.4.2.1.5")) {
                processInfoMap.put("runParameters", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.4.2.1.6")) {

                String runType = snmpWalkData.get(oid);

                if(runType.equals("1"))
                        runType = "Unknown";
                else if (runType.equals("2"))
                runType = "Operating Sysytem";
                else if (runType.equals("3"))
                    runType = "Device Driver";
                else if (runType.equals("4"))
                    runType = "Application";
                processInfoMap.put("runType", runType);

            } else if (oid.startsWith("1.3.6.1.2.1.25.4.2.1.7")) {

                String runStatus = snmpWalkData.get(oid);

                if(runStatus.equals("1"))
                    runStatus = "Running";
                else if (runStatus.equals("2"))
                    runStatus = "Runnable";
                else if (runStatus.equals("3"))
                    runStatus = "Not Runnable ";
                else if (runStatus.equals("4"))
                    runStatus = "Invalid";

                processInfoMap.put("runStatus", runStatus);
            } else if (oid.startsWith("1.3.6.1.2.1.25.5.1.1.1")) {
                processInfoMap.put("cpuPerformance", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.5.1.1.2")) {
                processInfoMap.put("memory", snmpWalkData.get(oid));
            }
    }

    private boolean checkIfProcessInfo(String oid) {
        return oid.startsWith("1.3.6.1.2.1.25.4.2.1.2") || oid.startsWith("1.3.6.1.2.1.25.4.2.1.4")
                || oid.startsWith("1.3.6.1.2.1.25.4.2.1.5") || oid.startsWith("1.3.6.1.2.1.25.4.2.1.6")
                || oid.startsWith("1.3.6.1.2.1.25.4.2.1.7") || oid.startsWith("1.3.6.1.2.1.25.5.1.1.1")
                || oid.startsWith("1.3.6.1.2.1.25.5.1.1.2");
    }

    public void getSWInstalledMap(Map<String, String> snmpWalkData, String oid) {

        Map<String, String> SWInfoMap;


            String id[] = oid.split("\\.");
            String softwareId = id[id.length - 1];

            if (!SWInstalledMap.containsKey(softwareId)) {
                SWInfoMap = new HashMap<>();
                SWInstalledMap.put(softwareId, SWInfoMap);
            } else {
                SWInfoMap = SWInstalledMap.get(softwareId);
            }

            if (oid.startsWith("1.3.6.1.2.1.25.6.3.1.2")) {
                SWInfoMap.put("swInstalledName", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.6.3.1.5")) {
                SWInfoMap.put("swInstalledDate", DateConvertHex.convertDecimal(snmpWalkData.get(oid)));
            }

    }

    private boolean checkSWInfo(String oid) {
        return oid.startsWith("1.3.6.1.2.1.25.6.3.1.2") || oid.startsWith("1.3.6.1.2.1.25.6.3.1.5");
    }

    public void getStorageMap(Map<String, String> snmpWalkData, String oid) {

        Map<String, String> storageInfoMap;

            String id[] = oid.split("\\.");
            String storageId = id[id.length - 1];

            if (!storageMap.containsKey(storageId)) {
                storageInfoMap = new HashMap<>();
                storageMap.put(storageId, storageInfoMap);
            } else {
                storageInfoMap = storageMap.get(storageId);
            }

            if (oid.startsWith("1.3.6.1.2.1.25.2.3.1.2")) {
                String type = snmpWalkData.get(oid);
                type = (String.valueOf(type.charAt(type.length() - 1)));
                storageInfoMap.put("type", type);
            } else if (oid.startsWith("1.3.6.1.2.1.25.2.3.1.3")) {
                storageInfoMap.put("description", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.2.3.1.4")) {
                storageInfoMap.put("allocation units", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.2.3.1.5")) {
                storageInfoMap.put("size", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.2.3.1.6")) {
                storageInfoMap.put("used", snmpWalkData.get(oid));
            }
    }

    private boolean checkStorageInfo(String oid) {
        return oid.startsWith("1.3.6.1.2.1.25.2.3.1.2") || oid.startsWith("1.3.6.1.2.1.25.2.3.1.3")
                || oid.startsWith("1.3.6.1.2.1.25.2.3.1.4") || oid.startsWith("1.3.6.1.2.1.25.2.3.1.5")
                || oid.startsWith("1.3.6.1.2.1.25.2.3.1.6");
    }

    public void getDeviceMap(Map<String, String> snmpWalkData, String oid) {

        Map<String, String> hrDeviceInfoMap;

            String id[] = oid.split("\\.");
            String deviceId = id[id.length - 1];

            if (!deviceMap.containsKey(deviceId)) {
                hrDeviceInfoMap = new HashMap<>();
                deviceMap.put(deviceId, hrDeviceInfoMap);
            } else {
                hrDeviceInfoMap = deviceMap.get(deviceId);
            }

            if (oid.startsWith("1.3.6.1.2.1.25.3.2.1.3")) {
                hrDeviceInfoMap.put("hrDeviceDescr", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.3.2.1.5")) {
                hrDeviceInfoMap.put("hrDeviceStatus", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.25.3.2.1.6")) {
                hrDeviceInfoMap.put("hrDeviceErrors", snmpWalkData.get(oid));
            }
    }

    private boolean checkHrDeviceDescrInfo(String oid) {
        return oid.startsWith("1.3.6.1.2.1.25.3.2.1.3") || oid.startsWith("1.3.6.1.2.1.25.3.2.1.5") || oid.startsWith("1.3.6.1.2.1.25.3.2.1.6");
    }

    public void getInterfaceMap(Map<String, String> snmpWalkData, String oid) {

        Map<String, String> interfaceInfoMap;

            String id[] = oid.split("\\.");
            String intrfaceId = id[id.length - 1];

            if (!interfaceMap.containsKey(intrfaceId)) {
                interfaceInfoMap = new HashMap<>();
                interfaceMap.put(intrfaceId, interfaceInfoMap);
            } else {
                interfaceInfoMap = interfaceMap.get(intrfaceId);
            }

            if (oid.startsWith("1.3.6.1.2.1.2.2.1.2")) {
                interfaceInfoMap.put("ifDescr", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.2.2.1.3")) {
                interfaceInfoMap.put("ifType", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.2.2.1.4")) {
                interfaceInfoMap.put("ifMtu", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.2.2.1.5")) {
                interfaceInfoMap.put("ifSpeed", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.2.2.1.6")) {
                interfaceInfoMap.put("ifPhysAddress", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.2.2.1.7")) {
                interfaceInfoMap.put("ifAdminStatus", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.2.2.1.8")) {
                interfaceInfoMap.put("ifOperStatus", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.2.2.1.9")) {
                interfaceInfoMap.put("ifLastChange", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.2.2.1.10")) {
                interfaceInfoMap.put("ifInOctets", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.2.2.1.16")) {
                interfaceInfoMap.put("ifOutOctets", snmpWalkData.get(oid));
            }

    }

    private boolean checkInterfaceInfo(String oid) {
        return oid.startsWith("1.3.6.1.2.1.2.2.1.2") ||oid.startsWith("1.3.6.1.2.1.2.2.1.3") ||
                oid.startsWith("1.3.6.1.2.1.2.2.1.4") || oid.startsWith("1.3.6.1.2.1.2.2.1.5") ||
                oid.startsWith("1.3.6.1.2.1.2.2.1.6") ||oid.startsWith("1.3.6.1.2.1.2.2.1.7")||
                oid.startsWith("1.3.6.1.2.1.2.2.1.8") ||oid.startsWith("1.3.6.1.2.1.2.2.1.9")||
                oid.startsWith("1.3.6.1.2.1.2.2.1.10") ||oid.startsWith("1.3.6.1.2.1.2.2.1.16");
    }

    public Map<String, String> getSystemMap(Map<String, String> snmpWalkData) {

        for (String oid : snmpWalkData.keySet()) {

            if (!checkSystemInfo(oid)) {
                continue;
            }

            if (oid.equals("1.3.6.1.2.1.1.1.0")) {
                systemMap.put("sysDescr", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.1.2.0")) {
                systemMap.put("sysObjectID", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.1.3.0")) {
                systemMap.put("sysUpTimeInstance", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.1.4.0")) {
                systemMap.put("sysContact", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.1.5.0")) {
                systemMap.put("sysName", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.1.6.0")) {
                systemMap.put("sysLocation", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.1.7.0")) {
                systemMap.put("sysServices", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.2.1.0")) {
                systemMap.put("ifNumber", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.4.1.0")) {
                systemMap.put("ipForwarding", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.4.2.0")) {
                systemMap.put("ipDefaultTTL", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.4.3.0")) {
                systemMap.put("ipInReceives", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.4.8.0")) {
                systemMap.put("ipInDiscards", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.4.9.0")) {
                systemMap.put("ipInDelivers", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.4.10.0")) {
                systemMap.put("ipOutRequests", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.4.11.0")) {
                systemMap.put("ipOutDiscards", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.4.12.0")) {
                systemMap.put("ipOutNoRoutes", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.4.20.1.1") && (oid.equals("1.3.6.1.2.1.4.20.1.1.127.0.0.1")== false)) {
                systemMap.put("ipAdEntAddr", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.4.20.1.2") && (oid.equals("1.3.6.1.2.1.4.20.1.2.127.0.0.1")== false)) {
                systemMap.put("ipAdEntIfIndex", snmpWalkData.get(oid));
            } else if (oid.startsWith("1.3.6.1.2.1.4.20.1.3") && (oid.equals("1.3.6.1.2.1.4.20.1.3.127.0.0.1")== false)) {
                systemMap.put("ipAdEntNetMask", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.4.21.1.8.0.0.0.0 ")) {
                systemMap.put("ipRouteType", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.25.1.1.0")) {
                systemMap.put("hrSystemUptime", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.25.1.2.0 ")) {
                systemMap.put("hrSystemDate", DateConvertHex.convertDecimal(snmpWalkData.get(oid)));
            } else if (oid.equals("1.3.6.1.2.1.25.1.5.0")) {
                systemMap.put("hrSystemNumUsers", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.25.1.6.0")) {
                systemMap.put("hrSystemProcesses", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.25.2.2.0")) {
                systemMap.put("hrMemorySize", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.25.3.5.1.1.1")) {
                systemMap.put("hrPrinterStatus", snmpWalkData.get(oid));
            } else if (oid.equals("1.3.6.1.2.1.25.6.1.0")) {
                systemMap.put("hrSWInstalledLastChange", snmpWalkData.get(oid));
            }
        }
        return systemMap;
    }

    private boolean checkSystemInfo(String oid) {
        return oid.startsWith("1.3.6.1.2.1.1.1.0") ||  oid.startsWith("1.3.6.1.2.1.1.2.0") ||
                oid.startsWith("1.3.6.1.2.1.1.3.0") ||  oid.startsWith("1.3.6.1.2.1.1.4.0") ||
                oid.startsWith("1.3.6.1.2.1.1.5.0") ||  oid.startsWith("1.3.6.1.2.1.1.6.0") ||
                oid.startsWith("1.3.6.1.2.1.1.7.0") ||  oid.startsWith("1.3.6.1.2.1.2.1.0") ||
                oid.startsWith("1.3.6.1.2.1.4.1.0") ||  oid.startsWith("1.3.6.1.2.1.4.2.0") ||
                oid.startsWith("1.3.6.1.2.1.4.3.0") || oid.startsWith("1.3.6.1.2.1.4.8.0") ||
                oid.startsWith("1.3.6.1.2.1.4.9.0") || oid.startsWith("1.3.6.1.2.1.4.10.0") ||
                oid.startsWith("1.3.6.1.2.1.4.11.0") || oid.startsWith("1.3.6.1.2.1.4.12.0") ||
                oid.startsWith("1.3.6.1.2.1.4.20.1.1") && (!oid.equals("1.3.6.1.2.1.4.20.1.1.127.0.0.1")) ||
                oid.startsWith("1.3.6.1.2.1.4.20.1.2") && (!oid.equals("1.3.6.1.2.1.4.20.1.2.127.0.0.1")) ||
                oid.startsWith("1.3.6.1.2.1.4.20.1.3") && (!oid.equals("1.3.6.1.2.1.4.20.1.3.127.0.0.1")) ||
                oid.startsWith("1.3.6.1.2.1.4.21.1.8.0.0.0.0") || oid.startsWith("1.3.6.1.2.1.25.1.1.0") ||
                oid.startsWith("1.3.6.1.2.1.25.1.2.0") || oid.startsWith("1.3.6.1.2.1.25.1.5.0") ||
                oid.startsWith("1.3.6.1.2.1.25.1.6.0") || oid.startsWith("1.3.6.1.2.1.25.2.2.0") ||
                oid.startsWith("1.3.6.1.2.1.25.3.5.1.1.1") || oid.startsWith("1.3.6.1.2.1.25.6.1.0");
    }


    public static void main(String[] args) throws IOException {
        SnmpUtility client = new SnmpUtility("127.0.0.1", "public");
        Map<String, String> snmpDataMap = client.snmpWalk(".");
        SnmpMapClassifier classifier = new SnmpMapClassifier();
        Map<String, Map<String, String>> snmpData = classifier.traverseOid(snmpDataMap, "ProcessMap");

        if(snmpData != null)
            for (String key : snmpData.keySet()) {
                System.out.println(key + ":" + snmpData.get(key));
            }
        else{
            System.out.println("wrong choice");
        }

        Map<String, String> snmpSystemData = classifier.getSystemMap(snmpDataMap);
        for (String key : snmpSystemData.keySet()) {
            System.out.println(key + ":" + snmpSystemData.get(key));
        }
    }
}
