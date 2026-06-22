package io.github.andruid929.leutils.swing;

import org.intellij.lang.annotations.MagicConstant;

import java.awt.*;
import java.util.Objects;

import javax.swing.*;

/**
 * Utility class for displaying standard Swing dialog boxes.
 * <p>
 * This class provides convenient methods for showing error, information, and warning dialogs
 * with customisable titles and optional icons. Dialog titles can be configured globally
 * or default values will be used.
 *
 * @author Andrew Jones
 * @apiNote These dialogs are blocking calls and are best used at the end of the sequence
 * or used with {@link SwingUtilities#invokeLater(Runnable)}.
 * @since 4.0.0
 */
public final class Dialogs {

    /**
     * The default title for error dialogs.
     */
    private static final String ERROR_DIALOG_NAME = "Error";
    /**
     * The default title for information dialogs.
     */
    private static final String INFO_DIALOG_NAME = "Info";
    /**
     * The default title for warning dialogs.
     */
    private static final String WARNING_DIALOG_NAME = "Warning";
    /**
     * The default title for the confirmation dialogs.
     */

    private static final String CONFIRM_DIALOG_NAME = "Confirmation";
    /**
     * Custom title for error dialogs. If null, the default ERROR_DIALOG_NAME is used.
     */
    private static String errorDialogName;
    /**
     * Custom title for information dialogs. If null, the default INFO_DIALOG_NAME is used.
     */
    private static String infoDialogName;
    /**
     * Custom title for warning dialogs. If null, the default WARNING_DIALOG_NAME is used.
     */
    private static String warningDialogName;
    /**
     * Custom title for confirmation dialogs. If null, the default CONFIRM_DIALOG_NAME is used.
     */

    private static String confirmationDialogName;

    /**
     * Utility classes cannot be instantiated
     *
     */

    private Dialogs() {
    }

