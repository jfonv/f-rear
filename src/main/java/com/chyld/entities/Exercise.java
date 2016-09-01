package com.chyld.entities;

import com.chyld.enums.ExerciseEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "exercises")
public class Exercise {
    private int id;
    private int version;
    private ExerciseEnum exercise;
    private int quantity;
    private int durationInMinutes;
    private int calories;
    private Date created;
    private Date modified;
    private User user;

    @Id
    @GeneratedValue
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Version
    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }

    @Enumerated(EnumType.STRING)
    @Column(name="exercise_type", columnDefinition = "ENUM('swim','run','bike','lift')")
    public ExerciseEnum getExercise() { return exercise; }
    public void setExercise(ExerciseEnum exercise) { this.exercise = exercise; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Column(name="duration")
    public int getDurationInMinutes() { return durationInMinutes; }
    public void setDurationInMinutes(int durationInMinutes) { this.durationInMinutes = durationInMinutes; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    @CreationTimestamp
    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }

    @UpdateTimestamp
    public Date getModified() { return modified; }
    public void setModified(Date modified) { this.modified = modified; }

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id")
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}


/*
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL DEFAULT '0',
  `exercise_type` enum('swim','run','bike','lift') NOT NULL,
  `quantity` int(11) DEFAULT '0',
  `duration` int(11) DEFAULT '0',
  `calories` int(11) DEFAULT '0',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,


 */