package com.example.capstine_2.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer railwaysId;
    @NotEmpty
    @Size(min = 1, max = 50)
    @Column(columnDefinition = "varchar(50) not null unique")
    private String name;
    @NotEmpty
    @Size(min = 1, max = 50)
    @Column(columnDefinition = "varchar(50) not null")
    private String location;
    @NotNull
    @PositiveOrZero
    @Column(columnDefinition = "int not null")
    private int tripCapacity;

















}
