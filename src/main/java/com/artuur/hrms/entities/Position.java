package com.artuur.hrms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_positions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Long positionId;

    @Column(nullable = false)
    private String title;

    private Double salaryRangeMin; // Opcional: para validar salários
    private Double salaryRangeMax;
}
