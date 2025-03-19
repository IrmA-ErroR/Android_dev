package com.mirea.kabanovasvetlana.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



public class nav_data extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nav_data, container, false);

        TextView textView = rootView.findViewById(R.id.textViewInfo);
        String dataEngineeringText = "\n" +
                "Data Engineering - ключевая область в сфере обработки данных. " +
                "Она занимается проектированием, разработкой и оптимизацией " +
                "инфраструктуры для сбора, хранения и обработки больших объемов данных. " +
                "Специалисты в этой области работают с ETL-процессами, хранилищами данных " +
                "и различными инструментами для обработки потоков данных, такими как Apache Spark, Kafka и Airflow.\n\n" +
                "\nСлучай из жизни:\n" +
                "Нужно попасть на двенадцатый этаж. Захожу в лифт, нажимаю кнопку «1», затем «2» и безуспешно ищу глазами клавишу Enter… ";
        textView.setText(dataEngineeringText);

        return rootView;
    }
}
