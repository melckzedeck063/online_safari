package com.example.online_safari.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SafariDto {
    private String startPoint;
    private String destination;
    private String price;
    private String departure;
}
