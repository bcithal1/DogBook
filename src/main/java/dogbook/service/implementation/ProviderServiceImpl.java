package dogbook.service.implementation;

import dogbook.model.Provider;
import dogbook.repository.ProviderRepo;
import dogbook.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderRepo providerRepo;

    @Override
    public Provider createProvider(Provider provider){
        return providerRepo.save(provider);
    }
    @Override
    public List<Provider> getAllProvidersByUserId(Integer userId){
        return providerRepo.findAllByUserId(userId);
    }
}
