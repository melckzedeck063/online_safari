package com.example.online_safari.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "safaris")
@SQLDelete(sql = "UPDATE safaris SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class Safari extends BaseEntity {
    @Column(name = "from")
    private String from;

    @Column(name = "destination")
    private String destination;

    @Column(name = "price")
    private String price;

    @Column(name = "departure")
    private String departure;

    @ManyToOne
    @JoinColumn(name = "addedBy", referencedColumnName = "uuid")
    private UserAccount addedBy;
}
