package com.chyld.repositories;

import com.chyld.entities.Exercise;
import com.chyld.entities.User;
import com.chyld.enums.ExerciseEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IExerciseRepository extends PagingAndSortingRepository<Exercise, Integer>
{
    @Query("select distinct e from User u join u.exercises e where u.id = :id")
    public Page<Exercise> findByUserId(@Param("id") int id, Pageable pageable);


    public Exercise findByExercise(ExerciseEnum exercise);


}

/*
public interface IActorRepository extends PagingAndSortingRepository<Actor, Integer> {
    @Query("select distinct m from Movie m join m.actors a where a.id = :id")
    public Page<Movie> findAllMoviesByActorId(@Param("id") int id, Pageable pageable);

    @Query("select distinct s from Movie m join m.actors a join m.studio s where a.id = :id")
    public Page<Studio> findAllStudiosByActorId(@Param("id") int id, Pageable pageable);
}

 */