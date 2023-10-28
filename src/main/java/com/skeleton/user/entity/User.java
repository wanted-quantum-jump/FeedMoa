package com.skeleton.user.entity;

import com.skeleton.user.enums.UserVerification;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    private String account;

    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserVerification userVerification;

    @Builder
    public User(String account, String email, String password, UserVerification userVerification) {
        this.account = account;
        this.email = email;
        this.password = password;
        this.userVerification = UserVerification.UNRESOLVED;
    }


    public void updateVerificationStatus() {
        this.userVerification = UserVerification.RESOLVED;
    }
}
