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
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer accountId;

    @Column(length = 200)
    private String accountName;

    private String password;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Roles role;

    private Boolean isActive; // Hoặc sử dụng Boolean và xử lý chuyển đổi trong mã


}
