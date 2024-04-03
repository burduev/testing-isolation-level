package com.jedi.isolationlevel.isolation;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;
}
