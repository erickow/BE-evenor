package com.ericko.evenor.service.role;

import com.ericko.evenor.entity.Role;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
public interface RoleService {

    List<Role> getRole();

    Role getRole(UUID id);

    Role createRole(@Valid Role role);

    Role updateRole(@Valid Role role);

    void deleteRole(UUID id);
}
