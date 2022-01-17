package ru.bootjava.graduating.restaurantsvoting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Restaurant extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @Size(max = 128)
    @NotEmpty
    private String name;

    @OneToOne
    @JoinColumn(name = "lunch_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lunch lunch;
}