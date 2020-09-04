package com.company.ksena.service.google_api_service;

import com.company.ksena.entity.api.google_api.GeocodeResponse;

public interface GoogleApiService {
    String NAME = "ksena_GoogleApiService";

    void init();

    GeocodeResponse getGeocodeByCoordinates(String apiKey, double lat, double lon);

    GeocodeResponse getGeocodeByAddress(String apiKey, String address);

}