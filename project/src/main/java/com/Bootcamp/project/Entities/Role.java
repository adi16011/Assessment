package com.Bootcamp.project.Entities;

import com.Bootcamp.project.Auditing.Auditable;
import com.Bootcamp.project.Enumerators.RoleEnum;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;


@Entity
@EnableJpaAuditing
public class Role extends Auditable<String> implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Long id;


    @Enumerated(EnumType.STRING)
    private RoleEnum authority;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthority(RoleEnum authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {

        return String.valueOf(authority);
    }
}
