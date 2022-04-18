package com.Bootcamp.project.controller;


import com.Bootcamp.project.DTOs.*;
import com.Bootcamp.project.Entities.*;

import com.Bootcamp.project.ExceptionHandlers.ResourceDoesNotExistException;
import com.Bootcamp.project.repos.*;
import com.Bootcamp.project.security.RoleService;
import com.Bootcamp.project.services.AsyncEmailService;
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
import java.util.Map;


@RestController
@RequestMapping(value = "/seller")
public class SellerController {

    @Autowired
    private DataSource dataSource;



    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepo categoryRepo;








    @Autowired
    private AddressRepo addressRepo;


    @Autowired
    private AuthenticationFacade authenticationFacade;



    @Autowired
    private UserRepository userRepository;


    @Autowired
    private SellerRepo sellerRepo;


    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProductVariationRepo productVariationRepo;

    @Autowired
    private CategoryMetadataFieldRepo categoryMetadataFieldRepo;

    @Autowired
    private CategoryMetaDataFieldValueRepo categoryMetaDataFieldValueRepo;

    @Autowired
    private AsyncEmailService asyncEmailService;











    @GetMapping("/viewSeller")
    public SellerResponseDTO viewSeller(){

        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();

        SellerResponseDTO dto = new SellerResponseDTO();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setActive(user.isActive());

        Seller seller = sellerRepo.findByUserId(user);

        dto.setCompanyContact(seller.getCompanyContact());
        dto.setCompanyName(seller.getCompanyName());
        dto.setGst(seller.getGst());

        Address address = addressRepo.findByUserId(user.getId());

        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        dto.setAddressLine(address.getAddressLine());
        dto.setZipCode(address.getZipCode());

        return dto;


    }


    @PutMapping("/update_password")
    public String updateSellerPassword(@Valid @RequestBody UpdatePasswordDTO dto){

        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();


        if(dto.getPassword().equals(dto.getConfirmPassword())){

            userRepository.setPassword(passwordEncoder.encode(dto.getPassword()), user.getId());
            asyncEmailService.sendASynchronousMail(user.getEmail(),"Password Updated","Your Password is updated successfully");



            return "Password updated Successfully";






        }else {

            return "Password Mismatch";


        }




    }



    @PutMapping("/update_address/{aid}")
    public String updateAddress(@PathVariable Long aid,@Valid @RequestBody AddressChangeRequestDTO changeRequestDTO){

        Address address = addressRepo.findById(aid).orElse(null);

        if(address == null){

            return "Address Not Found";


        }


        addressRepo.updateAddress(address.getId(),
                changeRequestDTO.getCity(),
                changeRequestDTO.getState(),
                changeRequestDTO.getCountry(),
                changeRequestDTO.getAddressLine(),
                changeRequestDTO.getZipCode(),
                changeRequestDTO.getLabel());


        return "Address updated Successfully";








    }


    @PutMapping("/update")
    public ResponseEntity<String> updateSeller(@Valid @RequestBody SellerUpdateRequestDTO seller){


        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();

        Seller sellerData = sellerRepo.findByUserId(user);


        if(seller.getFirstName()!=null){
            user.setFirstName(seller.getFirstName());
        }

        if(seller.getLastName()!=null){
            user.setLastName(seller.getLastName());
        }

        if(seller.getEmail()!=null){
            user.setEmail(seller.getEmail());
        }

        if(seller.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(seller.getPassword()));
        }

        if(seller.getCompanyContact()!=null){

            sellerData.setCompanyContact(seller.getCompanyContact());

        }

        if(seller.getCompanyName()!=null){
            sellerData.setCompanyName(seller.getCompanyName());
        }

        if(seller.getGst()!=null){
            sellerData.setGst(seller.getGst());
        }

        userRepository.setUser(user.getFirstName(), user.getLastName(),user.getEmail(),user.getPassword(), user.getId());

        sellerRepo.setSeller(user.getId(), sellerData.getCompanyContact(),sellerData.getCompanyName(),sellerData.getGst());


