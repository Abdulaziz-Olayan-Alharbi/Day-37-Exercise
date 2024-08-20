package com.example.capstine_2.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer tripId;
    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer trainId;
    @NotEmpty
    @Column(columnDefinition = "varchar(4) not null unique")
    private String seat;
    @NotEmpty
    @Pattern(regexp = "^(Standard|First|Business)$")
    @Column(columnDefinition = "varchar(10) not null")
    private String level;
    @NotNull
    @PositiveOrZero
    @Column(columnDefinition = "double not null")
    private double price;
    @Pattern(regexp = "^(Available|Not Available)$")
    @Column(columnDefinition = "varchar(20) not null")
    private String available = "Available";
}
