package one.abhipray.User_Service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name="body_stats")
@Data
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="date", nullable = false)
    private LocalDate date;

    @Column(name="weight")
    private Double weight;

    @Column(name="body_fat_percentage")
    private Double bodyFatPercentage;

    @Column(name="muscle_mass")
    private Double muscleMass;

    @Column(name="water_percentage")
    private Double waterPercentage;

    @Column(name="bmi")
    private Double bmi;

    @Column(name="gender")
    private String gender;

    @Column(name="age")
    private Integer age;

    @Column(name="ethnicity")
    private String ethnicity;

    @Column(name="height")
    private Double height;

    @Column(name="resting_heart_rate")
    private Integer restingHeartRate;

    @Column(name="active_minutes")
    private Integer activeMinutes;

    @Column(name="sleep_hours")
    private Double sleepHours;

    @Column(name="blood_pressure_systolic")
    private Integer bloodPressureSystolic;

    @Column(name="blood_pressure_diastolic")
    private Integer bloodPressureDiastolic;

    @Column(name="cholesterol_level")
    private Integer cholesterolLevel;

    @Column(name="blood_sugar_level")
    private Integer bloodSugarLevel;

    @Column(name="vo2_max")
    private Double vo2Max;

    // Foreign key to link to the User entity
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}