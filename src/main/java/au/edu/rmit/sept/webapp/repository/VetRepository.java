package au.edu.rmit.sept.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.model.Vet;

public interface VetRepository extends JpaRepository<Vet, Long> {

    Optional<Vet> findByUsername(String username);

    Vet findByEmail(String email);

}