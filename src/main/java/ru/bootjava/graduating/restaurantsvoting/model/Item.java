package ru.bootjava.graduating.restaurantsvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.bootjava.graduating.restaurantsvoting.View;
import ru.bootjava.graduating.restaurantsvoting.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "item", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "local_date"},
        name = "item_unique_title_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    @NoHtml
    private String title;

    @Range(min = 100)
    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "local_date", columnDefinition = "date default now()", nullable = false)
    @NotNull
    private LocalDate localDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

    public Item(Integer id, String title, int price, LocalDate localDate) {
        super(id);
        this.title = title;
        this.price = price;
        this.localDate = localDate;
    }

    public Item(Integer id, String title, int price, LocalDate localDate, Restaurant restaurant) {
        super(id);
        this.title = title;
        this.price = price;
        this.localDate = localDate;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", localDate=" + localDate +
                '}';
    }
}
