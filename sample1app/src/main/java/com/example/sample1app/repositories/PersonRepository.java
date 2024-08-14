package com.example.sample1app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sample1app.Person;

import java.util.List;
import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT d FROM Person d ORDER BY d.name")
    List<Person> findAllOrderByName();

    @Query("SELECT d FROM Person d WHERE age > :min and age < :max")
    List<Person> findByAge(@Param("min") int min, @Param("max") int max);

    public Optional<Person> findById(Long name);
    public List<Person> findByNameLike(String name);
    public List<Person> findByIdIsNotNullOrderByIdDesc();
    public List<Person> findByAgeGreaterThan(Integer age);
    public List<Person> findByAgeBetween(Integer age1, Integer age2);
}
