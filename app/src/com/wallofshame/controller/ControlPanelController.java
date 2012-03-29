package com.wallofshame.controller;

import com.wallofshame.domain.Credential;
import com.wallofshame.service.MailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Since: 3/27/12
 */
@Controller
public class ControlPanelController {

    private MailNotificationService mailNotificationService;

    @Autowired
    public ControlPanelController(MailNotificationService mailNotificationService) {
        this.mailNotificationService = mailNotificationService;
    }

    @RequestMapping(value = "/control.html",method = RequestMethod.GET)
    public String show(){
        if(Credential.getInstance().isEmpty())
            return "login";
        return "control";
    }
    

    @RequestMapping(value="/control.html",method = RequestMethod.POST)
    public String sendEmail(Model model){
          mailNotificationService.notifyMissingPeopleAsyn();
          model.addAttribute("info","Mails are sent!");
          return "control";
    }
}
