package com.example.be.repository;

import com.example.be.model.TestQuizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface TestQuizzRepository extends JpaRepository<TestQuizz, Long> {

    TestQuizz findTestQuizzById(Long id);

    List<TestQuizz> findAllTestQuizzBySubjectId(Long id);

    @Query("SELECT u FROM TestQuizz u WHERE u.activationCode = :code")
    TestQuizz findQuizzByCode(@Param("code") String code);

}