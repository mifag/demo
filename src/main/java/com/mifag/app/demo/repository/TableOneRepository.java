package com.mifag.app.demo.repository;

import com.mifag.app.demo.entity.TableOne;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TableOneRepository extends CrudRepository<TableOne, Long> {
    @Query("SELECT t FROM TableOne t WHERE t.id = :ass AND t.name = :piss")
    TableOne getMeTableOneYouFuck(@Param("ass") Long qqq, @Param("piss") String www);
}
