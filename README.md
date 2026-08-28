# Le Utils v5.0

A lightweight Java utility library providing helpful functions for common repetitive tasks.

---
## Table of contents

- [Notice board](#notice-board)
- [What's new](#whats-new-)
- [Requirements](#requirements)
- [Adding the library](#using-the-library)
  - [Maven](#maven)
  - [Gradle](#gradle)
- [Features](#features)
---

## Notice board

### Breaking change
- `Config.readConfig(Path)` will now throw an `IOException` if the specified path is a folder or
  the file does not exist. See [additions](#additions) for more info on the flag.

## What's new?

### Additions

- **[StringFormatter](src/main/java/io/github/andruid929/leutils/stringutil/StringFormatter.java)**
  - `interpolateAll()` to replace all placeholders with a single value.

- **[Config](src/main/java/io/github/andruid929/leutils/config/Config.java)**
  - `failOnMissingFile` flag that decides whether an exception is thrown when `readConfig()`
    cannot find the path specified (or if the path points to a folder). This flag will stop the Config
    from returning empty values which would then overwrite the persisted values on the next write. On by default,
    can be enabled/disabled by calling the new static `setFailOnMissingFile(boolean)`... disable it at your own risk.
  - Lines that start with `#` will be treated as comments and won't be picked up as invalid lines

### Changes

- `StringFormatter.interpolate()` now uses a `StringBuilder` instead of multiple regex calls better
  performance

### Removals

- As promised in past deprecation notices, the `strings` package (io.github.andruid929.leutils.strings) has been removed
  because everything, within the one class that was there, was moved to the
  [stringutil](src/main/java/io/github/andruid929/leutils/stringutil) package.

### Bug fixes
- Empty lines are no longer collected by [Config](src/main/java/io/github/andruid929/leutils/config/Config.java) as
  invalid lines
- `StringFormatter.interpolate()` now operates on literals instead of the mixed regex and literal logic before

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
    <version>5.0.0</version>
</dependency>
```

### Gradle

In `build.gradle`:

```groovy
implementation 'io.github.andruid929:le-utils:5.5.0'
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

- **Global configuration management**:
  - Add/remove/clear configuration entries with support for various data types (String, Number, boolean, arrays, char)
  - Inspect current global state with `getLoadedConfigs()` and `numberOfConfigs()`
  - Import entire maps of key/value pairs with `addFromMap()`

- **Persistence**:
  - Persist global configuration to file with `persistConfig()` (sorted keys for stable diffs)
  - Load persisted configs with `readConfig()` or `loadSavedChanges()`
  - Control behavior on missing files with `setFailOnMissingFile()` flag (throws exception by default)

- **Immutable instance-level views**:
  - Create immutable `Config` objects from configuration file lines
  - Parse values as various types: String, int, long, float, double, boolean, char, arrays
  - Auto-skip comment lines (starting with `#`) and empty lines
  - Inspect invalid configuration lines with `getInvalidConfigs()`

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

### PathFinder - Unix/Windows/Mac path handler

Get OS-specific paths for common directories (Windows, Mac, Linux):

- **Quick access paths**:
  - User home directory via `USER_HOME` constant
  - Documents folder via `DOCUMENTS_FOLDER` constant or `getDocumentsFolder()`
  - App data folder via `APPDATA_FOLDER` constant or `getAppDataFolder()`
    - Windows: `%APPDATA%`
    - Mac: `~/Library/Application Support`
    - Linux: `~/.config`

- **Path construction**:
  - `getUserFolder(String)` - Get a single folder from user home
  - `createPathFromHomeRoot(String...)` - Build paths extending from user home (e.g., `.m2/repository`)
