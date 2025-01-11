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

public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer jobId;

    private String jobName;

    @Lob
    private String jobDetails;

    private String jobAddress;

    private String salary;

    @ManyToOne
    @JoinColumn(name = "branchId")

    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "accountId")

    private Accounts account;

    private Boolean isActive;




}
