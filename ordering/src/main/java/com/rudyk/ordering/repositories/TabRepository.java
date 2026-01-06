package com.rudyk.ordering.repositories;




import com.rudyk.ordering.domain.model.Tab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabRepository extends JpaRepository<Tab, Long> { }