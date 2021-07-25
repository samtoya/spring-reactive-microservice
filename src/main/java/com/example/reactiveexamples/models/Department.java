package com.example.reactiveexamples.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("department")
public class Department {
    @Id
    private int id;
    private String name;
    @Column("user_id")
    private int userId;
    private String loc;
}
