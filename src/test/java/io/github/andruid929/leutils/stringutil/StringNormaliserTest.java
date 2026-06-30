package io.github.andruid929.leutils.stringutil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringNormaliserTest {

    @Test
    void normaliseUrl() {
        String invalidUrl = " https:\\\\github.com\\Andruid929/le utils ";

        assertEquals("https://github.com/Andruid929/le-utils", StringNormaliser.normaliseUrl(invalidUrl, StringNormaliser.SpaceMode.HYPHEN));
        assertEquals("https://github.com/Andruid929/le%20utils", StringNormaliser.normaliseUrl(invalidUrl, StringNormaliser.SpaceMode.SPACE));
        assertEquals("https://github.com/Andruid929/le_utils", StringNormaliser.normaliseUrl(invalidUrl, StringNormaliser.SpaceMode.UNDERSCORE));
    }

}
