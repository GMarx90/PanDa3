package com.example.panda3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be null")
    private String city;

    @NotBlank(message = "Name cannot be null")
    private String road;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be in the format xx-xxx")
    private String postCode;




    public void setCity(String city) {
       if(city!=null){
        this.city = city;
    }else {
           this.city ="CITY";
           System.out.println("City name cannot be null");
       }}

    public void setRoad(String road) {
        if (road!=null){
        this.road = road;
    }else {
            this.road = "ROAD";
            System.out.println("Road name cannot be null");
        }}
    public void setPostCode(String postCode) {
        if (postCode!=null){
        this.postCode = postCode;}
        else{
            this.postCode= "POSTCODE";
        }
            System.out.println("PostCode number cannot be null");
    }
}
