package com.example.reactiveexamples.repositories;

import com.example.reactiveexamples.models.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    @Query("SELECT * FROM user WHERE age >= $1")
    Flux<User> findByAge(Integer age);
}
