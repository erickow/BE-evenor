package com.ericko.evenor.service.role;

import com.ericko.evenor.entity.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface RoleService {

    List<Role> getRole();

    Role getRole(String id);

    Role createRole(@Valid Role role);

    Role updateRole(@Valid Role role);

    void deleteRole(String id);
}
