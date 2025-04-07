package it.aulab.progettofinale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aulab.progettofinale.models.CareerRequest;
import it.aulab.progettofinale.models.User;
import it.aulab.progettofinale.repositories.CareerRequestRepository;



import java.util.List;

@Service
public class CareerRequestServiceImpl  implements CareerRequestService {

    @Autowired
    private CareerRequestRepository careerRequestRepository;

    @Autowired
    private EmailService emailService;

    
    @Transactional
    public boolean isRoleAlreadyAssigned(User user, CareerRequest careerRequest) {
        List<Long> allUsersIds = careerRequestRepository.findAllUsersIds();

        if (allUsersIds.contains(user.getId())) {
            return false;
        }

        List<Long> requests = careerRequestRepository.findByUserId(user.getId());

        return requests.stream().anyMatch(roleId -> roleId.equals(careerRequest.getRole().getId()));
}

    public void save(CareerRequest careerRequest, User user) {
        careerRequest.setUser(user);
        careerRequest.setIsChecked(false);
        careerRequestRepository.save(careerRequest);

        // invio richiesta del ruolo tramite mail  ad admin 
        emailService.sendSimpleEmail("adminAulabpost@admin.com", "Richiesta per ruolo" + careerRequest.getRole().getName(), "C'è una richiesta di collaborazione da parte di " + user.getUsername());
    }

    @Override
    public void careerAccept(Long requestId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'careerAccept'");
    }

    @Override
    public CareerRequest find(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

}