        return new ResponseEntity<String>("Seller is updated successfully",HttpStatus.ACCEPTED);















//        return new ExceptionResponse(new Date(),"Successful","Successful Updation of Seller");







    }


    @PostMapping(value = "/add/product")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){

        Authentication authentication = authenticationFacade.getAuthentication();


        UserEntity user = (UserEntity) authentication.getPrincipal();

        Seller seller = sellerRepo.findByUserId(user);

        Category category = categoryRepo.findById(productRequestDTO.getCategoryId()).orElse(null);


        if(category == null){
            return new ResponseEntity<String>("The category for this product doesn't exist please enter valid category id",HttpStatus.BAD_REQUEST);

        }

        if(category.getParentCategoryId()==null) {
            return new ResponseEntity<String>("You can't add a product to a parent Category", HttpStatus.BAD_REQUEST);

        }


        List<Product> sameBrandCategorySeller = productRepository.findSameBrandCategorySeller(productRequestDTO.getBrand(), productRequestDTO.getCategoryId(), seller.getSid());

        if(sameBrandCategorySeller == null){
            return new ResponseEntity<String>("A product with same brand and category already exist",HttpStatus.BAD_REQUEST);

        }


        Product product = new Product();

        product.setName(productRequestDTO.getName());
        product.setSeller(seller);
        product.setActive(false);
        product.setBrand(productRequestDTO.getBrand());
        product.setCancellable(productRequestDTO.isCanBeCancelled());
        product.setReturnable(productRequestDTO.isCanBeReturned());
        product.setDescription(productRequestDTO.getDescription());
        product.setDeleted(false);



        product.setCategoryId(category);


        productRepository.save(product);


        return new ResponseEntity<String>("Product Successfully Added",HttpStatus.CREATED);
    }


    @PostMapping(value = "/add/product/variation")
    public ResponseEntity<String> addProductVariation(@Valid @RequestBody ProductVariationDTO productVariationDTO){

        if(productVariationDTO.getMetadata() == null){

            return new ResponseEntity<String>("Atleast One metadata is needed for product variation to be added",HttpStatus.BAD_REQUEST);

        }



        ProductVariation productVariation = new ProductVariation();

        Product  product = productRepository.findById(productVariationDTO.getProductId()).orElse(null);

        if(product == null){
            return new ResponseEntity<String>("Product doesn't exist",HttpStatus.BAD_REQUEST);

        }



        Map<String,String> metadataExample = productVariationDTO.getMetadata();



        for(Map.Entry<String,String> entry : metadataExample.entrySet()){

            CategoryMetadataField categoryMetadataField = new CategoryMetadataField();

            categoryMetadataField = categoryMetadataFieldRepo.findByName(entry.getKey());

            if(categoryMetadataField == null){
                return new ResponseEntity<String>("Category Metadata Field doesn't Exist",HttpStatus.BAD_REQUEST);


            }

            String value = categoryMetaDataFieldValueRepo.findByCategoryAndCategoryMetadataValue(product.getCategoryId().getId(), categoryMetadataField.getId());


            boolean flagForValuesFound = false;


            String[] res = value.split("[,]", 0);
            for(String myStr: res) {
                if(entry.getValue().equals(myStr)){

                    flagForValuesFound = true;


                }
            }

            if(flagForValuesFound == false){

                return new ResponseEntity<String>("One of The Field values doesn't exist",HttpStatus.BAD_REQUEST);


            }







        }




        productVariation.setProduct(product);

        productVariation.setActive(true);
        productVariation.setPrice(productVariationDTO.getPrice());
        productVariation.setQuantityAvailable(productVariationDTO.getQuantity());
        productVariation.setMetadata(productVariationDTO.getMetadata());

        productVariationRepo.save(productVariation);




        return new ResponseEntity<String>("Product Variation is successfully saved ",HttpStatus.CREATED);





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


    @GetMapping(value = "/view/product/variation/{id}")
    public ProductVariationResponseDTO viewProductVariation(@PathVariable Long id){

        ProductVariation productVariation = productVariationRepo.findById(id).orElse(null);

        if(productVariation == null){
            throw new ResourceDoesNotExistException("Product Variation exist of id : " + id);

        }

        ProductVariationResponseDTO productVariationResponseDTO = new ProductVariationResponseDTO();


        productVariationResponseDTO.setBrand(productVariation.getProduct().getBrand());
        productVariationResponseDTO.setCancellable(productVariation.getProduct().isCancellable());
        productVariationResponseDTO.setDescription(productVariation.getProduct().getDescription());
        productVariationResponseDTO.setMetadata(productVariation.getMetadata());
        productVariationResponseDTO.setName(productVariation.getProduct().getName());
        productVariationResponseDTO.setReturnable(productVariation.getProduct().isReturnable());
        productVariationResponseDTO.setActive(productVariation.getActive());
        productVariationResponseDTO.setPrice(productVariation.getPrice());
        productVariationResponseDTO.setQuantityAvailable(productVariation.getQuantityAvailable());
        productVariationResponseDTO.setDeleted(productVariation.getProduct().isDeleted());


        return productVariationResponseDTO;





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

    @GetMapping(value = "/view/productVariationOfProduct/{id}")
    public List<ProductVariationResponseDTO> viewAllProductVariationOfSingleProduct(@RequestBody PageableDTO pageableDTO, @PathVariable Long id){


        List<ProductVariationResponseDTO> listNeeded = new ArrayList<>();

        Integer offset = Math.toIntExact(pageableDTO.getOffset());
        Integer size = Math.toIntExact(pageableDTO.getSize());

        List<ProductVariation> listProducts;

        ProductVariation productVariationFound = productVariationRepo.findById(id).orElse(null);



        if(productVariationFound == null){

            throw new ResourceDoesNotExistException("Product Variation does not exist with id :" +id);

        }



        if(offset != -1 && size != 0){

            Pageable page = PageRequest.of(offset, size, Sort.by("id"));



            listProducts = productVariationRepo.findAllByProduct(productVariationFound.getProduct(),page);




        }else{
            listProducts = productVariationRepo.findAllByProduct(productVariationFound.getProduct());


        }






        for(ProductVariation productVariation : listProducts){
            ProductVariationResponseDTO productVariationResponseDTO = new ProductVariationResponseDTO();

            productVariationResponseDTO.setBrand(productVariation.getProduct().getBrand());
            productVariationResponseDTO.setCancellable(productVariation.getProduct().isCancellable());
            productVariationResponseDTO.setDescription(productVariation.getProduct().getDescription());
            productVariationResponseDTO.setMetadata(productVariation.getMetadata());
            productVariationResponseDTO.setName(productVariation.getProduct().getName());
            productVariationResponseDTO.setReturnable(productVariation.getProduct().isReturnable());
            productVariationResponseDTO.setActive(productVariation.getActive());
            productVariationResponseDTO.setPrice(productVariation.getPrice());
            productVariationResponseDTO.setQuantityAvailable(productVariation.getQuantityAvailable());
            productVariationResponseDTO.setDeleted(productVariation.getProduct().isDeleted());

            listNeeded.add(productVariationResponseDTO);




        }

        return listNeeded;





    }

    @DeleteMapping(value = "/delete/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){

        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return new ResponseEntity<String>("Product that you are looking to delete doesn't exist",HttpStatus.BAD_REQUEST);

        }
        Authentication authentication = authenticationFacade.getAuthentication();

        if(!product.getCreatedBy().equals(authentication.getName())){

            return new ResponseEntity<String>("You can't delete this resource it is not accessible",HttpStatus.BAD_REQUEST);





        }

        productRepository.deleteById(id);

        return new ResponseEntity<String>("Product deleted successfully",HttpStatus.ACCEPTED);


    }

    @PutMapping(value = "/update/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,@Valid @RequestBody UpdateProductRequestDTO updateProductRequestDTO){

        Product product = productRepository.findById(id).orElse(null);

        Authentication authentication = authenticationFacade.getAuthentication();

//        if(!product.getCreatedBy().equals(authentication.getName())){
//
//            return new ResponseEntity<String>("You can't delete this resource it is not accessible",HttpStatus.BAD_REQUEST);
//
//
//
//
//
//        }

        if(product == null){
            return new ResponseEntity<String>("Product doesn't exist with id : "+ id,HttpStatus.BAD_REQUEST);

        }

        List<Product> productWithSameNames = productRepository.findAllByName(updateProductRequestDTO.getName());

        if(!productWithSameNames.isEmpty()){
            return new ResponseEntity<String>("This name already exists : " + updateProductRequestDTO.getName(),HttpStatus.BAD_REQUEST);

        }



        product.setName(updateProductRequestDTO.getName());
        product.setDescription(updateProductRequestDTO.getDescription());
        product.setReturnable(updateProductRequestDTO.isReturnable());
        product.setCancellable(updateProductRequestDTO.isCancellable());

        productRepository.save(product);


        return new ResponseEntity<String>("Product Successfully Updated",HttpStatus.ACCEPTED);









    }


    @PutMapping(value = "/update/product/variation/{id}")
    public ResponseEntity<String> updateProductVariation(@PathVariable Long id,@Valid @RequestBody UpdateProductRequestVariationDTO updateProductRequestVariationDTO){

        ProductVariation productVariation = productVariationRepo.findById(id).orElse(null);


        Authentication authentication = authenticationFacade.getAuthentication();

//        if(!productVariation.getCreatedBy().equals(authentication.getName())){
//
//            return new ResponseEntity<String>("You can't delete this resource it is not accessible",HttpStatus.BAD_REQUEST);
//
//
//
//
//
//        }

        if(productVariation == null){
            return new ResponseEntity<String>("Product Variation doesn't exist with id : "+ id,HttpStatus.BAD_REQUEST);

        }

        if(productVariation.getProduct().isActive()){
            return new ResponseEntity<String>("Product Variation is not active ",HttpStatus.BAD_REQUEST);

        }


        productVariation.setQuantityAvailable(updateProductRequestVariationDTO.getQuantity());
        productVariation.setPrice(updateProductRequestVariationDTO.getPrice());
        productVariation.setMetadata(updateProductRequestVariationDTO.getMetadata());
        productVariation.setActive(updateProductRequestVariationDTO.isActive());





        productVariationRepo.save(productVariation);



        return new ResponseEntity<String>("Product Variation Successfully Updated",HttpStatus.ACCEPTED);









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

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logoutCustomer(@RequestParam String token, Principal principal){

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





}
