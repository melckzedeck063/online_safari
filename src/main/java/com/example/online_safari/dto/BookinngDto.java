package com.example.online_safari.dto;

import com.example.online_safari.model.UserAccount;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookinngDto {

    private String pickupd_date;
    private String status;
    private String from;
    private String destination;
    private double seats;
    private double totalCost;
    private String safariUuid;

}
