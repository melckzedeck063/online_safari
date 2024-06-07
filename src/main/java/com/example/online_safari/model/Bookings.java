package com.example.online_safari.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "bookings")
@SQLDelete(sql = "UPDATE bookings SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class Bookings extends BaseEntity {

    @Column(name = "pickup_date")
    private String pickupDate;

    @Column(name = "status")
    private String status;

    @Column(name = "from")
    private String from;

    @Column(name = "destination")
    private String destination;

    @Column(name = "seats")
    private double seats;

    @Column(name = "totalBill")
    private double totalBill;

    @Column(name = "book_number")
    private String bookNumber;

    @ManyToOne
    @JoinColumn(name = "route", referencedColumnName = "uuid")
    private Safari  route;

    @ManyToOne
    @JoinColumn(name = "bookedBy",referencedColumnName = "uuid")
    private UserAccount bookedBy;

    @PrePersist
    public void generateBookNumber(){
        if(getId() != null){
            this.bookNumber = "B.00"+getId();
        }
    }

}
