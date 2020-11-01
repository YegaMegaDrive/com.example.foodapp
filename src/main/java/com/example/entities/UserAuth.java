package com.example.entities;

import com.example.to.enums.RoleEnum;
import com.example.to.enums.StatusEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "authorization")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;



}
