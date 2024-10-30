package doan.timkiemvieclam.entity;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer blogId;

    private String title;

    private String detail;

    private String image;

    private String description;

    @ManyToOne
    @JoinColumn(name = "accountId")

    private Accounts account;

    private Boolean isActive;



}
