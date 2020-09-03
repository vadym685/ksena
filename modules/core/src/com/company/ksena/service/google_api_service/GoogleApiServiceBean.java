package com.company.ksena.service.google_api_service;

import com.company.ksena.entity.api.google_api.GeocodeResponse;
import com.company.ksena.entity.server_constants.ServerConstants;
import com.company.ksena.service.RepoService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@Service(GoogleApiService.NAME)
public class GoogleApiServiceBean implements GoogleApiService {

    private Logger LOG = LoggerFactory.getLogger(GoogleApiServiceBean.class);

    private IGoogleApi api = null;
    private long timeOut = 60;

    @Inject
    private RepoService repository;


    public void init() {
        ServerConstants constants = repository.getServerConstants();
        String baseApiUrl = constants.getGoogleUrl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseApiUrl)
                .client(createClient())
//                            .addConverterFactory(JacksonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(IGoogleApi.class);
    }

    private OkHttpClient createClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder
                .connectTimeout(TimeUnit.SECONDS.toMillis(timeOut), TimeUnit.SECONDS)
                .writeTimeout(TimeUnit.SECONDS.toMillis(timeOut), TimeUnit.SECONDS)
                .readTimeout(TimeUnit.SECONDS.toMillis(timeOut), TimeUnit.SECONDS);

        return clientBuilder.build();
    }

    public GeocodeResponse getGeocodeByCoordinates(String apiKey, double lat, double lon) {
        String latLon = String
                .format("%.6f|%.6f", lat, lon)
                .replace(",", ".")
                .replace("|", ",");

        LOG.info(latLon);

        Call<GeocodeResponse> call = api.getGeocodeByLatLon(apiKey, latLon);

        GeocodeResponse geocode = null;
        try {
            Response<GeocodeResponse> response = call.execute();
            if (response.isSuccessful()) {
                geocode = response.body();
                if (geocode != null)
                    return geocode;
            } else {
                ResponseBody errorBody = response.errorBody();
                String resp = errorBody.string();
                LOG.error(String.format("\nResponse:%s\nCode:%d", resp, response.code()));
            }
        } catch (Exception ex) {
            LOG.error("", ex);
        }

        return geocode;
    }

    public GeocodeResponse getGeocodeByAddress(String apiKey, String address) {
        LOG.info(address);
        ServerConstants constants = repository.getServerConstants();
        String baseApiUrl = constants.getGoogleUrl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseApiUrl)
                .client(createClient())
//                            .addConverterFactory(JacksonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(IGoogleApi.class);
        Call<GeocodeResponse> call = api.getGeocodeByAddress(apiKey, address);

        GeocodeResponse geocode = null;
        try {
            Response<GeocodeResponse> response = call.execute();
            if (response.isSuccessful()) {
                geocode = response.body();
                if (geocode != null)
                    return geocode;
            } else {
                ResponseBody errorBody = response.errorBody();
                String resp = errorBody.string();
                LOG.error(String.format("\nResponse:%s\nCode:%d", resp, response.code()));
            }
        } catch (Exception ex) {
            LOG.error("", ex);
        }

        return geocode;
    }

}