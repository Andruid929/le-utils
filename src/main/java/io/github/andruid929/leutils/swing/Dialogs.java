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
 * @since 4.0.0
 */
public final class Dialogs {

    /**
     * Utility classes cannot be instantiated
     * */

    private Dialogs() {
    }

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
    private static final  String WARNING_DIALOG_NAME = "Warning";

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
     * Sets a custom title for error dialogs.
     *
     * @param name the custom title to use for error dialogs
     */
    public static void setErrorDialogName(String name) {
        errorDialogName = name;
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
     * Sets a custom title for warning dialogs.
     *
     * @param name the custom title to use for warning dialogs
     */
    public static void setWarningDialogName(String name) {
        warningDialogName = name;
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
     * Sets a custom title for information dialogs.
     *
     * @param name the custom title to use for information dialogs
     */
    public static void setInfoDialogName(String name) {
        infoDialogName = name;
    }

    /**
     * Gets the current title for information dialogs.
     *
     * @return the custom information dialog title, or the default "Info" if not set
     */
    public static String getInfoDialogName() {
        return Objects.requireNonNullElse(infoDialogName, INFO_DIALOG_NAME);

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

}
