package one.abhipray.User_Service.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {
    private String email;
    private String contact;
    private String name;
    private String password;

    public SignupRequest(String email, String contact, String name, String password) {
        this.email = email;
        this.contact = contact;
        this.name = name;
        this.password = password;
    };
}
