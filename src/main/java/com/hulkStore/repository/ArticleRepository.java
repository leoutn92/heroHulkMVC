package com.hulkStore.repository;

import com.hulkStore.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {
}
