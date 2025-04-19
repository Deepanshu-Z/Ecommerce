package com.ecommerce.ecom.Model;

import jakarta.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING) //so that it doesnt store as Integer in DB
    private AppRole roleName;

    public AppRole getRoleName() {
        return roleName;
    }

    public void setRoleName(AppRole role) {
        this.roleName = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
