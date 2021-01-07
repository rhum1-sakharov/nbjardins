package org.rlsv.adapters.primaries.application.springapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    @GetMapping(value = "/connect")
    public void connect(@RequestParam("code") String code, @RequestParam("scope") String scope ) throws Exception {

        System.out.println(code);
        System.out.println(scope);

    }

}
