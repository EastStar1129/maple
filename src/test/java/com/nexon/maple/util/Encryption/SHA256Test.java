package com.nexon.maple.util.Encryption;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA256Test {

    @Test
    public void 단방향_sha256_테스트() {
        //given
        String data = "data";

        //when
        String encData = new SHA256().encrypt(data);

        //then
        assertNotEquals(data, encData);
    }
}