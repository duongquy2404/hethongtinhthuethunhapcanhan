package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.model.entity.Role;
import com.btl.sqa_n1_5.repository.RoleRepository;
import com.btl.sqa_n1_5.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role findByName(String name) {
    Role role = roleRepository.findRoleByName(name);
    return role;
  }
}
