package io.github.andruid929.leutils.swing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DialogsTest {

    @Test
    @DisplayName("Set error dialog name")
    void setErrorDialogName() {
        Dialogs.setErrorDialogName("123");

        assertEquals("123", Dialogs.getErrorDialogName());
    }

    @Test
    @DisplayName("Get error dialog name with default fallback")
    void getErrorDialogName() {
        Dialogs.setErrorDialogName("123");

        assertEquals("123", Dialogs.getErrorDialogName());

        Dialogs.setErrorDialogName(null);

        assertEquals("Error", Dialogs.getErrorDialogName());
    }

    @Test
    @DisplayName("Set warning dialog name")
    void setWarningDialogName() {
        Dialogs.setWarningDialogName("123");

        assertEquals("123", Dialogs.getWarningDialogName());
    }

    @Test
    @DisplayName("Get warning dialog name with default fallback")
    void getWarningDialogName() {
        Dialogs.setWarningDialogName("123");

        assertEquals("123", Dialogs.getWarningDialogName());

        Dialogs.setWarningDialogName(null);

        assertEquals("Warning", Dialogs.getWarningDialogName());
    }

    @Test
    @DisplayName("Set info dialog name")
    void setInfoDialogName() {
        Dialogs.setInfoDialogName("123");

        assertEquals("123", Dialogs.getInfoDialogName());
    }

    @Test
    @DisplayName("Get confirmation dialog name with default fallback")
    void getConfirmationDialogName() {
        Dialogs.setConfirmationDialogName("123");

        assertEquals("123", Dialogs.getConfirmationDialogName());

        Dialogs.setConfirmationDialogName(null);

        assertEquals("Confirmation", Dialogs.getConfirmationDialogName());
    }

    @Test
    @DisplayName("Get info dialog name with default fallback")
    void getInfoDialogName() {
        Dialogs.setInfoDialogName("123");

        assertEquals("123", Dialogs.getInfoDialogName());

        Dialogs.setInfoDialogName(null);

        assertEquals("Info", Dialogs.getInfoDialogName());
    }

    @Test
    @DisplayName("Set confirmation dialog name")
    void setConfirmationDialogName() {
        Dialogs.setConfirmationDialogName("123");

        assertEquals("123", Dialogs.getConfirmationDialogName());
    }

}
