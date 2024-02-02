package org.flowers.project.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "FLOWER")
public class FlowerEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "short_name", nullable = false)
    private String shortName;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "color")
    private String color;
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @LastModifiedDate
    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate;

}