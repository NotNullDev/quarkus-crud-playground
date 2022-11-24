package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class AppUser extends PanacheEntity {
    public String name;
    public String surname;

//    @ManyToOne
//    public Team team;
}
