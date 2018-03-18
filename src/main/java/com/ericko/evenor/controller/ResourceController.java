package com.ericko.evenor.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="user", method=RequestMethod.GET)
    public String helloUser() {
        return "hello user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="admin", method=RequestMethod.GET)
    public String helloAdmin() {
        return "hello admin";
    }

    @PreAuthorize("hasRole('CLIENT')")
    @RequestMapping(value="client", method=RequestMethod.GET)
    public String helloClient() {
        return "hello user authenticated by normal client";
    }

    @PreAuthorize("hasRole('ROLE_TRUSTED_CLIENT')")
    @RequestMapping(value="trusted_client", method=RequestMethod.GET)
    public String helloTrustedClient() {
        return "hello user authenticated by trusted client";
    }

    @RequestMapping(value="principal", method=RequestMethod.GET)
    public Object getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }

    @RequestMapping(value="roles", method=RequestMethod.GET)
    public Object getRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

}