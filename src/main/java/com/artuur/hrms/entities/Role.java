package com.artuur.hrms.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(unique = true, nullable = false)
    private String name;

    public enum Values {

        ROLE_ADMIN(1L),
        ROLE_MANAGER(2L),
        ROLE_EMPLOYEE(3L);

        long roleId;

        Values(long roleId){
            this.roleId = roleId;
        }

        public long getRoleId() {
            return roleId;
        }
    }
}
