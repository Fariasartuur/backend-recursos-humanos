package com.artuur.hrms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "tb_scale_day_configs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScaleDayConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "scale_id")
    private WorkScale workScale;

    @Column(nullable = false)
    private Integer dayOrder;

    @Column(nullable = false, name = "is_work_day")
    private Boolean workDay;

    private LocalTime plannedEntry;
    private LocalTime plannedExit;
}
