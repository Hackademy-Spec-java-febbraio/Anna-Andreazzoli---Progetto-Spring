package it.aulab.progettofinale.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import it.aulab.progettofinale.repositories.ArticleRepository;
import it.aulab.progettofinale.repositories.CareerRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NotificationInterceptor implements HandlerInterceptor {

    @Autowired
    private CareerRequestRepository careerRequestRepository;

    @Autowired
    ArticleRepository articleRepository;
    

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
                            ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && request.isUserInRole("ROLE_ADMIN")) {
            int careerCont = careerRequestRepository.findByIsCheckedFalse().size();
            modelAndView.addObject("careerRequests", careerCont);
        }
        if (modelAndView != null && request.isUserInRole("ROLE_REVISOR")) {
            int revisedCont = articleRepository.findByIsAcceptedIsNull().size();
            modelAndView.addObject("articlesToBeRevised", revisedCont);
        }
    }
}
