package com.mka.allcountries.di;

import com.mka.allcountries.model.CountriesService;
import com.mka.allcountries.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void injectRetrofit(CountriesService service);
    void injectInstance(ListViewModel viewModel);
}
