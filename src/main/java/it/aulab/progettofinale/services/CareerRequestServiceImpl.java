package it.aulab.progettofinale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aulab.progettofinale.models.CareerRequest;
import it.aulab.progettofinale.models.Role;
import it.aulab.progettofinale.models.User;
import it.aulab.progettofinale.repositories.CareerRequestRepository;
import it.aulab.progettofinale.repositories.RoleRepository;
import it.aulab.progettofinale.repositories.UserRepository;



import java.util.List;

@Service
public class CareerRequestServiceImpl  implements CareerRequestService {

    @Autowired
    private CareerRequestRepository careerRequestRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    
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
        emailService.sendSimpleEmail("adminAulabpost1@admin.com", "Richiesta per ruolo" + careerRequest.getRole().getName(), "C'è una richiesta di collaborazione da parte di " + user.getUsername());
    }

    @Override
    public void careerAccept(Long requestId) {
       //recupero richiesta
       CareerRequest request = careerRequestRepository.findById(requestId).get();

       //dalla richiesta estraggo utante richiedente e ruolo richiesto 
        User user = request.getUser();
        Role role = request.getRole();

        //recupero tutti ruoli che utente già possiede e aggiungo il nuovo ruolo
        List<Role> rolesUser = user.getRoles();
        Role newRole = roleRepository.findByName(role.getName());
        rolesUser.add(newRole);

        //salvo tutte le nuove modifiche 
        user.setRoles(rolesUser);
        userRepository.save(user);
        request.setIsChecked(true);
        careerRequestRepository.save(request);

        emailService.sendSimpleEmail( user.getEmail(), "Ruolo abilitato", "Ciao , la tua richiesta di collaborazione è stata accettata dalla nostra amministrazione");

    }

    @Override
    public CareerRequest find(Long id) {
        return careerRequestRepository.findById(id).get();
    }

}