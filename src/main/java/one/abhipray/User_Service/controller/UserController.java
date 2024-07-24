package one.abhipray.User_Service.controller;

import jakarta.servlet.http.HttpServletRequest;
import one.abhipray.User_Service.dto.user.*;
import one.abhipray.User_Service.security.JwtTokenProvider;
import one.abhipray.User_Service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
      try{
          Response response = userService.signup(signupRequest);
          return ResponseEntity.status(Integer.parseInt(response.getResponseCode())).body(response.getMessage());
      }
      catch(Exception e){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong at our end");
      }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            String jwt = jwtTokenProvider.createToken(authentication);
            LoginResponse response = userService.login(loginRequest);

            if(response.getResponseCode().equals("200")){
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("token", jwt);
                responseBody.put("message", response.getBody().get("message"));
                response.setBody(responseBody);
            }

            return ResponseEntity.status(Integer.parseInt(response.getResponseCode())).body(response.getBody());
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: Invalid email or password");
        }
        catch(Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong at our end");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(HttpServletRequest request) {
        try{
            String token = request.getHeader("Authorization").substring(7);
            Map<String,String> userDetails = jwtTokenProvider.getUserDetails(token);
            long userId = Long.parseLong(userDetails.get("userId"));
            Response response = userService.deleteUser(userId);
            return ResponseEntity.status(Integer.parseInt(response.getResponseCode())).body(response.getMessage());
        }
        catch(Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong at our end");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody UpdateRequest updateRequest) {
        try{
            String token = request.getHeader("Authorization").substring(7);
            Map<String,String> userDetails = jwtTokenProvider.getUserDetails(token);
            long userId = Long.parseLong(userDetails.get("userId"));
            Response response = userService.updateUser(userId, updateRequest);
            return ResponseEntity.status(Integer.parseInt(response.getResponseCode())).body(response.getMessage());
        }
        catch(Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong at our end");
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> get(HttpServletRequest request) {
        try{
            String token = request.getHeader("Authorization").substring(7);
            Map<String,String> userDetails = jwtTokenProvider.getUserDetails(token);
            long userId = Long.parseLong(userDetails.get("userId"));
            ProfileResponse response = userService.getUser(userId);
            return ResponseEntity.status(Integer.parseInt(response.getResponseCode())).body(response.getBody());
        }
        catch(Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong at our end");
        }
    }
}
