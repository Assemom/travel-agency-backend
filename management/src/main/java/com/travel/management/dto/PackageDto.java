package com.travel.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PackageDto {
        @NotBlank
        private String name;
        @NotBlank
        private String description;
        @Positive
        private double price;

}
