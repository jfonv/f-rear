package com.chyld.controllers;

import com.chyld.entities.Exercise;
import com.chyld.entities.Profile;
import com.chyld.entities.User;
import com.chyld.security.JwtToken;
import com.chyld.services.ExerciseService;
import com.chyld.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    private ExerciseService  exerciseService;
    private UserService  userService;

    @Autowired
    public void setExercise(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }
    @Autowired
    public void setUser(UserService userService) {
        this.userService = userService; }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createExercise (@RequestBody Exercise exercise, Principal user) {
        int uid = ((JwtToken)user).getUserId();
        User u = userService.findUserById(uid);

        exercise.setUser(u);
        exerciseService.create(exercise);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public Iterable<Exercise> index(@RequestParam(name = "page", required = false, defaultValue = "0") int page){
        int uid = 0;
        return exerciseService.findByUserId(uid, page);
    }
}
