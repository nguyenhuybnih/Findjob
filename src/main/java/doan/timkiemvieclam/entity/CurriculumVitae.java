package doan.timkiemvieclam.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumVitae {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer cvId;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Accounts account;

    private String fullName;

    private String address;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String skill;

    private String careerObjective;

    private String education;

    private String project;

}
