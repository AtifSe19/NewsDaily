package com.orion.newsdaily.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {
    // You can add custom queries here if needed
}
