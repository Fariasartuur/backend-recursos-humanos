package com.artuur.hrms.entities;


import com.artuur.hrms.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tb_time_records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "time_record_id")
    private UUID timeRecordId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime entryHour;
    private LocalTime exitHour;

    private LocalTime breakStart;
    private LocalTime breakEnd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private boolean validated = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
