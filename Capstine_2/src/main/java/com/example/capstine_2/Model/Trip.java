package com.example.capstine_2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer railwaysId;
    @NotNull
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer trainId;
    @NotNull
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer stationFromId;
    @NotNull
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer stationToId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime endTime;
    @NotNull
    @Column(columnDefinition = "int not null")
    private int capacity;
    @PositiveOrZero
    @Column(columnDefinition = "int not null")
    private int buyNumber = 0;
    @Pattern(regexp = "^(Canceled|Terminated|Scheduled|In Transit)$")
    @Column(columnDefinition = "varchar(15) not null")
    private String status = "Scheduled";















}
