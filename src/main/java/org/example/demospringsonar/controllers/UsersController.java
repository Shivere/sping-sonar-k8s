package org.example.demospringsonar.controllers;

import org.example.demospringsonar.entities.Users;
import org.example.demospringsonar.interfaces.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping("/hardcoded")
    public List<UsersController.User> getUsers() {
        return Arrays.asList(
                new UsersController.User("John", "john@ncba.com"),
                new UsersController.User("Jane", "jane@ncba.com")
        );
    }

    public static class User {
        private String name;
        private String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }
}
