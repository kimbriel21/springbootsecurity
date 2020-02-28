package com.spring.security.security.initialize;

import com.spring.security.security.repository.model.User;
import com.spring.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DBInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("userkim",passwordEncoder.encode("userkim"),"USER","ACCESS_TEST1");
        User userAdmin = new User("adminkim",passwordEncoder.encode("adminkim"),"ADMIN","ACCESS_TEST1,ACCESS_TEST2");

        List<User> users = Arrays.asList(user,userAdmin);
        userRepository.saveAll(users);
    }
}
