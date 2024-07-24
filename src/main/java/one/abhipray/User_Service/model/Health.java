package one.abhipray.User_Service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="health_issues")
@Data
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="issue_name", nullable = false)
    private String issueName;

    @Column(name="description")
    private String description;

    @Column(name="severity")
    private String severity;

    @Column(name="status")
    private String status; // Consider using an enum (ACTIVE, RESOLVED)

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}