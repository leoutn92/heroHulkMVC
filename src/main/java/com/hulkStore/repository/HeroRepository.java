package com.hulkStore.repository;

import com.hulkStore.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Integer> {
    public List<Hero> findByName(String name);
}
