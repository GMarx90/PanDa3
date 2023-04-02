package com.example.panda3.factory;

import com.example.panda3.entity.User;

public class Facotry {

    public User createUserWithProperFiled(){
        Long id=1L;
        User user= new User(id, "Piotr", "Kowalski", "piotrkowalski@gmail.com", "Password12@3");
        return user;
    }

    public User createUserWithWrongPassword(){
        Long id=1L;
        User user= new User(id, "Piotr", "Kowalski", "piotr.kowalski@gmail.com", "Password");
        return user;
    }

    public User createUserWithWrongMailAndPassword(){
        Long id=1L;
        User user= new User(id, "Piotr", "Kowalski", "piotr.kowalski.gmail.com", "Password");
        return user;
    }
    public User createUserWithWrongMail(){
        Long id=1L;
        User user= new User(id, "Piotr", "Kowalski", "piotr.kowalski.gmail.com", "Password!@#123");
        return user;
    }

    public User createUserWithoutName(){
        Long id=1L;
        User user= new User(id, "", "Kowalski", "piotr.kowalski.gmail.com", "Password!@#123");
        user.setFirstName(null);
        return user;
    }
    public User createUserWithToLongNameAndSurname(){
        Long id=1L;
        User user= new User(
                id, "PiotasdadasdasfasfasfasfafasfasKoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalskifafsasfar",
                "KoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalskiKoasfafafafafafafafasfasfasfasfasfafwalski",
                "piotr.kowalski@gmail.com",
                "Password!@#123");
        return user;
    }
}
