package one.abhipray.User_Service.service;

import jakarta.validation.ConstraintViolationException;
import one.abhipray.User_Service.dto.user.*;
import one.abhipray.User_Service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import one.abhipray.User_Service.model.User;
import java.util.Optional;
@Service
public class UserService {
   @Autowired
   private UserRepository userRepository;

   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   public Response signup(SignupRequest signupRequest){
       try {
           User user = new User();
           user.setUsername(signupRequest.getName());
           user.setEmail(signupRequest.getEmail());
           user.setContact(signupRequest.getContact());
           user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
           user.setVerified(false);
           User newUser = userRepository.save(user);
           return new Response("User created successfully", false, "201");
       } catch (DataIntegrityViolationException e) {
           return new Response("Failed to create user: unique constraint violation", true, "400");
       } catch (ConstraintViolationException e) {
           return new Response( "Validation error: " + e.getMessage(), true, "400");
       } catch (MethodArgumentNotValidException e) {
           return new Response( "Method argument not valid: " + e.getMessage(), true, "400");
       } catch (IllegalArgumentException e) {
           return new Response( "Illegal argument: " + e.getMessage(), true, "400");
       } catch (AccessDeniedException e) {
           return new Response( "Access denied: " + e.getMessage(), true, "403");
       } catch (Exception e) {
           return new Response( e.getMessage(), true, "500");
       }

   }

   public LoginResponse login(LoginRequest loginRequest){
         try {
             Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
             if (userOptional.isEmpty()) {
                 return new LoginResponse("User not found", true, "404", null);
             }
             User user = userOptional.get();
              if(!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
                return new LoginResponse("Incorrect password", true, "401", null);}

              // Generate Token

              return new LoginResponse("Login successful", false, "200", "");
         } catch (DataIntegrityViolationException e) {
              return new LoginResponse("Failed to login: unique constraint violation", true, "400", null);
         } catch (ConstraintViolationException e) {
              return new LoginResponse( "Validation error: " + e.getMessage(), true, "400", null);
         } catch (MethodArgumentNotValidException e) {
              return new LoginResponse( "Method argument not valid: " + e.getMessage(), true, "400", null);
         } catch (IllegalArgumentException e) {
              return new LoginResponse( "Illegal argument: " + e.getMessage(), true, "400", null);
         } catch (AccessDeniedException e) {
              return new LoginResponse( "Access denied: " + e.getMessage(), true, "403", null);
         } catch (Exception e) {
              return new LoginResponse( e.getMessage(), true, "500", null);
         }
   }

   public Response deleteUser(long userId){
       try{
           Optional<User> userOptional = userRepository.findById(userId);

           if(userOptional.isEmpty()){
               return new Response("User not found", true, "404");
           }
           userRepository.deleteById(userId);
           return new Response("User deleted successfully", false, "200");
       }
       catch (DataIntegrityViolationException e) {
           return new Response("Failed to delete user: unique constraint violation", true, "400");
       } catch (ConstraintViolationException e) {
           return new Response( "Validation error: " + e.getMessage(), true, "400");
       } catch (MethodArgumentNotValidException e) {
           return new Response( "Method argument not valid: " + e.getMessage(), true, "400");
       } catch (IllegalArgumentException e) {
           return new Response( "Illegal argument: " + e.getMessage(), true, "400");
       } catch (AccessDeniedException e) {
           return new Response( "Access denied: " + e.getMessage(), true, "403");
       } catch (Exception e) {
           return new Response( e.getMessage(), true, "500");
       }
   }

   public Response updateUser(long userId,UpdateRequest updateRequest){
       try{
           Optional<User> userOptional = userRepository.findById(userId);

           if(userOptional.isEmpty()){
               return new Response("User not found", true, "404");
           }
           User user = userOptional.get();
           if (updateRequest.getEmail() != null){
               user.setEmail(updateRequest.getEmail());
               if(user.isVerified()){
                   user.setVerified(false);
               }
               // here need to implement a jwt blacklist service to invalidate the user jwt token
           }
           if (updateRequest.getContact() != null) user.setContact(updateRequest.getContact());
           if (updateRequest.getUsername() != null) user.setUsername(updateRequest.getUsername());
           if (updateRequest.getBio() != null) user.setOauthProviderId(updateRequest.getBio());

           userRepository.save(user);
           return new Response("User updated successfully", false, "200");
       }
       catch (DataIntegrityViolationException e) {
           return new Response("Failed to update user: unique constraint violation", true, "400");
       } catch (ConstraintViolationException e) {
           return new Response( "Validation error: " + e.getMessage(), true, "400");
       } catch (MethodArgumentNotValidException e) {
           return new Response( "Method argument not valid: " + e.getMessage(), true, "400");
       } catch (IllegalArgumentException e) {
           return new Response( "Illegal argument: " + e.getMessage(), true, "400");
       } catch (AccessDeniedException e) {
           return new Response( "Access denied: " + e.getMessage(), true, "403");
       } catch (Exception e) {
           return new Response( e.getMessage(), true, "500");
       }
   }

   public ProfileResponse getUser(long userId){
         try{
              Optional<User> userOptional = userRepository.findById(userId);

              if(userOptional.isEmpty()){
                return new ProfileResponse("User not found", true, "404",null,null,null,null,null );
              }
              User user = userOptional.get();
              return new ProfileResponse("User found", false, "200",user.getUsername(), user.getEmail(), user.getContact(),user.getProfilePicture(), user.getBio());
         }
         catch (DataIntegrityViolationException e) {
              return new ProfileResponse("Failed to get user: unique constraint violation", true, "400", null,null,null,null,null);
         } catch (ConstraintViolationException e) {
              return new ProfileResponse( "Validation error: " + e.getMessage(), true, "400", null,null,null,null,null);
         } catch (MethodArgumentNotValidException e) {
              return new ProfileResponse( "Method argument not valid: " + e.getMessage(), true, "400", null,null,null,null,null);
         } catch (IllegalArgumentException e) {
              return new ProfileResponse( "Illegal argument: " + e.getMessage(), true, "400", null,null,null,null,null);
         } catch (AccessDeniedException e) {
              return new ProfileResponse( "Access denied: " + e.getMessage(), true, "403", null,null,null,null,null);
         } catch (Exception e) {
              return new ProfileResponse( e.getMessage(), true, "500", null,null,null,null,null);
         }
   }
}
