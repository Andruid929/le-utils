package io.github.andruid929.leutils.wora;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;

public class PathFinderTest {

	@Test	
	void testDocuments() {
		String documents = System.getenv("HOME") + File.separator + "Documents";

		assertEquals(documents, PathFinder.DOCUMENTS_FOLDER);
		assertEquals(Path.of(documents), PathFinder.getDocumentsFolder());
	}

}
