package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
@EnableWebSecurity
@Slf4j
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{id}")
    public String updateImageUser(@RequestParam String name, @RequestParam MultipartFile photo,
                                  @AuthenticationPrincipal User user,Model model) throws IOException {
        User userInDB = userRepository.findByUsername(user.getUsername());
        userInDB.setPhoto(photo.getBytes());
        userRepository.save(userInDB);
        model.addAttribute("currentUser",user);
        return "profile/userProfile";
    }


    @GetMapping("/{id}")
    public String getProfileUser(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("currentUser", user);
        return "profile/userProfile";
    }

    @GetMapping("/{id}/image")
    public String updateProfileUser(@AuthenticationPrincipal User user,Model model){
        model.addAttribute("currentUser",user);
        return "profile/updateImage";
    }
}
