package com.trunarrative.exercise.searchcompany;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class TestUtil {

    public static String loadJson(String jsonPath) throws RuntimeException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(new File(TestUtil.class.getResource(jsonPath).getPath())).toString();


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
