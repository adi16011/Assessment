package com.Bootcamp.project.services;

import com.Bootcamp.project.DTOs.CategoryDTO;
import com.Bootcamp.project.DTOs.CategoryFieldValueResDTO;
import com.Bootcamp.project.DTOs.CategoryMetadataFieldValueDTO;
import com.Bootcamp.project.Entities.Category;
import com.Bootcamp.project.Entities.CategoryMetadataField;
import com.Bootcamp.project.Entities.CategoryMetadataFieldValues;
import com.Bootcamp.project.ExceptionHandlers.ResourceAlreadyExistException;
import com.Bootcamp.project.ExceptionHandlers.ResourceDoesNotExistException;
import com.Bootcamp.project.repos.CategoryMetaDataFieldValueRepo;
import com.Bootcamp.project.repos.CategoryMetadataFieldRepo;
import com.Bootcamp.project.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CategoryMetadataFieldRepo categoryMetadataFieldRepo;

    @Autowired
    CategoryMetaDataFieldValueRepo categoryMetaDataFieldValueRepo;

    public CategoryMetadataField addField(String name){
        if(categoryMetadataFieldRepo.findByName(name)==null){
            CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
            categoryMetadataField.setName(name);
            return categoryMetadataFieldRepo.save(categoryMetadataField);
        }else {
            throw new ResourceAlreadyExistException("Field Already Exist With name :- "+name);
        }
    }


    public Category addCategory(String name){


        if(categoryRepo.findByName(name)==null){
            Category category = new Category();
            category.setName(name);
            categoryRepo.save(category);
            return category;
        }else {
            throw new ResourceAlreadyExistException("Category Already Exist With name :- "+name);
        }
    }


    public Category addCategory(String name, Long id){


        if(!categoryRepo.findById(id).isPresent()){

            throw new ResourceDoesNotExistException("Parent Category does not exist with id " +id);



        }else {
            if(categoryRepo.findByName(name)!=null){

                throw new ResourceAlreadyExistException("Category Already Exist With name :- "+name);

            }
            Category category = new Category();
            category.setName(name);
            category.setParentCategoryId(id);
            categoryRepo.save(category);
            return category;

        }


//        if(categoryRepo.findById(id).orElse(null) == null&&categoryRepo.findById(id).orElse(null).getParentCategoryId()==null)
//            throw new ResourceDoesNotExistException("Parent Category does not exist with id " +id);
//        if(categoryRepo.findByName(name)==null && categoryRepo.findById(id).orElse(null).getParentCategoryId()==null){
//            Category category = new Category();
//            category.setName(name);
//            category.setParentCategoryId(id);
//            categoryRepo.save(category);
//            return category;
//        }else {
//            throw new ResourceAlreadyExistException("Category Already Exist With name :- "+name);
//        }
    }
    public Map<String, List<CategoryDTO>> viewCategoryById(Long id) {
        if(categoryRepo.findById(id)==null)
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        Map<String,List<CategoryDTO>> map = new HashMap<>();
        Category category = categoryRepo.findById(id).orElse(null);
        if(category.getParentCategoryId()==null){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(category.getName());
            categoryDTO.setParentId(category.getParentCategoryId());
            map.put("Root Category", Arrays.asList(categoryDTO));
            return map;

        }
        List<Category> childCat = categoryRepo.findAllByParentCategoryId(id);

        List<CategoryDTO> responseDTO = new ArrayList<>();


        for(Category category1 :childCat){

            CategoryDTO childCategoryDTO = new CategoryDTO();
            childCategoryDTO.setName(category1.getName());
            childCategoryDTO.setParentId(category1.getParentCategoryId());
            responseDTO.add(childCategoryDTO);


        }

        map.put("Child Category",responseDTO);


        Category parentCategory = categoryRepo.findById(category.getParentCategoryId()).orElse(null);
        CategoryDTO newCategoryDTO = new CategoryDTO();
        newCategoryDTO.setName(parentCategory.getName());
        newCategoryDTO.setParentId(parentCategory.getParentCategoryId());

        List<CategoryDTO> parentCat = new ArrayList<>();
        parentCat.add(newCategoryDTO);

        map.put("Parent Category",parentCat);

        return map;
    }
    public Category viewCategory(Long id){
        Category category = categoryRepo.findById(id).orElse(null);
        if(category==null){
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        }
        return category;
    }
    public Category updateCategory(Long id, String name) {
        Category category = categoryRepo.findById(id).orElse(null);
        if(categoryRepo.findByName(name)!=null)
            throw new ResourceAlreadyExistException("Category Already Exist With name :- "+name);
        if(category!=null){
            category.setName(name);
            categoryRepo.save(category);
            return category;
        }else {
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        }
    }


    @Transactional
    public CategoryFieldValueResDTO addMetadataValue(Long id, Long mid, Set<String> value) {
        if(categoryRepo.findById(id)==null){
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        }
        else if (categoryMetadataFieldRepo.findById(mid)==null){
            throw new ResourceDoesNotExistException("Field does not exist with id " +mid);
        }
        if(categoryMetaDataFieldValueRepo.viewMetadataValues(id, mid)!=null){
            throw new ResourceAlreadyExistException("Field Value Already Exist With Category ID :- "+id+" and Field ID :-"+mid);
        }
        StringBuilder sb=new StringBuilder();
        for (String name : value) {
            sb.append(name);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);

//        CategoryMetadataFieldValues values = new CategoryMetadataFieldValues();
//        Category category = categoryRepo.findById(id).orElse(null);
//        CategoryMetadataField categoryMetadataField = categoryMetadataFieldRepo.findById(mid).orElse(null);
//
//
//
//        values.setCategory(category);
//        values.setCategoryMetadataField(categoryMetadataField);
//        values.setValue(sb.toString());

        categoryMetaDataFieldValueRepo.addMetadataValues(id,mid,sb.toString());

        return new CategoryFieldValueResDTO(id,mid,sb.toString());
    }

    @Transactional
    public CategoryFieldValueResDTO updateMetadataValue(Long id, Long mid, Set<String> value) {
        if(categoryRepo.findById(id)==null){
            throw new ResourceDoesNotExistException("Category does not exist with id " +id);
        }
        if (categoryMetadataFieldRepo.findById(mid)==null){
            throw new ResourceDoesNotExistException("Field does not exist with id " +mid);
        }
        if(categoryMetaDataFieldValueRepo.viewMetadataValues(id, mid)==null){
            throw new ResourceAlreadyExistException("Field Value does not Exist With Category ID :- "+id+" and Field ID :-"+mid);
        }
        List<Object[]> result = categoryMetaDataFieldValueRepo.viewMetadataValues2(id, mid);
        Object[] temp = result.get(0);
        String[] str = temp[2].toString().split(",");
        for(String st: str){
            value.add(st);
        }
        StringBuilder sb=new StringBuilder();
        for (String name : value) {
            sb.append(name);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        categoryMetaDataFieldValueRepo.updateMetadataValues(id,mid,sb.toString());
        return new CategoryFieldValueResDTO(id,mid,sb.toString());
    }
}