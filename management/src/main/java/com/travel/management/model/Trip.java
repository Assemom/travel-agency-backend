package com.travel.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double price;
    private int duration;
    private String locationLink;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    private boolean available;

    @ManyToMany(mappedBy = "trips") // Bidirectional mapping
    private Set<Package> packages = new HashSet<>();

    // Getters, setters, constructors

    public Trip() {
    }

    public Trip(Long id, String title, String destination, String description, double price, int duration, String locationLink, LocalDateTime createdAt, User createdBy, boolean available, Set<Package> packages) {
        this.id = id;
        this.title = title;
        this.destination = destination;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.locationLink = locationLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.available = available;
        this.packages = packages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLocationLink(String locationLink) {
        this.locationLink = locationLink;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", destination='" + destination + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", locationLink='" + locationLink + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                ", available=" + available +
                ", packages=" + packages +
                '}';
    }
}
