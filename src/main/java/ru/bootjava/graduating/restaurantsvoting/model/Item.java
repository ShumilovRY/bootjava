package ru.bootjava.graduating.restaurantsvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Item extends BaseEntity {

    @Column(name = "dish_name", nullable = false)
    @NotEmpty
    @Size(max = 128)
    private String dishName;

    @Column(name = "price", nullable = false)
    @NotNull
    private Double price;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lunch_id", referencedColumnName = "id")
    private Lunch lunch;
}
