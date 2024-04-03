package com.jedi.isolationlevel.isolation;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
}
