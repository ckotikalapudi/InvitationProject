package com.example.restful_api.repository;

import com.example.restful_api.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface GuestRepository extends JpaRepository<Guest, Integer>{}