    /**
     * Internal method to display a dialog with a custom icon.
     *
     * @param parentComponent the parent component for the dialog, or null for default
     * @param message         the message to display in the dialog
     * @param title           the title of the dialog
     * @param messageType     the type of message (INFORMATION_MESSAGE, WARNING_MESSAGE, or ERROR_MESSAGE)
     * @param icon            the custom icon to display in the dialog
     */
    private static void showDialog(Component parentComponent, Object message, String title,
                                   @MagicConstant(intValues = {JOptionPane.INFORMATION_MESSAGE,
                                           JOptionPane.WARNING_MESSAGE, JOptionPane.ERROR_MESSAGE}) int messageType,
                                   Icon icon) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType, icon);
    }

    /**
     * Displays a confirmation dialog with a custom icon and parent component.
     *
     * @param parentComponent the parent component for the dialog, or null for the default
     * @param message         the message to display in the dialog
     * @param icon            the custom icon to display in the dialog
     * @return an int showing which option the user picked, 0 for yes and 1 for no.
     * @since 4.3.0
     */
    private static int showConfirmDialog(Component parentComponent, Object message, Icon icon) {
        return JOptionPane.showConfirmDialog(parentComponent, message, getConfirmationDialogName(),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
    }

    /**
     * Displays a confirmation dialog with the specified parent component and message.
     *
     * @param parentComponent the parent component for the dialog; can be {@code null}.
     * @param message         the message to be displayed in the confirmation dialog.
     * @return an int showing which option the user picked, 0 for yes and 1 for no.
     * @since 4.3.0
     */
    private static int showConfirmDialog(Component parentComponent, Object message) {
        return JOptionPane.showConfirmDialog(parentComponent, message, getConfirmationDialogName(),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Internal method to display a dialog with the default icon for the message type.
     *
     * @param parentComponent the parent component for the dialog, or null for default
     * @param message         the message to display in the dialog
     * @param title           the title of the dialog
     * @param messageType     the type of message (INFORMATION_MESSAGE, WARNING_MESSAGE, or ERROR_MESSAGE)
     */
    private static void showDialog(Component parentComponent, Object message, String title,
                                   @MagicConstant(intValues = {JOptionPane.INFORMATION_MESSAGE,
                                           JOptionPane.WARNING_MESSAGE, JOptionPane.ERROR_MESSAGE}) int messageType) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }

    /**
     * Gets the current title for error dialogs.
     *
     * @return the custom error dialog title, or the default "Error" if not set
     */
    public static String getErrorDialogName() {
        return Objects.requireNonNullElse(errorDialogName, ERROR_DIALOG_NAME);
    }

    /**
     * Sets a custom title for error dialogs.
     *
     * @param name the custom title to use for error dialogs
     */
    public static void setErrorDialogName(String name) {
        errorDialogName = name;
    }

    /**
     * Gets the current title for warning dialogs.
     *
     * @return the custom warning dialog title, or the default "Warning" if not set
     */
    public static String getWarningDialogName() {
        return Objects.requireNonNullElse(warningDialogName, WARNING_DIALOG_NAME);
    }

    /**
     * Sets a custom title for warning dialogs.
     *
     * @param name the custom title to use for warning dialogs
     */
    public static void setWarningDialogName(String name) {
        warningDialogName = name;
    }

    /**
     * Gets the current title for the confirmation dialogs.
     *
     * @return the custom confirmation dialog title, or the default "Confirmation" if not set
     * @since 4.3.0
     */

    public static String getConfirmationDialogName() {
        return Objects.requireNonNullElse(confirmationDialogName, CONFIRM_DIALOG_NAME);
    }

    /**
     * Sets a custom title for confirmation dialogs.
     *
     * @param name the custom title to use for confirmation dialogs
     * @since 4.3.0
     */

    public static void setConfirmationDialogName(String name) {
        confirmationDialogName = name;
    }

    /**
     * Gets the current title for information dialogs.
     *
     * @return the custom information dialog title, or the default "Info" if not set
     */
    public static String getInfoDialogName() {
        return Objects.requireNonNullElse(infoDialogName, INFO_DIALOG_NAME);

    }

    /**
     * Sets a custom title for information dialogs.
     *
     * @param name the custom title to use for information dialogs
     */
    public static void setInfoDialogName(String name) {
        infoDialogName = name;
    }

    //Error dialogs

    /**
     * Displays an error dialog with a custom icon and parent component.
     *
     * @param parentComponent the parent component for the dialog, or null for default
     * @param message         the message to display in the dialog
     * @param icon            the custom icon to display in the dialog
     */
    public static void showErrorDialog(Component parentComponent, Object message, Icon icon) {
        showDialog(parentComponent, message, getErrorDialogName(), JOptionPane.ERROR_MESSAGE, icon);
    }

    /**
     * Displays an error dialog with a custom icon.
     *
     * @param message the message to display in the dialog
     * @param icon    the custom icon to display in the dialog
     */
    public static void showErrorDialog(Object message, Icon icon) {
        showDialog(null, message, getErrorDialogName(), JOptionPane.ERROR_MESSAGE, icon);
    }

    /**
     * Displays an error dialog with a parent component.
     *
     * @param parentComponent the parent component for the dialog, or null for default
     * @param message         the message to display in the dialog
     */
    public static void showErrorDialog(Component parentComponent, Object message) {
        showDialog(parentComponent, message, getErrorDialogName(), JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an error dialog.
     *
     * @param message the message to display in the dialog
     */
    public static void showErrorDialog(Object message) {
        showDialog(null, message, getErrorDialogName(), JOptionPane.ERROR_MESSAGE);
    }

    //Info dialogs

    /**
     * Displays an information dialog with a custom icon and parent component.
     *
     * @param parentComponent the parent component for the dialog, or null for default
     * @param message         the message to display in the dialog
     * @param icon            the custom icon to display in the dialog
     */
    public static void showInfoDialog(Component parentComponent, Object message, Icon icon) {
        showDialog(parentComponent, message, getInfoDialogName(), JOptionPane.INFORMATION_MESSAGE, icon);
    }

    /**
     * Displays an information dialog with a custom icon.
     *
     * @param message the message to display in the dialog
     * @param icon    the custom icon to display in the dialog
     */
    public static void showInfoDialog(Object message, Icon icon) {
        showDialog(null, message, getInfoDialogName(), JOptionPane.INFORMATION_MESSAGE, icon);
    }

    /**
     * Displays an information dialog with a parent component.
     *
     * @param parentComponent the parent component for the dialog, or null for default
     * @param message         the message to display in the dialog
     */
    public static void showInfoDialog(Component parentComponent, Object message) {
        showDialog(parentComponent, message, getInfoDialogName(), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays an information dialog.
     *
     * @param message the message to display in the dialog
     */
    public static void showInfoDialog(Object message) {
        showDialog(null, message, getInfoDialogName(), JOptionPane.INFORMATION_MESSAGE);
    }

    //Warning dialogs

    /**
     * Displays a warning dialog with a custom icon and parent component.
     *
     * @param parentComponent the parent component for the dialog, or null for default
     * @param message         the message to display in the dialog
     * @param icon            the custom icon to display in the dialog
     */
    public static void showWarningDialog(Component parentComponent, Object message, Icon icon) {
        showDialog(parentComponent, message, getWarningDialogName(), JOptionPane.WARNING_MESSAGE, icon);
    }

    /**
     * Displays a warning dialog with a custom icon.
     *
     * @param message the message to display in the dialog
     * @param icon    the custom icon to display in the dialog
     */
    public static void showWarningDialog(Object message, Icon icon) {
        showDialog(null, message, getWarningDialogName(), JOptionPane.WARNING_MESSAGE, icon);
    }

    /**
     * Displays a warning dialog with a parent component.
     *
     * @param parentComponent the parent component for the dialog, or null for default
     * @param message         the message to display in the dialog
     */
    public static void showWarningDialog(Component parentComponent, Object message) {
        showDialog(parentComponent, message, getWarningDialogName(), JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays a warning dialog.
     *
     * @param message the message to display in the dialog
     */
    public static void showWarningDialog(Object message) {
        showDialog(null, message, getWarningDialogName(), JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays a confirmation dialog with a custom icon and parent component.
     *
     * @param parentComponent the parent component for the dialog, or null for the default
     * @param message         the message to display in the dialog
     * @param icon            the custom icon to display in the dialog
     * @return true if the user selects the "Yes" option, false otherwise
     * @since 4.3.0
     */
    public static boolean showConfirmationDialog(Component parentComponent, Object message, Icon icon) {
        int option = showConfirmDialog(parentComponent, message, icon);

        return option == 0;
    }

    /**
     * Displays a confirmation dialog with the specified message and icon.
     *
     * @param message the message to be displayed in the dialog; should not be null
     * @param icon    the icon to be displayed alongside the message
     * @return {@code true} if the user confirms the dialog, {@code false} otherwise
     * @since 4.3.0
     */
    public static boolean showConfirmationDialog(Object message, Icon icon) {
        return showConfirmationDialog(null, message, icon);
    }

    /**
     * Displays a confirmation dialog with the specified message and returns the user's choice.
     * The dialog presents the user with Yes and No options.
     *
     * @param message the message or object to display in the confirmation dialog
     * @return true if the user selects Yes, false if the user selects No
     * @since 4.3.0
     */
    public static boolean showConfirmationDialog(Object message) {
        int option = showConfirmDialog(null, message);

        return option == 0;
    }

}
