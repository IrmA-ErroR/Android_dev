package com.mirea.kabanovasvetlana.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class nav_browser extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nav_browser, container, false);
        webView = rootView.findViewById(R.id.webView);
        webView.loadUrl("https://www.google.com");
        return rootView;
    }
}