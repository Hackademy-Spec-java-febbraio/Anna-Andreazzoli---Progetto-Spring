package it.aulab.progettofinale.services;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}
