package com.example.reactiveexamples.repositories;

import com.example.reactiveexamples.models.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DepartmentRepository extends ReactiveCrudRepository<Department, Integer> {
    Mono<Department> findByUserId(Integer userId);
}
