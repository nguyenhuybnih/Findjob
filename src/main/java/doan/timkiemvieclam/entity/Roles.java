package doan.timkiemvieclam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer roleId;

    private String  roleName;

    private Boolean isActive;

}
