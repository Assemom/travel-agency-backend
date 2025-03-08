package com.travel.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewDto {
    private Long tripId;
    private Long packageId;
    @NotBlank
    private String text;
}
