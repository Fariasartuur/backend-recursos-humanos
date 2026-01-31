package com.artuur.hrms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_scales")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkScale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "scale_id")
    private UUID scaleId;

    @Column(nullable = false)
    private String name; // Ex: "Escala Administrativa 5x2", "Plantão 12x36"

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    // Ordem dos dias é fundamental, por isso usamos uma List
    @OneToMany(mappedBy = "workScale", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("dayOrder ASC")
    private List<ScaleDayConfig> days;
}
