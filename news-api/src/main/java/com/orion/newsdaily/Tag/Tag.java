package com.orion.newsdaily.Tag;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
