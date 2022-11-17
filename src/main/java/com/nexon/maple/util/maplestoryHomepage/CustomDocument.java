package com.nexon.maple.util.maplestoryHomepage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public abstract class CustomDocument {

    public Document connect(String URL) throws IOException {
        return Jsoup.connect(URL).get();
    }

    public Document connect(String URL, Map<String, String> headers) throws IOException {
        return Jsoup.connect(URL).headers(headers).get();
    }

    public abstract Object build() throws IOException;
}
