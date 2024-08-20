package com.example.capstine_2.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Railways {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Size(min = 2, max = 50)
    @Column(columnDefinition = "varchar(50) not null unique")
    private String name;
    @NotNull
    @Positive
    @Column(columnDefinition = "int not null")
    private int stationsNumber;
    @NotEmpty
    @Size(min = 2 , max = 25)
    @Column(columnDefinition = "varchar(25) not null")
    private String country;







}
