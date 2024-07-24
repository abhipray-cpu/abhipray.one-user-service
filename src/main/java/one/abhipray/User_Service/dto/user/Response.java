package one.abhipray.User_Service.dto.user;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
 private String message;
 private boolean error;
 private String responseCode;

    public Response(String message, boolean error, String responseCode) {
        this.message = message;
        this.error = error;
        this.responseCode = responseCode;
    }
}
