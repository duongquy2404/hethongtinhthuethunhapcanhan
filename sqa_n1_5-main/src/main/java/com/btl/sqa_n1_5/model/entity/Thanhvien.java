package com.btl.sqa_n1_5.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Thanhvien {
  @Id
  private Integer id;
  private String username;
  private String password;
  private String email;
  private String dienthoai;
  private String diachi;

  @ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
  @JoinTable(
      name="users_roles",
      joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
      inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
  private List<Role> roles = new ArrayList<>();

  public Thanhvien(Integer id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }
}
