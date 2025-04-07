package it.aulab.progettofinale.services;

import it.aulab.progettofinale.models.CareerRequest;
import it.aulab.progettofinale.models.User;

public interface CareerRequestService {
    boolean isRoleAlreadyAssigned(User user, CareerRequest careerRequest);
    void save(CareerRequest careerRequest, User user);
    void careerAccept(Long requestId);
    CareerRequest find(Long id);
}
