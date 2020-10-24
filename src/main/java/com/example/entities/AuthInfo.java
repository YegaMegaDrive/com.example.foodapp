package com.example.entities;

import com.example.to.RoleEnum;
import com.example.to.StateEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "authorization")
public class AuthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
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
    private StateEnum state;



}
