package net.atos.etax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.atos.etax.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUsername(String username);
}