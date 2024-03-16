package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 필드 생성자
@ToString
@Table(name = "members")
public class Member {
    @Id
    @Column(name = "member_email", nullable = false)
    private String memberEmail;
    @Column(name = "member_password", nullable = false)
    private String memberPassword;
    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}
