package com.example.ladium.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Builder
@Table(name = "user")
public class User_info {
    @JsonIgnore
    @Id
    @Column(name = "user_id", nullable = false)
    private String user_id;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
}
