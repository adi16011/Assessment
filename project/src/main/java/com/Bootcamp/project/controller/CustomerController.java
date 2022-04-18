package com.Bootcamp.project.controller;


import com.Bootcamp.project.DTOs.*;
import com.Bootcamp.project.Entities.*;
import com.Bootcamp.project.ExceptionHandlers.ResourceDoesNotExistException;
import com.Bootcamp.project.repos.*;
import com.Bootcamp.project.security.RoleService;
import com.Bootcamp.project.services.AsyncEmailService;
import com.Bootcamp.project.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    CategoryRepo categoryRepo;



    @Autowired
    private ProductRepository productRepository;



    @Autowired
    private DataSource dataSource;


    @Autowired
    ConfirmTokenRepo confirmTokenRepo;


    @Autowired
    private AuthenticationFacade authenticationFacade;


    @Autowired
    private AddressRepo addressRepo;



    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private AsyncEmailService asyncEmailService;



    @PostMapping(value = "/logout")
    public ResponseEntity<String> logoutCustomer(@RequestParam String token,Principal principal){

        Authentication authentication = authenticationFacade.getAuthentication();




        JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)principal;
        OAuth2AccessToken accessToken = jdbcTokenStore.getAccessToken(oAuth2Authentication);

        if(!accessToken.getValue().equals(token)){
            return new ResponseEntity<String>("This token doesn't exist",HttpStatus.BAD_REQUEST);

        }

        if(accessToken.isExpired()){

            jdbcTokenStore.removeAccessToken(accessToken.getValue());
            jdbcTokenStore.removeRefreshToken(accessToken.getRefreshToken());


            return new ResponseEntity<String>("This token is expired already",HttpStatus.BAD_REQUEST);

        }

        jdbcTokenStore.removeAccessToken(accessToken.getValue());
        jdbcTokenStore.removeRefreshToken(accessToken.getRefreshToken());

        return new ResponseEntity<String>("User id logged out",HttpStatus.ACCEPTED);



    }





    @GetMapping("/viewCustomer")
    public CustomerResponseDTO viewCustomer(){

        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();

        Customer customer = customerRepo.findByUserId(user);












        CustomerResponseDTO returnDto = new CustomerResponseDTO();

        returnDto.setId(user.getId());
        returnDto.setFirstName(user.getFirstName());
        returnDto.setLastName(user.getLastName());
        returnDto.setIs_Active(user.isActive());
        returnDto.setContact(customer.getContact());



        return returnDto;




    }

    @GetMapping("/viewAddress")
    public AddressResponseDTO viewAddress(){

        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();

        Address address = addressRepo.findByUserId(user.getId());

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        addressResponseDTO.setAddress(address.getAddressLine());
        addressResponseDTO.setCity(address.getCity());
        addressResponseDTO.setState(address.getState());
        addressResponseDTO.setZipCode(address.getZipCode());
        addressResponseDTO.setCountry(address.getCountry());
        addressResponseDTO.setLabel(address.getLabel());


        return addressResponseDTO;



    }


    @PutMapping("/updateCustomer")
    public ResponseEntity<String> updateCustomer(@Valid @RequestBody CustomerUpdateRequestDTO customerUpdateRequestDTO){


        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();

        Customer customer = customerRepo.findByUserId(user);



        if(customerUpdateRequestDTO.getFirstName()!=null){
            user.setFirstName(customerUpdateRequestDTO.getFirstName());
        }

        if(customerUpdateRequestDTO.getLastName()!=null){
            user.setLastName(customerUpdateRequestDTO.getLastName());
        }

        if(customerUpdateRequestDTO.getEmail()!=null){
            user.setEmail(customerUpdateRequestDTO.getEmail());
        }

        if(customerUpdateRequestDTO.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(customerUpdateRequestDTO.getPassword()));
        }

        if(customerUpdateRequestDTO.getContact()!=null){

            customer.setContact(customerUpdateRequestDTO.getContact());

        }



        userRepository.setUser(user.getFirstName(), user.getLastName(),user.getEmail(),user.getPassword(), user.getId());


        customerRepo.setCustomer(user.getId(), customer.getContact());








        String response = "Customer is updated successfully!!";







