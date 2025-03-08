package com.travel.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TripDto {
    @NotBlank
    private String title;
    @NotBlank
    private String destination;
    @NotBlank
    private String description;
    @Positive
    private double price;
}
