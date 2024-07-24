package one.abhipray.User_Service.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Setter
@Getter
public class LoginResponse {
    private boolean error;
    private String responseCode;
    private Map<String,String> body;

    public LoginResponse(String message, boolean error, String responseCode,String token) {
        this.error = error;
        this.responseCode = responseCode;
        this.body = new HashMap<>();
        this.body.put("message",message);
        this.body.put("token",token);
    }
}

