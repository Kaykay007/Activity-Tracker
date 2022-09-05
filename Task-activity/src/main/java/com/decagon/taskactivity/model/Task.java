package com.decagon.taskactivity.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String title;
    private  String description;

    @Column (name= "status")
    private  String status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private  LocalDateTime updateAt;

    @Column(nullable = true)
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")

    private User user;


}
