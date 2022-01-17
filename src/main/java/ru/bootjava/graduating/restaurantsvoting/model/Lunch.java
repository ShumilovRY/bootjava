package ru.bootjava.graduating.restaurantsvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "lunches")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Lunch extends BaseEntity {

    private LocalDate date;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lunch", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Item> items;

    @JsonIgnore
    @OneToOne(mappedBy = "lunch")
    private Restaurant restaurant;
}
