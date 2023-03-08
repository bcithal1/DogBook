package dogbook.service;

import dogbook.model.Provider;

import java.util.List;

public interface ProviderService {
    Provider createProvider(Provider provider);
    List<Provider> getAllProvidersByUserId(Integer id);
}
