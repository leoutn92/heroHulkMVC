package com.hulkStore.repository;


import com.hulkStore.model.ArticleSold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSoldRepository extends JpaRepository<ArticleSold, Integer> {
}
