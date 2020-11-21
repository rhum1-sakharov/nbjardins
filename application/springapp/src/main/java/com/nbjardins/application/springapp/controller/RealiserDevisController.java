package com.nbjardins.application.springapp.controller;

import com.nbjardins.application.springapp.service.RealiserDevisService;
import com.nbjardins.domain.entities.Mail;
import com.nbjardins.domain.entityresponse.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/realiser-devis")
public class RealiserDevisController {


    RealiserDevisService realiserDevisService;

    public RealiserDevisController(RealiserDevisService realiserDevisService) {
        this.realiserDevisService = realiserDevisService;
    }


    @PostMapping(value = "/demander-devis")
    public Response<Mail> demanderDevis(@RequestParam("fromEmail") String fromEmail, @RequestParam("textEmail") String textEmail, @RequestParam("titleMail") String titleMail) {
        return realiserDevisService.demanderDevis(fromEmail,textEmail,titleMail);
    }


}
