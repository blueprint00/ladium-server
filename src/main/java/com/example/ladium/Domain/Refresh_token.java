package com.example.ladium.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "refresh_token")
public class Refresh_token {
    @Id
    @Column(name = "refresh_key", nullable = false)
    private String refresh_key;
    @Column(name = "refresh_value", nullable = false)
    private String refresh_value;

    public Refresh_token updateValue(String token){
        this.refresh_value = token;
        return this;
    }
}
