package org.rlsv.adapters.primaries.application.springapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artisans")
public class ArtisanController {

    private static final Logger LOG = LoggerFactory.getLogger(ArtisanController.class);

    @GetMapping
    public void test()  {

        LOG.info("in artisan");
    }


}
