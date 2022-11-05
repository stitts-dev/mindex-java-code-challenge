package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation comp) throws Exception{
        LOG.debug("Received Compensation create request for [{}]", comp);
        return compensationService.create(comp);
    }

    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) throws Exception{
        LOG.debug("The compensation for [{}] requested", id);
        return compensationService.read(id);
    }

    @GetMapping("/compensation")
    public List<Compensation> read() {
        LOG.debug("Getting the read request");
        return compensationService.read();
    }
}