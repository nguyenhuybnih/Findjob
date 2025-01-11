package doan.timkiemvieclam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employersq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer employerId;

    @Column(length = 200)
    private String employerName;

    private String employerAddress;

    private String employerAvatar;

    private String employerWebsite;

    private String description;

    private String quantity;

    private Boolean isActive; // Hoặc sử dụng Boolean và xử lý chuyển đổi trong mã
    @OneToOne
    @JoinColumn(name = "accountId")
    private Accounts account;
}
