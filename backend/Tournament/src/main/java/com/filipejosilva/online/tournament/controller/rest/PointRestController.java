package com.filipejosilva.online.tournament.controller.rest;

import com.filipejosilva.online.tournament.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/point")
public class PointRestController {

    /* This controller may not be needed */
    private PointService pointService;

    @Autowired
    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }
}
