package com.imparagiocando.imparagiocando.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findCustomUserByUsername(String username);
    MyUser save(MyUser customUser);

}

