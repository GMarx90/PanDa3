package com.example.panda3.factory;

import com.example.panda3.entity.car.VehicleEntity;
import com.example.panda3.entity.user.UserEntity;

public class Facotry {

    public UserEntity createUserWithProperFiled(){
        Long id=1L;
        UserEntity userEntity = new UserEntity(id, "Piotr", "Kowalski", "piotrkowalski@gmail.com", "Password12@3");
        return userEntity;
    }

    public UserEntity createUserWithWrongPassword(){
        Long id=1L;
        UserEntity userEntity = new UserEntity(id, "Piotr", "Kowalski", "piotr.kowalski@gmail.com", "Password");
        return userEntity;
    }

    public UserEntity createUserWithWrongMailAndPassword(){
        Long id=1L;
        UserEntity userEntity = new UserEntity(id, "Piotr", "Kowalski", "piotr.kowalski.gmail.com", "Password");
        return userEntity;
    }
    public UserEntity createUserWithWrongMail(){
        Long id=1L;
        UserEntity userEntity = new UserEntity(id, "Piotr", "Kowalski", "piotr.kowalski.gmail.com", "Password!@#123");
        return userEntity;
    }

    public UserEntity createUserWithoutName(){
        Long id=1L;
        UserEntity userEntity = new UserEntity(id, "", "Kowalski", "piotr.kowalski.gmail.com", "Password!@#123");
        userEntity.setFirstName(null);
        return userEntity;
    }
    public UserEntity createUserWithToLongNameAndSurname(){
        Long id=1L;
        UserEntity userEntity = new UserEntity(
                id, "PiotasdadasdasfasfasfasfafasfasKoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalskifafsasfar",
                "KoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalski",
                "piotr.kowalski@gmail.com",
                "Password!@#123");
        return userEntity;
    }
    public VehicleEntity properVehicleFor5Person(){
        VehicleEntity vehicleEntity =
                new VehicleEntity(1L,
                        "Ford",
                        "Mustang",
                        2020,
                        5,
                        480
                );
    return vehicleEntity;
    }   public VehicleEntity vehicleForMinusPrice(){
        VehicleEntity vehicleEntity =
                new VehicleEntity(1L,
                        "Ford",
                        "Mustang",
                        2020,
                        5,
                        -480
                );
    return vehicleEntity;
    }
    public VehicleEntity vehicleFromFuture(){
        VehicleEntity vehicleEntity =
                new VehicleEntity(1L,
                        "Ford",
                        "Mustang",
                        2030,
                        5,
                        480
                );
    return vehicleEntity;
    }    public VehicleEntity VehicleFor3Person(){
        VehicleEntity vehicleEntity =
                new VehicleEntity(1L,
                        "Ford",
                        "Mustang",
                        2020,
                        3,
                        480
                );
    return vehicleEntity;
    }
}
