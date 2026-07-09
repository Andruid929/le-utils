# Le Utils v4.4.0

A lightweight Java utility library providing helpful functions for common repetitive tasks.

---
## Table of contents

- [Notice board](#notice-board)
  - [Deprecation notice](#deprecation-notice)
- [What's new](#whats-new)
- [Requirements](#requirements)
- [Adding the library](#using-the-library)
  - [Maven](#maven)
  - [Gradle](#gradle)
- [Features](#features)
---

## Notice board

### Deprecation notice

**_[Strings package](src/main/java/io/github/andruid929/leutils/strings)_**
- [StringUtil](src/main/java/io/github/andruid929/leutils/strings/StringUtil.java) class is deprecated and will be removed in v5.0.0
- Class functions have been moved to the [stringutil](src/main/java/io/github/andruid929/leutils/stringutil) package
- New usage:
  ```java
  //Instead of StringUtil.trimCharacters("(name=Andrew), 1";
  StringFormatter.trimCharacters("(name=Andrew)", 1); //Different class, same function
  ```

## What's new? 

### Additions

- `StringFormatter.interpolate()`

- **Config helpers**
  - Added bulk import support with `Config.addFromMap(...)`.
  - Added helpers to load persisted config files and inspect the currently loaded global config state.

### Changes

- **Config**
    - The configuration API now supports importing a full map at once, loading persisted config files, and reading the
      global state that has been loaded into memory.

---

## Requirements

- JDK 11 or newer
- Dependencies:
    - JetBrains annotations

---

## Using the library

Add the dependency to your project:

### Maven

In `pom.xml`:

```xml

<dependency>
    <groupId>io.github.andruid929</groupId>
    <artifactId>le-utils</artifactId>
    <version>4.4.0</version>
</dependency>
```

### Gradle

In `build.gradle`:

```groovy
implementation 'io.github.andruid929:le-utils:4.4.0'
```

---

## Features

### Error handling

- **ErrorMessageHandler**: Simple utility for formatting and printing exception messages
    - Get clean, formatted error messages from exceptions
    - Print errors to custom output streams
    - Handles exceptions with or without messages
    - Get stack traces from exceptions as Strings

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

### NumberFormatting

- **NumberFormatting**:
    - Format numbers with custom or default (2) decimal places
    - Automatic inclusion of standard group separators (e.g. 1,234.56)

### Config

Create and read configs with a simple and readable key:value pair format.

- Collect configs in a global configuration, persist it to any file of your choosing,
  read the configs with an immutable object with getters.
- Import entire maps of key/value pairs, load persisted config files, and inspect the current global state.

### String utilities

- **`StringFormatter`**: Character-level manipulations (e.g., trimming, formatting) and template interpolation with `{}` placeholders.
- **`Separators`**: Advanced string splitting into `List` or `Set` (regex or literal).
- **`StringNormaliser`**: Normalisation of URLs.

### Swing utilities

- **Keybinds** – Customisable key input combinations to perform tasks
- **Dialogs** – Display simple warning, error, info or confirmation dialogs

### Dialogs

- Choose to display a warning, error or information
- Set custom titles and messages

### Keybinds

- Add keybinds to swing frames
- Single key presses or multiple combinations with shift, alt etc

### Error root tracing

- Find the root cause of nested exceptions with their messages

### Unix/Windows path handler

- Get OS specific paths
