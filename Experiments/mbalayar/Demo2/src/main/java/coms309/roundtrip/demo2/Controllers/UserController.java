package coms309.roundtrip.demo2.Controllers;

import coms309.roundtrip.demo2.Model.Users;
import coms309.roundtrip.demo2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("users/all")
    List<Users> GetAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("users/post/info/{u}/{n}/{e}/{p}")
    Users PostUserByPath(@PathVariable String u, @PathVariable String n, @PathVariable String e, @PathVariable String p) {
        Users newUser = new Users();
        newUser.setUsername(u);
        newUser.setName(n);
        newUser.setEmail(e);
        newUser.setPassword(p);
        userRepository.save(newUser);
        return newUser;
    }

    @PostMapping("users/post")
    Users PostUserByPath(@RequestBody Users newUser) {
        userRepository.save(newUser);
        return newUser;
    }

}
