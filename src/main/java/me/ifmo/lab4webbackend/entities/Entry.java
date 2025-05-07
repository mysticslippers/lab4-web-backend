package me.ifmo.lab4webbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@Table(name = "entry")
public class Entry {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entry_id_seq")
    @SequenceGenerator(name = "entry_id_seq", sequenceName = "entry_id_seq", allocationSize = 1)
    private long id;

    private double x;
    private double y;
    private double r;
    private boolean isResult;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Entry(double x, double y, double r, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.user = user;
        evaluateResult();
    }

    private void evaluateResult() {
        this.isResult = isWithinTriangle() || isWithinRectangle() || isWithinCircle();
    }

    private boolean isWithinTriangle() {
        return (this.x <= 0 && this.y <= 0 && this.y >= (-this.x - this.r));
    }

    private boolean isWithinRectangle() {
        return (this.x <= 0 && this.y >= 0 && this.x <= this.r && this.y <= this.r);
    }

    private boolean isWithinCircle() {
        return (this.x >= 0 && this.y >= 0 && (this.x * this.x + this.y * this.y <= (this.r * this.r) / 4));
    }
}
