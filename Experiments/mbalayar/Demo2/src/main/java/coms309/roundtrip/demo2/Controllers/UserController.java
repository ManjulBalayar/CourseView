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

    @PostMapping("users/post/info/{n}/{e}")
    Users PostUserByPath(@PathVariable String n, @PathVariable String e) {
        Users newUser = new Users();
        newUser.setName(n);
        newUser.setEmail(e);
        userRepository.save(newUser);
        return newUser;
    }

    @PostMapping("users/post")
    Users PostUserByPath(@RequestBody Users newUser) {
        userRepository.save(newUser);
        return newUser;
    }

}
