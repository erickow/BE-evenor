package com.ericko.evenor.service.role;

import com.ericko.evenor.entity.Role;
import com.ericko.evenor.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getRole() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Role getRole(String id) {
        return roleRepository.findOne(id);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(String id) {
        roleRepository.delete(id);
    }
}
