package com.Bootcamp.project.controller;


import com.Bootcamp.project.DTOs.CustomerRequestDTO;
import com.Bootcamp.project.DTOs.LoginDTO;
import com.Bootcamp.project.DTOs.SellerRequestDTO;
import com.Bootcamp.project.Entities.*;
import com.Bootcamp.project.ExceptionHandlers.UserExistException;
import com.Bootcamp.project.ExceptionHandlers.PasswordNotMatchingException;
import com.Bootcamp.project.ExceptionHandlers.ResourceDoesNotExistException;
import com.Bootcamp.project.repos.*;
import com.Bootcamp.project.security.RoleService;
import com.Bootcamp.project.services.AsyncEmailService;
import com.Bootcamp.project.services.CategoryService;
import com.Bootcamp.project.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;


import static org.springframework.security.oauth2.common.util.OAuth2Utils.CLIENT_ID;

@RestController
public class PublicAccessController {

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    ConfirmTokenRepo confirmTokenRepo;



    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    private DataSource dataSource;





    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ClientDetailsService clientDetailsService;



    @Autowired
    private AsyncEmailService asyncEmailService;

    @PostMapping(value = "/registerCustomer")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO){

        if(userRepository.findByEmail(customerRequestDTO.getEmail())!=null){
            throw new UserExistException("Customer already exist with email " + customerRequestDTO.getEmail());

        }

        if(!(customerRequestDTO.getPassword().equals(customerRequestDTO.getConfirmPassword()))){
            throw new PasswordNotMatchingException("Your Password is not matching ");

        }











        UserEntity user = new UserEntity();




        user.setFirstName(customerRequestDTO.getFirstName());
        user.setLastName(customerRequestDTO.getLastName());
        user.setPassword(passwordEncoder.encode(customerRequestDTO.getPassword()));
        user.setEmail(customerRequestDTO.getEmail());
        user.setActive(false);









        Customer customer = new Customer();

        customer.setContact(customerRequestDTO.getContact());

        customer.setUserId(user);








        customerRepo.save(customer);

        UserEntity userf =userRepository.findByEmail(user.getEmail());


        roleService.assignUserRole(userf.getId(),3L);



        ConfirmToken confirmToken = new ConfirmToken(userf);
        confirmTokenRepo.save(confirmToken);
        asyncEmailService.sendASynchronousMail(user.getEmail(),"Complete Activation of your Account","To activate your account please use this link : " + "http://localhost:8080/customer/confirm?token="+confirmToken.getConfirmationToken());



        String response = "User Successfully created please check your email to activate your account";












//        ExceptionResponse response = new ExceptionResponse(new Date(),"User Created","User Successfully created please check your email to activate your account");











        return new ResponseEntity<String>(response,HttpStatus.CREATED);

    }


    @PostMapping(value = "/registerSeller")
    public ResponseEntity<String> registeringSeller(@Valid @RequestBody SellerRequestDTO sellerRequestDTO){


        if(userRepository.findByEmail(sellerRequestDTO.getEmail())!=null){
            throw new UserExistException("Seller already exist with email " + sellerRequestDTO.getEmail());

        }

        if(!(sellerRequestDTO.getPassword().equals(sellerRequestDTO.getConfirmPassword()))){
            throw new PasswordNotMatchingException("Your Password is not matching ");

        }

        UserEntity user = new UserEntity();




        user.setFirstName(sellerRequestDTO.getFirstName());
        user.setLastName(sellerRequestDTO.getLastName());
        user.setPassword(passwordEncoder.encode(sellerRequestDTO.getPassword()));
        user.setEmail(sellerRequestDTO.getEmail());
        user.setActive(false);









        Seller seller = new Seller();


        seller.setGst(sellerRequestDTO.getGst());
        seller.setCompanyContact(sellerRequestDTO.getCompanyContact());

        seller.setCompanyName(sellerRequestDTO.getCompanyName());

        seller.setUserId(user);










        sellerRepo.save(seller);


        UserEntity userf = userRepository.findByEmail(user.getEmail());


        roleService.assignUserRole(userf.getId(),2L);


        Address address = new Address();

        address.setCity(sellerRequestDTO.getCity());
        address.setState(sellerRequestDTO.getState());
        address.setCountry(sellerRequestDTO.getCountry());
        address.setAddressLine(sellerRequestDTO.getAddressLine());
        address.setZipCode(sellerRequestDTO.getZipCode());
        address.setLabel(sellerRequestDTO.getLabel());
        address.setUserId(userf);



        addressRepo.save(address);

        asyncEmailService.sendASynchronousMail(user.getEmail(), "Seller Registration Process","Your Registration is done and is now waiting approval");




        return new ResponseEntity<String>("Seller is created successfully",HttpStatus.CREATED);





//        return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(new Date(),"Seller created successfully","Success Message"), HttpStatus.ACCEPTED);

    }



    @PutMapping(value = "/confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam("token") String confirmationToken) throws InvalidTokenException {
        ConfirmToken token = confirmTokenRepo.findByConfirmationToken(confirmationToken);
        System.out.println(token.getUserEntity().getEmail());
        if (token != null) {
            UserEntity user = userRepository.findByEmail(token.getUserEntity().getEmail());
            if (user == null) {
                throw new InvalidTokenException("Invalid token");
            }
            user.setActive(true);
            userRepository.save(user);

            asyncEmailService.sendASynchronousMail(user.getEmail(),"Registration Completed","Your account is now activated");


            return new ResponseEntity<String>("Your account is now activated", HttpStatus.CREATED);
        } else {
            throw new InvalidTokenException("token doesn't exist ");
        }
    }

    @PutMapping(value = "resend/activation/{email}")
    public ResponseEntity<String> resendActivation(@PathVariable String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceDoesNotExistException("User Not found");
        }
        ConfirmToken confirmationToken = new ConfirmToken(user);
        confirmTokenRepo.save(confirmationToken);

        asyncEmailService.sendASynchronousMail(user.getEmail(), "Complete registration","To confirm your account, please click on the given link : "
                +"http://localhost:8080/confirm?token="+confirmationToken.getConfirmationToken());

        return new ResponseEntity<String>("The email for the activation is sent please check the email", HttpStatus.OK);
    }


//    @PostMapping(value = "/login")
//    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO){
//
//        HashMap<String, String> parameters = new HashMap<>();
//        parameters.put("client_id", CLIENT_ID);
//        parameters.put("client_secret", CLIENT_SECRET);
//        parameters.put("grant_type", "client_credentials");
//
//        clientDetailsService.loadClientByClientId(CLIENT_ID);
//
//
//        ClientDetails clientDetails = clientDetailsStore.get(CLIENT_ID);
//
//        // Create principal and auth token
//        User userPrincipal = new User(CLIENT_ID, CLIENT_SECRET, true, true, true, true, clientDetails.getAuthorities());
//
//        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(userPrincipal, CLIENT_SECRET,
//                clientDetails.getAuthorities());
//
//        ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
//
//        return accessToken.getBody();
//
//    }

}
