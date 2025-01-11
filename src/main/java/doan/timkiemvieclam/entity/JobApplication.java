package doan.timkiemvieclam.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer applicationId;

    @ManyToOne
    @JoinColumn(name = "cvId")
    private CurriculumVitae CV;

    @ManyToOne
    @JoinColumn(name = "jobId")
    private Jobs jobs;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Accounts account;

    private LocalDate applicationDate;

}
