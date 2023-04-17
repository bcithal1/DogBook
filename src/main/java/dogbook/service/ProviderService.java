package dogbook.service;

import dogbook.model.Provider;
import dogbook.repository.ProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepo providerRepo;

    public Provider createProvider(Provider provider) {
        return providerRepo.save(provider);
    }

    public List<Provider> getAllProvidersByUserId(Integer userId) {
        return providerRepo.findAllByUserId(userId);
    }
}
