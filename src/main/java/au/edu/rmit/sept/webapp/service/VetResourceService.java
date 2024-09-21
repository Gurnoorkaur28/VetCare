package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.VetResource;
import au.edu.rmit.sept.webapp.repository.VetResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetResourceService {

    private final VetResourceRepository vetResourceRepository;

    @Autowired
    public VetResourceService(VetResourceRepository vetResourceRepository) {
        this.vetResourceRepository = vetResourceRepository;
    }

    public List<VetResource> getAllResources() {
        return vetResourceRepository.findAll();
    }

    public void addResource(VetResource resource) {
        vetResourceRepository.save(resource);
    }
}
