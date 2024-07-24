package one.abhipray.User_Service.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateRequest {
    private String email;
    private String contact;
    private String username;
    private String bio;
}
