package com.college.snmp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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

    @RequestMapping(value="checkAjax", method = RequestMethod.POST)
    public @ResponseBody
    String post( @RequestBody JsonResponse response) {
        System.out.println("receivejhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        String ip = response.getIp();
        String community = response.getCommunity();
        System.out.println(ip + community);
        return ip+community;
    }



    @RequestMapping(value = "administration", method = RequestMethod.GET)
    public String administration(@RequestParam String type){
        return type;
    }
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

}
