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

    @Column(updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdDate;

    @PrePersist //Tự động gán ngày hiện tại khi tạo mới một bản ghi.
    protected void onCreate() {
        this.createdDate = LocalDate.now(); // Gán ngày hiện tại theo định dạng năm - tháng ngày
    }


}
