package net.atos.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.atos.demo.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	Person findByUserName(String userName);
}