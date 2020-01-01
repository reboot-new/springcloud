package com.tan.springcloud2producer.repository;

import com.tan.springcloud2producer.entity.RainFall;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RainFallRepository extends JpaRepository<RainFall,Integer> {

    @Query(value = "SELECT u FROM RainFall u WHERE id=:id")
    RainFall findById(@Param("id") int id);
}
