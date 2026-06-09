package io.github.andruid929.leutils.swing;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Interface to ease positioning window elements next to or below other elements.
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
     */
    default void positionNextTo(@NotNull Component component, int offset, int @NotNull ... yOffset) {
        if (this instanceof Component) {

			Component origin = (Component) this;

            int yAxisOffset = 0;

            if (yOffset.length != 0) {
                yAxisOffset = yOffset[0];
            }

            int elementRightEdge = component.getX() + component.getWidth();

            origin.setLocation(elementRightEdge + offset, component.getY() + yAxisOffset);

            return;
        }

        String illegalClass = this.getClass().getName();

        throw new IllegalStateException(illegalClass + " is not a child of " + Component.class.getName());
    }

    /**
     * Position an element above or under this element (y-axis) with the specified offset.
     *
     * @param component the component to place this component under
     * @param offset    the offset on the y-axis
     * @param xOffset   the optional offset for the x-axis
     */

    default void positionUnder(@NotNull Component component, int offset, int @NotNull ... xOffset) {
        if (this instanceof Component) {

			Component origin = (Component) this;

            int xAxisOffset = 0;

            if (xOffset.length != 0) {
                xAxisOffset = xOffset[0];
            }

            int elementBottomEdge = component.getY() + component.getHeight();

            origin.setLocation(component.getX() + xAxisOffset, elementBottomEdge + offset);

            return;
        }

        String illegalClass = this.getClass().getName();

        throw new IllegalStateException(illegalClass + " is not a child of " + Component.class.getName());
    }

}

