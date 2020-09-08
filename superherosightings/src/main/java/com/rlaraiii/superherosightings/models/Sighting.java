/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.models;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author R Lara
 */
public class Sighting {
    
    private int id;
    
    @Min(value = 1, message = "A hero is needed for a sighting")
    private int heroId;
    
    @Min(value = 1, message = "A location is needed for a sighting")
    private int locationId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Sighting date/time cannot be empty")
    @Past(message = "Sighting date/time cannot be in the future")
    private LocalDateTime date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + this.heroId;
        hash = 97 * hash + this.locationId;
        hash = 97 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.heroId != other.heroId) {
            return false;
        }
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sighting{" + "id=" + id + ", heroId=" + heroId + ", locationId=" + locationId + ", date=" + date + '}';
    }
    
    
    
}
