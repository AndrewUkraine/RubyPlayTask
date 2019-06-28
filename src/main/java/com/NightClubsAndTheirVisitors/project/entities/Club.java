package com.NightClubsAndTheirVisitors.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "night_club_name", length = 30, nullable = false)
    @NotBlank(message = "Must by not empty")
    private String nightClubName;

    @Column(name = "quantity_Of_Visits", length = 30, nullable = false)
    private int quantityOfVisits;

    public Club() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNightClubName() {
        return nightClubName;
    }

    public void setNightClubName(String nightClubName) {
        this.nightClubName = nightClubName;
    }

    public int getQuantityOfVisits() {
        return quantityOfVisits;
    }

    public void setQuantityOfVisits(int quantityOfVisits) {
        this.quantityOfVisits = quantityOfVisits;
    }
}
