package com.alex.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 19.10.2016.
 */
@RestController
public class WebController {
    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public void method() {
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public void method2() {
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public void method3() {
    }

    @RequestMapping(value = "/user/name", method = RequestMethod.PATCH)
    public void method4() {
    }

    @RequestMapping(value = "/user/score", method = RequestMethod.PATCH)
    public void method5() {
    }
}
