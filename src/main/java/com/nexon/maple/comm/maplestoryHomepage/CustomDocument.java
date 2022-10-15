package com.nexon.maple.comm.maplestoryHomepage;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class CustomDocument {
    private Document document;

    //header없이 호출한 URL에서 마지막으로 반환된 document 가져오기
    public CustomDocument(String URL) {
        try {
            Connection conn = Jsoup.connect(URL);
            this.document = conn.get();
        } catch (IOException e) {
            this.document = null;
        }
    }

    //header를 추가한 URL에서 마지막으로 반환된 document 가져오기
    //javascript의 ajax기능
    public CustomDocument(String URL, Map<String, String> headers) {
        try {
            Connection conn = Jsoup.connect(URL).headers(headers);
            this.document = conn.get();
        } catch (IOException e) {
            this.document = null;
        }
    }

    public boolean isDocument() {
        if(this.document == null) return true;
        return false;
    }

    public Document getDocument() {
        return this.document;
    }

    public Elements getElements(String selector) {
        if(this.document == null) {
            return null;
        }

        return this.document.select(selector);
    }

}
