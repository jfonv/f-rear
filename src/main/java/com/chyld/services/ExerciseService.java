package com.chyld.services;

import com.chyld.entities.Exercise;
import com.chyld.entities.User;
import com.chyld.repositories.IExerciseRepository;
import com.chyld.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {
    IExerciseRepository repository;

    @Autowired
    public void setRepository(IExerciseRepository repository) {
        this.repository = repository;
    }


    public Page<Exercise> findByUserId(int uid, int page) {
        PageRequest pr = new PageRequest(page, 3);
        return this.repository.findByUserId(uid, pr);
    }

    public Exercise create(Exercise exercise) {
        return this.repository.save(exercise);
    }

    public void destroy(int id) {
        this.repository.delete(id);
    }

}
