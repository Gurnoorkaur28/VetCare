package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.Vet;
import au.edu.rmit.sept.webapp.repository.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetService {

    @Autowired
    private VetRepository vetRepository;

    public Vet findByEmail(String email) {
        return vetRepository.findByEmail(email);
    }

    public List<Vet> findAll() {
        return vetRepository.findAll();
    }

    public Vet findById(Long id) {
        return vetRepository.findById(id).orElse(null);
    }

    public void save(Vet vet) {
        vetRepository.save(vet);
    }
}