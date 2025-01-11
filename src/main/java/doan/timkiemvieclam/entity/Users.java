package doan.timkiemvieclam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer userId;

    private String userName;

    private String userAddress;

    private String userEmail;

    private String userAvatar;

    private String userPhone;

    private String description;

    @OneToOne
    @JoinColumn(name = "accountId")
    private Accounts account;
}
