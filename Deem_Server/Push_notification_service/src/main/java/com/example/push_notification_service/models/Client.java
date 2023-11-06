package com.example.push_notification_service.models;

import com.example.push_notification_service.config.PersonDetails;
import javax.websocket.Session;

public class Client {

    private Session session;
    private PersonDetails personDetails;

    public Client(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public PersonDetails getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }
}
