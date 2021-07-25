package com.example.reactiveexamples.services;

import com.example.reactiveexamples.dto.UserDepartmentDTO;
import com.example.reactiveexamples.models.Department;
import com.example.reactiveexamples.models.User;
import com.example.reactiveexamples.repositories.DepartmentRepository;
import com.example.reactiveexamples.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.function.BiFunction;

@Service
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public UserService(
            UserRepository userRepository,
            DepartmentRepository departmentRepository
    ) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public Mono<User> create(User user) {
        return userRepository.save(user);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    public Mono<User> updateUser(Integer userId, User user) {
        return userRepository.findById(userId)
                .flatMap(dbUser -> {
                    dbUser.setAge(user.getAge());
                    dbUser.setName(user.getName());
                    dbUser.setSalary(user.getSalary());

                    return userRepository.save(dbUser);
                });
    }

    public Mono<Void> deleteUser(Integer userId) {
        return userRepository.deleteById(userId);
    }

    public Flux<User> findUserByAge(Integer age) {
        return userRepository.findByAge(age);
    }

    public Flux<User> fetchUsers(List<Integer> ids) {
        return Flux.fromIterable(ids)
                .parallel()
                .runOn(Schedulers.boundedElastic())
//                .runOn(Schedulers.elastic())
                .flatMap(this::findById)
                .ordered((u1, u2) -> u2.getId() - u1.getId());
    }

    public Mono<Department> getDepartmentByUserId(Integer userId) {
        return departmentRepository.findByUserId(userId);
    }

    public Mono<UserDepartmentDTO> fetchUserAndDepartment(Integer userId) {
        Mono<User> user = findById(userId);
        Mono<Department> department = getDepartmentByUserId(userId)
                .subscribeOn(Schedulers.boundedElastic());
//                .subscribeOn(Schedulers.elastic());
        return Mono.zip(user, department, userDepartmentDTOBiFunction);
    }

    private final BiFunction<User, Department, UserDepartmentDTO> userDepartmentDTOBiFunction = (x1, x2) -> UserDepartmentDTO.builder()
            .age(x1.getAge())
            .departmentId(x2.getId())
            .departmentName(x2.getName())
            .userName(x1.getName())
            .userId(x1.getId())
            .loc(x2.getLoc())
            .salary(x1.getSalary()).build();

}
