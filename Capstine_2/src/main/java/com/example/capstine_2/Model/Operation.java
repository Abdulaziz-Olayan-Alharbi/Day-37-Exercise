package com.example.capstine_2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer userId;
    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer ticketId;
    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer tripId;
    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer stationFromId;
    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer stationToId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateTime = LocalDateTime.now();
    @Pattern(regexp = "^(Buy|Return)$")
    @Column(columnDefinition = "varchar(10) not null")
    private String type;
    @NotNull
    @PositiveOrZero
    @Column(columnDefinition = "double not null")
    private double price;
    @NotNull
    @Column(columnDefinition = "double not null")
    private double points;
}
