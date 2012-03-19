package com.wallofshame.controller;


import com.wallofshame.domain.Credential;
import com.wallofshame.domain.PeopleMissingTimeSheet;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShameController {


    @RequestMapping(value = "/{country}.html", method = RequestMethod.GET)
    public String index(Model model, @PathVariable String country) {
        Map<String,List<String>> names = PeopleMissingTimeSheet.getInstance().names();
        model.addAttribute("names", names.get(country));
        model.addAttribute("country", country);
        return "index";

    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String postCredential(HttpServletRequest request, Model model) {
        String username = StringUtils.trimToEmpty(request.getParameter("username"));
        String password = StringUtils.trimToEmpty(request.getParameter("password"));
        
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            model.addAttribute("error","User name and password required.");
            return "login";
        }

        Credential.getInstance().save(username,password);
        return "redirect:/China.html";
    }
//    @RequestMapping(value = Array("/login.html"), method = Array(RequestMethod.POST))
//    def save(request:HttpServletRequest){
//        Credential.save(request.getParameter("username"), request.getParameter("password"))
//        "redirect:/wallofshame/china"
//    }

//    @RequestMapping(value = Array("/login.html"), method = Array(RequestMethod.GET))
//    def login = {
//            new ModelAndView("login")
//    }
//
//    @RequestMapping(value = Array("/login.html"), method = Array(RequestMethod.POST))
//    def save(request:HttpServletRequest){
//        Credential.save(request.getParameter("username"), request.getParameter("password"))
//        "redirect:/wallofshame/china"
//    }

}
