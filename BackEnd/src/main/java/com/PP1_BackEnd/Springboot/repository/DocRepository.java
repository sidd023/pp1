package com.PP1_BackEnd.Springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.PP1_BackEnd.Springboot.model.Doc;


@Repository
public interface DocRepository  extends JpaRepository<Doc,String>{

}