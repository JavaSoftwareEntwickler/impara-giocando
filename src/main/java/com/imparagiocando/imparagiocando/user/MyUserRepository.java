package com.imparagiocando.imparagiocando.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findMyUserByUsername(String username);
    MyUser save(MyUser customUser);
}

