package carsharing_system.car_sharing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "driver_licenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverLicenseStatus status;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}