package com.example.fifafixture;

import com.example.fifafixture.model.FixtureResponse;

public interface ResponseListener {
    void didFetch(FixtureResponse response,String massage);
    void didError(String massage);
}
