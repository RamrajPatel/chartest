package com.college.snmp;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * Created type abhi on 14/4/15.
 */
@Controller
public class IndexController {
    /**
     * Welcome.
     *
     * @return the string
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String welcome() {
        System.out.println("Came here");
        return "login";
    }

    @RequestMapping(value = "settings", method = RequestMethod.GET)
    public String settings(){
        return "settings";
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping(value="checkAjax", method = RequestMethod.POST,headers = {"Content-type=application/json"})
    public @ResponseBody String checkAjaxMethod( @RequestBody JsonResponse response) {
        System.out.println("receivejhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        String ip = response.getIp();
        String community = response.getCommunity();
        System.out.println(ip + community);
        return ip+community;
    }

    @RequestMapping(value = "SNMP_UI", method = RequestMethod.GET)
    public String SNMP_UI(){
        return "SNMP_UI";
    }

    @RequestMapping(value = "dataTables", method = RequestMethod.GET)
    public String dataTables(){
        return "dataTables";
    }

    @RequestMapping("/hello")
    public @ResponseBody
    String hello (@RequestParam(value = "ip") String ip,
                 @RequestParam(value = "community") String community,
                 @RequestParam(value = "mapName") String mapName){
        System.out.println(ip);
        System.out.println(community);
        System.out.println(mapName);
        JSONObject json = null;

        try {
            SnmpUtility client = new SnmpUtility(ip, community);
            Map<String, String> snmpDataMap = client.snmpWalk(".");
            SnmpMapClassifier classifier = new SnmpMapClassifier();
            Map<String, Map<String, String>> snmpData = classifier.traverseOid(snmpDataMap, mapName);

            json = new JSONObject();
            json.putAll(snmpData);
            System.out.println(json.toString());
        }
        catch (Exception e)
        {
            System.out.println(" EXCEPTION IN RESPONSE BODY ( wrong ip ) : "+ e );
        }

        finally{return json.toString() ;}

    }

    @RequestMapping(value = "administration", method = RequestMethod.GET)
    public String administration(@RequestParam String type){
        return type;
    }



}
