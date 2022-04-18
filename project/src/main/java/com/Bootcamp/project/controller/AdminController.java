package com.Bootcamp.project.controller;


import com.Bootcamp.project.DTOs.*;
import com.Bootcamp.project.Entities.*;
import com.Bootcamp.project.ExceptionHandlers.ResourceDoesNotExistException;
import com.Bootcamp.project.repos.*;
import com.Bootcamp.project.security.RoleService;
import com.Bootcamp.project.services.AsyncEmailService;
import com.Bootcamp.project.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AuthenticationFacade authenticationFacade;


    @Autowired
    private AsyncEmailService asyncEmailService;



    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private SellerRepo sellerRepo;



    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;




    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/hello")
    public String hello(){
        return "hello";

    }


    @GetMapping(value = "/get/customers/{offset}/{size}")
    public List<GetCustomersResponseDTO> getCustomers(@PathVariable Long offset,@PathVariable Long size){

        List<GetCustomersResponseDTO> listNeeded = new ArrayList<>();

        Pageable page = PageRequest.of(Math.toIntExact(offset), Math.toIntExact(size),Sort.by("cid"));



        List<Customer> list = customerRepo.findAll(page).toList();





        for(Customer customer : list){

            GetCustomersResponseDTO object = new GetCustomersResponseDTO();

            UserEntity user = userRepository.findById(customer.getUserId().getId()).orElse(null);

            object.setEmail(user.getEmail());
            object.setFullName(user.getFirstName() + " " + user.getLastName());
            object.setIs_Active(user.isEnabled());
            object.setId(user.getId());

            listNeeded.add(object);




        }

        return listNeeded;




    }


    @GetMapping("/get/sellers/{offset}/{size}")
    public List<GetSellersResponseDTO> getSellers(@PathVariable Long offset,@PathVariable Long size){

        List<GetSellersResponseDTO> listNeeded = new ArrayList<>();

        Pageable page = PageRequest.of(Math.toIntExact(offset), Math.toIntExact(size),Sort.by("sid"));



        List<Seller> list = sellerRepo.findAll(page).toList();


//        List<Seller> list = sellerRepo.findAll();



        for(Seller seller : list){

            GetSellersResponseDTO object = new GetSellersResponseDTO();

            UserEntity user = userRepository.findById(seller.getUserId().getId()).orElse(null);

            object.setEmail(user.getEmail());
            object.setFullName(user.getFirstName() + " " + user.getLastName());
            object.setIs_Active(user.isEnabled());
            object.setId(user.getId());

            object.setCompanyContact(seller.getCompanyContact());
            object.setCompanyName(seller.getCompanyName());

            listNeeded.add(object);




        }

        return listNeeded;




    }



    @PutMapping("/activate/customer/{cid}")
    public String setActiveCustomer(@PathVariable Long cid){

        Customer customer = customerRepo.findById(cid).orElse(null);



        if(customer == null){
            return "User Not Found";

        }

        UserEntity user = userRepository.findById(customer.getUserId().getId()).orElse(null);

        userRepository.setActive(user.getId());



        return "User activated " + user.getUsername() + " " + user.getId();

    }

    @PutMapping("/deactivate/customer/{cid}")
    public String setUnActiveCustomer(@PathVariable Long cid){

        Customer customer = customerRepo.findById(cid).orElse(null);



        if(customer == null){
            return "User Not Found";

        }

        UserEntity user = userRepository.findById(customer.getUserId().getId()).orElse(null);

        userRepository.setUnActive(user.getId());



        return "User deactivated " + user.getUsername() + " " + user.getId();

    }


    @PutMapping("/activate/seller/{sid}")
    public String setActiveSeller(@PathVariable Long sid){

        Seller seller = sellerRepo.findById(sid).orElse(null);



        if(seller == null){
            return "User Not Found";

        }

        UserEntity user = userRepository.findById(seller.getUserId().getId()).orElse(null);

        userRepository.setActive(user.getId());



        return "User activated " + user.getUsername() + " " + user.getId();

    }

    @PutMapping("/deactivate/seller/{sid}")
    public String setUnActiveSeller(@PathVariable Long sid){

        Seller seller = sellerRepo.findById(sid).orElse(null);



        if(seller == null){
            return "User Not Found";

        }

        UserEntity user = userRepository.findById(seller.getUserId().getId()).orElse(null);

        userRepository.setUnActive(user.getId());



        return "User deactivated " + user.getUsername() + " " + user.getId();

    }



    @PostMapping("/category/add")
    public String addCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        if(categoryDTO.getParentId()==null) {
            Category category = categoryService.addCategory(categoryDTO.getName());
            return "Your category is created with id :" + category.getId();

        } else{

            Category category = categoryService.addCategory(categoryDTO.getName(), categoryDTO.getParentId());
            return "Your category is created with id :" + category.getId();

        }
    }

    @PostMapping("/metadata/add")
    public String addField(@Valid @RequestBody CategoryMetadataFieldDTO categoryMetadataFieldDTO){
        CategoryMetadataField categoryMetadataField = categoryService.addField(categoryMetadataFieldDTO.getName());
        return "Your metadata is created with id : " + categoryMetadataField.getId();


    }

    @GetMapping("/metadata/view")
    public List<CategoryMetadataField> getFieldList(){
        return categoryMetadataFieldRepo.findAll(Sort.by("id"));
    }


    @GetMapping("/category/view")
    public List<CategoryDTO> getCategoryList() {


        List<Category> categories = categoryRepo.findAll(Sort.by("id"));

        List<CategoryDTO> listNeeded = new ArrayList<>();


        for(Category category : categories){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setParentId(category.getParentCategoryId());
            categoryDTO.setName(category.getName());
            listNeeded.add(categoryDTO);


        }

        return listNeeded;

    }

    @GetMapping("/category/{id}")
    public Map<String, List<CategoryDTO>> getCategoryById(@PathVariable Long id) {
        return categoryService.viewCategoryById(id);
    }


    @PutMapping("/category/update/{id}")
    public Category addCategory(@PathVariable Long id,@Valid @RequestBody CategoryDTO categoryDTO){
        return categoryService.updateCategory(id,categoryDTO.getName());
    }

    @PostMapping("category/metadatavalue/add")
    public CategoryFieldValueResDTO addMetadataValue(@Valid @RequestBody CategoryMetadataFieldValueDTO metadataFieldValueDTO){
        return categoryService.addMetadataValue(metadataFieldValueDTO.getCategoryId(), metadataFieldValueDTO.getFieldId(),metadataFieldValueDTO.getValues());
    }

    @PutMapping("category/metadatavalue/update")
    public CategoryFieldValueResDTO updateMetadataValue(@Valid @RequestBody CategoryMetadataFieldValueDTO metadataFieldValueDTO){
        return categoryService.updateMetadataValue(metadataFieldValueDTO.getCategoryId(), metadataFieldValueDTO.getFieldId(),metadataFieldValueDTO.getValues());
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



    @PutMapping(value = "/activate/product/{id}")
    public ResponseEntity<String> activateProduct(@PathVariable Long id){

        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return new ResponseEntity<String>("Product doesn't exist", HttpStatus.BAD_REQUEST);

        }

        if(product.isActive()){
            return new ResponseEntity<String>("Product already active",HttpStatus.BAD_REQUEST);

        }

        product.setActive(true);

        productRepository.save(product);
        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();
        asyncEmailService.sendASynchronousMail(user.getEmail(),"Product Activation","The Product is activated with id :" + id);



        return new ResponseEntity<String>("Product Activated",HttpStatus.BAD_REQUEST);





    }

    @PutMapping(value = "/deactivate/product/{id}")
    public ResponseEntity<String> deActivateProduct(@PathVariable Long id){

        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return new ResponseEntity<String>("Product doesn't exist", HttpStatus.BAD_REQUEST);

        }

        if(!product.isActive()){
            return new ResponseEntity<String>("Product already deactivated",HttpStatus.BAD_REQUEST);

        }

        product.setActive(false);

        productRepository.save(product);

        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();
        asyncEmailService.sendASynchronousMail(user.getEmail(),"Product Deactivation","The Product is deactivated with id" + id);

        return new ResponseEntity<String>("Product Deactivated",HttpStatus.BAD_REQUEST);





    }










}
