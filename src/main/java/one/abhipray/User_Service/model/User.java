package one.abhipray.User_Service.model;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_email",nullable = false, unique = true)
    private String email;

    @Column(name="user_contact",unique = true)
    private String contact;

    @Column(name="user_name",nullable = false)
    private String username;

    @Column(name="user_password",nullable = false)
    private String password;

    @Column(name = "profile_picture",unique = true)
    private String profilePicture;

    @Column(name = "bio")
    private String bio;

    @Column(name = "oauth_provider")
    private String oauthProvider;

    @Column(name = "oauth_provider_id")
    private String oauthProviderId;

    @Column(name = "verified", nullable = false)
    private boolean verified = false;
}