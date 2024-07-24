package one.abhipray.User_Service.dto.user;

import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
public class ProfileResponse {
    private boolean error;
    private String responseCode;
    private Map<String, Object> body;

    public ProfileResponse(String message, boolean error, String responseCode, String username, String email, String contact, String profilePicture, String bio) {
        this.error = error;
        this.responseCode = responseCode;
        this.body = new HashMap<>();
        this.body.put("message", message);
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", email);
        data.put("contact", contact);
        data.put("profilePicture", profilePicture);
        data.put("bio", bio);
        this.body.put("data", data);
    }
}
