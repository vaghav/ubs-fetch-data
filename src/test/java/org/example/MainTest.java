package org.example;

import org.junit.jupiter.api.Test;



class MainTest {

    @Test
    void testMainMethod() {
        Main.main(new String[]{"-id", "2", "-o", "JSON"});
    }

}