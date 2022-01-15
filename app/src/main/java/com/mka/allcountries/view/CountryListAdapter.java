package com.mka.allcountries.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mka.allcountries.R;
import com.mka.allcountries.model.CountryModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>{

    private List<CountryModel> countries;

    public CountryListAdapter(List<CountryModel> countries) {
        this.countries = countries;
    }

    public void updateCountries(List<CountryModel> newCountries) {
        countries.clear();
        countries.addAll(newCountries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.bind(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView countryImage;

        @BindView(R.id.country)
        TextView countryName;

        @BindView(R.id.capital)
        TextView capitalName;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind(CountryModel countryModel) {
            countryName.setText(countryModel.getCountryName());
            capitalName.setText(countryModel.getCapital());
            Util.loadImage(countryImage, countryModel.getFlag());
        }
    }
}
