package com.samueldev.project.user.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "activation")
public class Activation {
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Id
    private UUID id;

    @OneToOne
    private User user;

    public Activation(User user) {
        this.user = user;
    }

}
