package com.mirea.kabanovasvetlana.mireaproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class NetworkFragment extends Fragment {
private TextView textViewMoonPhase;
private Retrofit retrofit;
private MoonPhaseApi moonPhaseApi;

@Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_network, container, false);
    textViewMoonPhase = view.findViewById(R.id.textViewMoonPhase);

    setupRetrofit();
    fetchMoonPhase();

    return view;
}

private void setupRetrofit() {
    retrofit = new Retrofit.Builder()
            .baseUrl("https://api.farmsense.net/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    moonPhaseApi = retrofit.create(MoonPhaseApi.class);
}

private void fetchMoonPhase() {
    // Параметры для API: date=сегодня в формате yyyy-MM-dd (API требует timestamp, будет пример ниже)
    long currentTimestamp = System.currentTimeMillis() / 1000L;

    Call<List<MoonPhaseResponse>> call = moonPhaseApi.getMoonPhase(currentTimestamp);
    call.enqueue(new Callback<List<MoonPhaseResponse>>() {
        @Override
        public void onResponse(Call<List<MoonPhaseResponse>> call, Response<List<MoonPhaseResponse>> response) {
            if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                MoonPhaseResponse moonPhase = response.body().get(0);
                textViewMoonPhase.setText("Фаза Луны: " + moonPhase.getPhase());
            } else {
                textViewMoonPhase.setText("Не удалось получить данные.");
            }
        }

        @Override
        public void onFailure(Call<List<MoonPhaseResponse>> call, Throwable t) {
            textViewMoonPhase.setText("Ошибка: " + t.getMessage());
        }
    });
}

interface MoonPhaseApi {
    @GET("moonphases/")
    Call<List<MoonPhaseResponse>> getMoonPhase(@Query("d") long dateTimestamp);
}

// Класс для парсинга JSON
public static class MoonPhaseResponse {
    @SerializedName("Phase")
    private String phase;

    public String getPhase() {
        return phase;
    }
}
}