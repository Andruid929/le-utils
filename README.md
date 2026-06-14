# Le Utils v4.2.0

A lightweight Java utility library providing helpful functions for common repetitive tasks.

---

## What's new?

### Additions

- [Positioning](src/main/java/io/github/andruid929/leutils/swing/Positioning.java)

  - An interface that adds two methods to java swing components: `positionUnder()` and `positionNextTo()`,
    places the calling component underneath and next to the target element with the specified offsets respectively.

- [PathFinder](src/main/java/io/github/andruid929/leutils/wora/PathFinder.java)

  - Utility class for getting OS specific paths

---

## Requirements

- JDK 11 or newer
- Dependencies:
    - JetBrains annotations

---

## Installation

Add the dependency to your project:

### Maven

In `pom.xml`:

```xml

<dependency>
    <groupId>io.github.andruid929</groupId>
    <artifactId>le-utils</artifactId>
    <version>${version}</version>
</dependency>
```

### Gradle

In `build.gradle`:

```groovy
implementation 'io.github.andruid929:le-utils:${version}'
```

---

## Features

### Error handling

- **ErrorMessageHandler**: Simple utility for formatting and printing exception messages
    - Get clean, formatted error messages from exceptions
    - Print errors to custom output streams
    - Handles exceptions with or without messages
    - Get stack traces from exceptions as Strings
    -

### String tokenisation

- **Tokeniser**: Parse and tokenise strings with quote handling
    - Respects quoted strings (double quotes)
    - Respects escaped double quotes
    - Custom exception handling for unclosed quotes

### Date and time utilities

Epoll-based date and time utilities.

- **Time utilities**: Parse and format time strings
- **Date utilities**: Parse and format date strings

Time unit conversion.

- Convert between any two time units: milliseconds, seconds, minutes, hours, days

Task time calculation

- Time taken for tasks to complete down to the nanosecond

### Data unit conversion

- **DataUnit**: Enum representing units from Byte to Petabyte (binary prefix, 1024)
- **DataUnitConversion**:
    - Convert between any two data units: B, KB, MB, GB, TB, PB
    - Supports `long` and `double` precision
    - Formatted string output with unit suffixes (e.g. "1.5MB")
    - Intentional `long` overflow wrap-around for performance and resilience

### Formatting

- **NumberFormatting**:
    - Format numbers with custom or default (2) decimal places
    - Automatic inclusion of standard group separators (e.g. 1,234.56)

### Config

Create and read configs with a simple and readable key:value pair format.

- Collect configs in a global configuration, persist it to any file of your choosing,
  read the configs with an immutable object with getters.

### String utilities

- **Trim leading and/or trailing characters**
- **Normalise URL strings**

### Swing utilities

- **Add keybinds** – Customisable key input combinations to perform tasks
- **Dialogs** – Display simple warning, error or info dialogs

### Dialogs

- Choose to display a warning, error or information
- Set custom titles and messages

### Keybinds

- Add keybinds to swing frames
- Single key presses or multiple combinations with shift, alt etc

### Error root tracing

- Find the root cause of nested exceptions with their messages
