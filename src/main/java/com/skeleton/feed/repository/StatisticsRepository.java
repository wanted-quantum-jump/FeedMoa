package com.skeleton.feed.repository;


import com.skeleton.feed.entity.PostHashtag;
import com.skeleton.feed.entity.Statistics;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query("SELECT s FROM Statistics s WHERE s.postTime BETWEEN :start AND :end")
    List<Statistics> findByTimeRange(@Param("start") Date start, @Param("end") Date end);
}