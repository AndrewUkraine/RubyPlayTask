package com.NightClubsAndTheirVisitors.project.entities;

import com.NightClubsAndTheirVisitors.project.entities.enums.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "user_name", length = 30, nullable = false)
    @NotBlank(message = "Must by not empty")
    private String username;

    @Column(name = "first_visit", updatable = false)
    private Date created;

    public User() {
    }

    @Column(name = "password", nullable = false, length = 100)
    @NotBlank (message = "Must by not empty")
    private String password;

    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles;

    public Set<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleType> roles) {
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinTable(name = "club_user",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName ="id")},
            inverseJoinColumns = {@JoinColumn(name = "club_id", referencedColumnName ="id")})
    private List<Club> club;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Club> getClub() {
        return club;
    }

    public void setClub(List<Club> club) {
        this.club = club;
    }
}
