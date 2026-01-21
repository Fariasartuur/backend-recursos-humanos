package com.artuur.hrms.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long statusId;

    @Column(unique = true, nullable = false)
    private String name;

    public enum Values {

        ACTIVE(1L),
        AWAY(2L),
        DISMISSED(3L);

        long statusId;

        Values(long statusId){
            this.statusId = statusId;
        }

        public long getStatusId() {
            return statusId;
        }
    }
}
