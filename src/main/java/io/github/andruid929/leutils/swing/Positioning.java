package io.github.andruid929.leutils.swing;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Interface to ease positioning window elements next to or below other elements.
 * <p>
 * <b>Note:</b> This interface is meant to be implemeted only by subclasses of {@link Component}
 * and will throw an {@link IllegalStateException} if either method is called from the illegal class object.
 *
 * @author Andrew Jones
 * @since 4.2.0
 */

public interface Positioning {

    /**
     * Position an element next to this element (x-axis) with the specified offset.
     *
     * @param component the component to place this component next to
     * @param offset    the offset on the x-axis
     * @param yOffset   the optional offset for the y-axis
     * @throws IllegalStateException if this method is called from class that does not extend {@link Component}.
     */
    default void positionNextTo(@NotNull Component component, int offset, int @NotNull ... yOffset) throws IllegalStateException {
        Component origin = requireComponent();

        int yAxisOffset = 0;

        if (yOffset.length != 0) {
            yAxisOffset = yOffset[0];
        }

        int elementRightEdge = component.getX() + component.getWidth();

        origin.setLocation(elementRightEdge + offset, component.getY() + yAxisOffset);
    }

    /**
     * Position an element above or under this element (y-axis) with the specified offset.
     *
     * @param component the component to place this component under
     * @param offset    the offset on the y-axis
     * @param xOffset   the optional offset for the x-axis
     * @throws IllegalStateException if this method is called from class that does not extend {@link Component}.
     */

    default void positionUnder(@NotNull Component component, int offset, int @NotNull ... xOffset) throws IllegalStateException {
        Component origin = requireComponent();

        int xAxisOffset = 0;

        if (xOffset.length != 0) {
            xAxisOffset = xOffset[0];
        }

        int elementBottomEdge = component.getY() + component.getHeight();

        origin.setLocation(component.getX() + xAxisOffset, elementBottomEdge + offset);
    }

    /**
     * Checks if this object is a child of {@link Component}
     */

    private Component requireComponent() {
        if (this instanceof Component) {
            return (Component) this;
        }

        String illegalClass = this.getClass().getName();

        throw new IllegalStateException(illegalClass + " is not a child of " + Component.class.getName());
    }
}

