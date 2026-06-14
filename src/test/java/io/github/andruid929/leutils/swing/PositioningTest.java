package io.github.andruid929.leutils.swing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.swing.*;

class PositioningTest implements Positioning {

	private JPanel targetPanel;

	@BeforeEach
	void initPanel() {
		System.setProperty("java.awt.headless", "true");

		targetPanel = new JPanel();
		targetPanel.setBounds(30, 30, 50, 50);
	}

    @Test
    void testPositionUnder() {
        assertThrows(IllegalStateException.class, () -> this.positionUnder(new JPanel(), 0));

		var panel = new CustomPanel();
		
		panel.positionUnder(targetPanel, 20);

		assertEquals(100, panel.getY());

		panel.setLocation(0, 0);
		panel.positionUnder(targetPanel, 20, 10);

		assertEquals(40, panel.getX());
    }

    @Test
    void testPositionNextTo() {
        assertThrows(IllegalStateException.class, () -> this.positionNextTo(new JPanel(), 0));

		var panel = new CustomPanel();

		panel.positionNextTo(targetPanel, 20);

		assertEquals(100, panel.getX());

		panel.setLocation(0, 0);
		panel.positionNextTo(targetPanel, 20, 10);

		assertEquals(40, panel.getY());
    }

	private class CustomPanel extends JPanel implements Positioning {

		public CustomPanel() {
		}

	}
}
