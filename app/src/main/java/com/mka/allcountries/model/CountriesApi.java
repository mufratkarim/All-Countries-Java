package com.mka.allcountries.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CountriesApi {

    String END_URL = "mufratkarim/mufratkarim/main/countries_mka.json";

    @GET(END_URL)
    Single<List<CountryModel>> getCountries();
}
