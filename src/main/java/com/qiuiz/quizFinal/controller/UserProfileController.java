package com.qiuiz.quizFinal.controller;

import com.qiuiz.quizFinal.model.HistoryGames;
import com.qiuiz.quizFinal.model.User;
import com.qiuiz.quizFinal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@EnableWebSecurity
@Slf4j
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder encoder;

    public UserProfileController(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @RequestMapping(value = "/{id}", params = {"UpdateAboutUser"})
    public String updateAboutUser(@RequestParam(required = false) String newAboutUser,final User user,
                                  @AuthenticationPrincipal User autUser, Model model){
        User userInDB = userRepository.findByUsername(autUser.getUsername());
        if(autUser.getUser_about() != null){
            userInDB.setUser_about(user.getUser_about());
        }
        if(newAboutUser != null){
            userInDB.setUser_about(newAboutUser);
        }
        userRepository.save(userInDB);
        log.info("User about: {}", userInDB.getUser_about());
        model.addAttribute("currentUser", userInDB);
        return "profile/userProfile";
    }

    @RequestMapping(value = "/{id}", params = {"UpdatePassword"})
    public String updatePassword(@RequestParam String oldPassword,@RequestParam String confirmPassword ,final User user,
                                 @AuthenticationPrincipal User autUser, Model model){
        if(!confirmPassword.equals(user.getPassword())) {
            log.info("ConfirmPassword: {}", confirmPassword.equals(user.getPassword()));
            log.info("Пароль не изменен");
            model.addAttribute("currentUser",autUser);
            return "profile/userProfile";
        }
        if(encoder.matches(oldPassword, autUser.getPassword())){
            log.info("Пароли совпадают");
            User userInDB = userRepository.findByUsername(autUser.getUsername());
            userInDB.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(userInDB);
        }
        model.addAttribute("currentUser",autUser);
        return "profile/userProfile";
    }


    /**
     * @param name Имя загруженного документа.
     * @param photo Документ выбранный для загрузки.
     * @param user Авторизированный пользователь.
     * @param model Модель для загрузки данных о пользователе на форму.
     * @return Страница пользователя с обновленными данными.
     * @throws IOException Если преобразование невозможно.
     */
    @PostMapping("/{id}")
    public String updateImageUser(@RequestParam String name, @RequestParam MultipartFile photo,
                                  @AuthenticationPrincipal User user,Model model) throws IOException {
        User userInDB = userRepository.findByUsername(user.getUsername());
        userInDB.setPhoto(photo.getBytes());
        userRepository.save(userInDB);
        model.addAttribute("currentUser",userInDB);

        return "profile/userProfile";
    }

    /**
     * @param user Авторизированный пользователь.
     * @param model Модель для загрузки данных о пользователе на форму.
     * @return Страница пользователя с текущим номером (id).
     */
    @GetMapping("/{id}")
    public String getProfileUser(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("currentUser", user);
        for(HistoryGames historyGames : user.getHistoryGames()){
            log.info("History date: {}", historyGames.getDate_quiz().toInstant());
        }
        return "profile/userProfile";
    }

    /**
     * @param user Авторизированный пользователь.
     * @param model Модель для загрузки данных о пользователе на форму.
     * @return Страница для обновления фото.
     */
    @GetMapping("/{id}/image")
    public String updateProfileUser(@AuthenticationPrincipal User user,Model model){
        model.addAttribute("currentUser",user);

        return "profile/updateImage";
    }
}
