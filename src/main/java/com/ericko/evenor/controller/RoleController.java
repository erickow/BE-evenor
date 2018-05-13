package com.ericko.evenor.controller;

import com.ericko.evenor.entity.Role;
import com.ericko.evenor.service.role.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static com.ericko.evenor.util.response.ResponseHandler.checkResourceFound;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get all role", notes = "the all role will be get")
    public @ResponseBody
    List<Role> getRole(
            HttpServletRequest request, HttpServletResponse response
    ) {
        List<Role> result = roleService.getRole();
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "role id", notes = "getting role by id")
    public @ResponseBody
    Role getRole(
            @ApiParam(value = "role id")
            @PathVariable("id") UUID id,
            HttpServletRequest request, HttpServletResponse response
    ){
        Role result = roleService.getRole(id);
        checkResourceFound(result);
        return result;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "role object", notes = "create a new role")
    public @ResponseBody
    Role createRole (
            @RequestBody Role role,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Role result = roleService.createRole(role);
        return result;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "role object", notes = "updating the existing role")
    public @ResponseBody
    Role updateRole (
            @ApiParam(value = "the role id")
            @PathVariable("id") String id,
            @RequestBody Role role,
            HttpServletRequest request, HttpServletResponse response
    ){

        return roleService.updateRole(role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "role object", notes = "delete existing user")
    public @ResponseBody
    void deleteRole(
            @ApiParam(value = "user id and object")
            @PathVariable("id") UUID id
    ) {
        roleService.deleteRole(id);
    }

}
