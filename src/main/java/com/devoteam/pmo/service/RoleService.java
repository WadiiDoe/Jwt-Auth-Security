package com.devoteam.pmo.service;

import com.devoteam.pmo.entity.Role;
import com.devoteam.pmo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);

    }
}