//        return new ExceptionResponse(new Date(),"Successful","Successful Updation of Customer");



        return new ResponseEntity<String>(response,HttpStatus.CREATED);





    }


    @PutMapping("/updateCustomerPassword")
    public ResponseEntity<String> updateCustomerPassword(@Valid @RequestBody UpdatePasswordDTO dto){

        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();


        if(dto.getPassword().equals(dto.getConfirmPassword())){

            userRepository.setPassword(passwordEncoder.encode(dto.getPassword()), user.getId());

            return new ResponseEntity<String>("Password updated Successfully for Customer",HttpStatus.ACCEPTED);


//            return new ExceptionResponse(new Date(),"Success!!","Password updated successfully for Customer");






        }else {

            return new ResponseEntity<String>("Password Mismatch Please check and then enter",HttpStatus.BAD_REQUEST);


//            return new ExceptionResponse(new Date(),"Failed!!","Password Mismatch for confirmPassword please check and then enter");


        }



    }



    @RequestMapping(value = "/addressUpdate/{aid}",method = RequestMethod.PATCH)
    public ResponseEntity<String> updateCustomerAddress(@PathVariable(value = "aid") Long aid, @Valid @RequestBody AddressChangeRequestDTO changeRequestDTO){


        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();



        Address address = addressRepo.findById(aid).orElse(null);


        if(address == null){

//            return new ExceptionResponse(new Date(),"Error","Address not Found");


        }


        addressRepo.updateAddress(address.getId(),
                changeRequestDTO.getCity(),
                changeRequestDTO.getState(),
                changeRequestDTO.getCountry(),
                changeRequestDTO.getAddressLine(),
                changeRequestDTO.getZipCode(),
                changeRequestDTO.getLabel());


        return new ResponseEntity<String>("Successfully updated the address",HttpStatus.ACCEPTED);



//        return new ExceptionResponse(new Date(),"Success","Successfully updated the address");








    }


    @RequestMapping(value = "/addAddress",method = RequestMethod.POST)
    public ResponseEntity<String> addAddress(@Valid @RequestBody AddressChangeRequestDTO changeRequestDTO){

        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();

        Address address = new Address();

        address.setAddressLine(changeRequestDTO.getAddressLine());
        address.setCountry(changeRequestDTO.getCountry());
        address.setLabel(changeRequestDTO.getLabel());
        address.setZipCode(changeRequestDTO.getZipCode());
        address.setCity(changeRequestDTO.getCity());
        address.setState(changeRequestDTO.getState());
        address.setUserId(user);

        addressRepo.save(address);

        return new ResponseEntity<String>("Address Entered Successfully",HttpStatus.CREATED);



//        return new ExceptionResponse(new Date(),"Success","Address updated successfully");







    }

    @DeleteMapping(value = "/deleteAddress")
    public ResponseEntity<String> deleteAddress(){

        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();

        Address address = addressRepo.findByUserId(user.getId());

        if(address == null){

            return new ResponseEntity<String>("Address Not Found",HttpStatus.BAD_REQUEST);

//            return new ExceptionResponse(new Date(),"Failed","Address not Found");

        }

        addressRepo.deleteAddress(address.getId());


        return new ResponseEntity<String>("Address deleted",HttpStatus.ACCEPTED);





//        return new ExceptionResponse(new Date(),"Success","Address deleted");



    }


    @GetMapping(value = "/view/product/{id}")
    public ProductResponseDTO viewProducts(@PathVariable Long id){

        Product product = productRepository.findById(id).orElse(null);

        if(product == null){
            throw new ResourceDoesNotExistException("Product Doesn't exist with id : " + id);

        }

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setBrand(product.getBrand());
        productResponseDTO.setCancellable(product.isCancellable());
        productResponseDTO.setDeleted(product.isDeleted());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setActive(product.isActive());
        productResponseDTO.setCategoryName(product.getCategoryId().getName());
        productResponseDTO.setReturnable(product.isReturnable());


        return productResponseDTO;



    }


    @GetMapping(value = "/view/products")
    public List<ProductResponseDTO> viewAllProducts(@RequestBody PageableDTO pageableDTO){

        List<ProductResponseDTO> listNeeded = new ArrayList<>();

        Integer offset = Math.toIntExact(pageableDTO.getOffset());
        Integer size = Math.toIntExact(pageableDTO.getSize());

        List<Product> listProducts;


        if(offset != -1 && size != 0){

            Pageable page = PageRequest.of(offset, size, Sort.by("id"));



            listProducts = productRepository.findAll(page).toList();



        }else{
            listProducts = productRepository.findAll();


        }






        for(Product product : listProducts){
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();

            productResponseDTO.setBrand(product.getBrand());
            productResponseDTO.setCancellable(product.isCancellable());
            productResponseDTO.setDeleted(product.isDeleted());
            productResponseDTO.setName(product.getName());
            productResponseDTO.setDescription(product.getDescription());
            productResponseDTO.setActive(product.isActive());
            productResponseDTO.setCategoryName(product.getCategoryId().getName());
            productResponseDTO.setReturnable(product.isReturnable());

            listNeeded.add(productResponseDTO);




        }

        return listNeeded;


    }


    @GetMapping("/category/view")
    public List<CategoryDTO> getCategoryList(@RequestBody Optional<String> id) {




        List<Category> categories = categoryRepo.findAll(Sort.by("id"));

        List<CategoryDTO> listNeeded = new ArrayList<>();


        for(Category category : categories){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setParentId(category.getParentCategoryId());
            categoryDTO.setName(category.getName());
            listNeeded.add(categoryDTO);


        }

        List<CategoryDTO> listNeededFinal = new ArrayList<>();



        if(!id.isPresent()){

            for(CategoryDTO categoryDTO : listNeeded){
                if(categoryDTO.getParentId() != null)
                {


                }else{
                    listNeededFinal.add(categoryDTO);

                }



            }

        }else{

            for(CategoryDTO categoryDTO : listNeeded){
                if(categoryDTO.getParentId() == null)
                {


                }else{

                    listNeededFinal.add(categoryDTO);


                }




            }

        }

        return listNeededFinal;

    }







}
