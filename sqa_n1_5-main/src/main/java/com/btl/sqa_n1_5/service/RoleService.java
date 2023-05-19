package com.btl.sqa_n1_5.service;


import com.btl.sqa_n1_5.model.entity.Role;

public interface RoleService {
  Role findByName(String name);
}
