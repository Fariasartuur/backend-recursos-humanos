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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "position_id")
    private UUID positionId;

    @Column(nullable = false)
    private String title;

    private Double salaryRangeMin;
    private Double salaryRangeMax;

    @Column(nullable = false)
    private Boolean active = true;
}
