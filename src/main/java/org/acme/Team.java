package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Team extends PanacheEntity {
    public String name;

//    @OneToMany
//    public List<AppUser> users;
}
