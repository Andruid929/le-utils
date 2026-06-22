package io.github.andruid929.leutils.swing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DialogsTest {

    @Test
    void setErrorDialogName() {
        Dialogs.setErrorDialogName("123");

        assertEquals("123", Dialogs.getErrorDialogName());
    }

    @Test
    void getErrorDialogName() {
        Dialogs.setErrorDialogName("123");

        assertEquals("123", Dialogs.getErrorDialogName());

        Dialogs.setErrorDialogName(null);

        assertEquals("Error", Dialogs.getErrorDialogName());
    }

    @Test
    void setWarningDialogName() {
        Dialogs.setWarningDialogName("123");

        assertEquals("123", Dialogs.getWarningDialogName());
    }

    @Test
    void getWarningDialogName() {
        Dialogs.setWarningDialogName("123");

        assertEquals("123", Dialogs.getWarningDialogName());

        Dialogs.setWarningDialogName(null);

        assertEquals("Warning", Dialogs.getWarningDialogName());
    }

    @Test
    void setInfoDialogName() {
        Dialogs.setInfoDialogName("123");

        assertEquals("123", Dialogs.getInfoDialogName());
    }

    @Test
    void getConfirmationDialogName() {
        Dialogs.setConfirmationDialogName("123");

        assertEquals("123", Dialogs.getConfirmationDialogName());

        Dialogs.setConfirmationDialogName(null);

        assertEquals("Confirmation", Dialogs.getConfirmationDialogName());
    }

    @Test
    void getInfoDialogName() {
        Dialogs.setInfoDialogName("123");

        assertEquals("123", Dialogs.getInfoDialogName());

        Dialogs.setInfoDialogName(null);

        assertEquals("Info", Dialogs.getInfoDialogName());
    }

    @Test
    void setConfirmationDialogName() {
        Dialogs.setConfirmationDialogName("123");

        assertEquals("123", Dialogs.getConfirmationDialogName());
    }

}
