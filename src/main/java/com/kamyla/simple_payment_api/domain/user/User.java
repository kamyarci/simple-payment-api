package com.kamyla.simple_payment_api.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamyla.simple_payment_api.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private BigDecimal balance; //saldo do usuario

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO user) {
        this.firstName = user.firstName();
        this.lastName = user.lastName();
        this.balance = user.balance();
        this.userType = user.userType();
        this.email = user.email();
        this.password = user.password();
        this.document = user.document();

    }

}
