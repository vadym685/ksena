package com.company.ksena.service.google_calendar_api_service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleCalendarService {
    String NAME = "ksena_GoogleCalendarService";

    Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException;

    String getEvents(String summary, String location, String description, String startDateTime, String endDateTime, String email, String timeZone) throws IOException, GeneralSecurityException;

    void removeEvent(String eventId) throws IOException, GeneralSecurityException;
}