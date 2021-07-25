package com.example.reactiveexamples.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("users")
public class User {
    @Id
    private int id;
    private String name;
    private int age;
    private double salary;
}
