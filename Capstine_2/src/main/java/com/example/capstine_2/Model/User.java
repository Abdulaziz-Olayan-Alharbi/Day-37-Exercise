package com.example.capstine_2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Size(min = 2, max = 50)
    @Column(columnDefinition = "varchar(50) not null")
    private String name;
    @NotNull
    @Min(16)
    @Max(100)
    @Column(columnDefinition = "int not null")
    private int age;
    @NotEmpty
    @Email
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;
    @NotNull
    @PositiveOrZero
    @Column(columnDefinition = "double not null")
    private double balance;
    @NotNull
    @Column(columnDefinition = "double not null")
    private double points;
}
