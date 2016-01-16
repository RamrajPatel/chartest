package com.college.snmp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


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

    @RequestMapping(value = "administration", method = RequestMethod.GET)
    public String administration(@RequestParam String type){
        return type;
    }

}
