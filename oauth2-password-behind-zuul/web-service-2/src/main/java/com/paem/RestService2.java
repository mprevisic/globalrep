package com.paem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestService2 {

    public static void main(String[] args) {
        SpringApplication.run(RestService2.class, args);
    }

    @RestController
    @RequestMapping("/")
    public static class Api {


        @GetMapping(path = "/insecure")
        public String insecure(Principal p) {
            return String.format("Insecure Hello %s from Web Service 2", p.getName());
        }

        @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')")
        @GetMapping(path = "/secure")
        public String secure(Principal p) {
            return String.format("Secure Hello %s from Web Service 2", p.getName());
        }

    }

}