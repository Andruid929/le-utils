# Le Utils

[![Maven Central](https://img.shields.io/maven-central/v/io.github.andruid929/le-utils)](https://central.sonatype.com/artifact/io.github.andruid929/le-utils)
[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

A lightweight Java utility library providing helpful functions for common repetitive tasks.

---
## Table of contents

- [Notice board](#notice-board)
- [What's new](#whats-new)
- [Changelog](CHANGELOG.md)
- [Requirements](#requirements)
- [Adding the library](#using-the-library)
  - [Maven](#maven)
  - [Gradle](#gradle)
- [Features](#features)
  - [Config files](#config-files)
  - [Data units](#data-units)
  - [Error handling](#error-handling)
  - [Number formatting](#number-formatting)
  - [Strings](#strings)
  - [Swing](#swing)
  - [Time](#time)
  - [Tokeniser](#tokeniser)
  - [Operating system and paths](#operating-system-and-paths)
  - [Library version](#library-version)
- [Building](#building)
- [License](#license)
---

## Notice board

### No breaking changes
- 5.1 only adds to the 5.0 API, so code built against 5.0 keeps working without recompiling. The one difference a
  caller could notice is that `Config.add(String, String)` and `Config.addFromMap(Map)` now throw
  `IllegalArgumentException` instead of `NullPointerException` for a `null` value.
- Coming from 4.x? 5.0 had breaking changes to `Config.readConfig()` and removed the `strings` package. See the
  [changelog](CHANGELOG.md#500---2026-08-28).

## What's new?

Highlights of 5.1. The [changelog](CHANGELOG.md) has every change in this and earlier versions.

- **[OperatingSystem](src/main/java/io/github/andruid929/leutils/wora/OperatingSystem.java)** (new): find out which
  OS the program is running on, or identify any `os.name` value, with `null` for systems it doesn't know.
- **[PathFinder](src/main/java/io/github/andruid929/leutils/wora/PathFinder.java)**: `getLocalAppDataFolder()` for
  files that shouldn't roam with a Windows profile.
- **[Config](src/main/java/io/github/andruid929/leutils/config/Config.java)**: every getter takes an optional
  default for missing keys, e.g. `config.getInteger("port", 8080)`.
- **[LeUtils](src/main/java/io/github/andruid929/leutils/LeUtils.java)** (new): `LeUtils.getVersion()`.
- Fixes for `getAppDataFolder()` when `%APPDATA%` is unset, and for "Darwin" being mistaken for Windows.

---

## Requirements

- JDK 11 or newer
- [JetBrains annotations](https://github.com/JetBrains/java-annotations), which Maven and Gradle add for you along
  with the library
- The [Swing](#swing) utilities need a desktop environment. Everything else also works on headless servers.

---

## Using the library

Add the dependency to your project:

### Maven

In `pom.xml`:

```xml
<dependency>
    <groupId>io.github.andruid929</groupId>
    <artifactId>le-utils</artifactId>
    <version>5.1.0</version>
</dependency>
```

### Gradle

In `build.gradle`:

```groovy
implementation 'io.github.andruid929:le-utils:5.1.0'
```

In `build.gradle.kts`:

```kotlin
implementation("io.github.andruid929:le-utils:5.1.0")
```

---

## Features

Each section names its package under `io.github.andruid929.leutils`. The
[Javadoc](https://javadoc.io/doc/io.github.andruid929/le-utils) covers every method.

### Config files

Package `config`: `Config`

Read and write config files in a simple `key:value` format:

```
# Server settings
port:8080
name:demo
debug:true
tags:red,green,blue
```

- **Reading**: `Config.readConfig(Path)` returns an immutable `Config`
  - Getters for String, int, long, float, double, boolean, char, String arrays and int arrays
  - Pass a default to any getter to use when the key is missing (a malformed value still throws)
  - Comment lines (starting with `#`) and empty lines are skipped, and lines that aren't `key:value` are kept in
    `getInvalidConfigs()`
  - A missing file or a folder throws an `IOException` by default, so empty values can't overwrite saved ones on the
    next write. `setFailOnMissingFile(false)` turns that off, at your own risk
- **Writing**: a global store, separate from the `Config` that `readConfig()` returns, which `persistConfig()` saves
  to a file
  - `persistConfig()` replaces the whole file with what's in the store, so to change one value in an existing file,
    load it into the store with `loadSavedChanges()` first. Comment lines aren't kept
  - `add()` for String, Number, boolean, arrays and char values, `addFromMap()`, `remove()` and `clear()`
  - Inspect it with `get()`, `getLoadedConfigs()` and `numberOfConfigs()`
  - Keys are saved sorted, for stable diffs

```java
Config config = Config.readConfig(Path.of("app.config"));

int port = config.getInteger("port");              // 8080
int timeout = config.getInteger("timeout", 30);    // 30, since the file has no timeout
boolean debug = config.getBoolean("debug");        // true
String[] tags = config.getArray("tags");           // [red, green, blue]

Config.loadSavedChanges(Path.of("app.config"));    // load the file into the store, so its other keys are kept
Config.add("port", 9090);
Config.persistConfig(Path.of("app.config"), true); // true creates missing folders
```

### Data units

Package `data`: `DataUnit`, `DataUnitConversion`

- `DataUnit` covers Byte to Petabyte in binary steps of 1024
- Convert between any two units, as `long` or `double`, or format the result with its unit suffix
- `long` conversions round down and deliberately wrap around on overflow, for speed; use the `double` versions when
  you need fractions

```java
DataUnitConversion.calculate(5, DataUnit.GIGABYTE, DataUnit.MEGABYTE);             // 5120
DataUnitConversion.formatWithUnit(1536.0, DataUnit.KILOBYTE, DataUnit.MEGABYTE);   // "1.5MB"
```

### Error handling

Package `errorhandling`: `ErrorMessageHandler`

- Short, readable messages from exceptions, with or without an exception message
- Print errors to `System.err` or any `PrintStream`, or get the stack trace as a String
- Find the root cause of nested exceptions, or its message

```java
Exception e = new RuntimeException("Save failed", new IOException("Disk full"));

ErrorMessageHandler.simpleErrorMessage(e);           // "java.lang.RuntimeException: Save failed"
ErrorMessageHandler.throwableRootMessageTracer(e);   // "Disk full"
ErrorMessageHandler.printSimpleErrorMessage(e);      // prints the simple message to System.err
```

### Number formatting

Package `formatting`: `NumberFormatting`

- Format numbers with group separators and 2 or a chosen number of decimal places
- Format a value as a percentage

```java
NumberFormatting.formatNumber(1234.5678);        // "1,234.57"
NumberFormatting.formatter(1234.5678, 3);        // "1,234.568"
NumberFormatting.formatWholeNumber(1234567);     // "1,234,567"
NumberFormatting.percentageDecimal("0.25");      // "25.00%"
```

### Strings

Package `stringutil`: `StringFormatter`, `Separators`, `StringNormaliser`

- **`StringFormatter`**: fill `{}` placeholders, and trim characters from the ends of a string
- **`Separators`**: split a string into an unmodifiable `List` or `Set`, by regex or by a literal delimiter
- **`StringNormaliser`**: tidy a URL by trimming it, replacing its spaces with `%20`, `_` or `-`, and turning `\`
  into `/`

```java
StringFormatter.interpolate("Hello {}, you have {} new messages", "Ada", 3);
// "Hello Ada, you have 3 new messages"
StringFormatter.interpolateAll("{} and {} again", "echo");   // "echo and echo again"
StringFormatter.trimCharacters("[value]");                   // "value"

Separators.literalSeparateAsList("red, green, blue", ", ");  // [red, green, blue]
StringNormaliser.normaliseUrl("my file name.txt", StringNormaliser.SpaceMode.HYPHEN);
// "my-file-name.txt"
```

### Swing

Package `swing`: `Dialogs`, `Positioning`. Package `swing.keyinput`: `Keybind`, `Keystrokes`, `KeyModifier`

- **`Dialogs`**: one-line error, warning, info and confirmation dialogs, with optional icons and custom titles
- **`Keybind`** and **`Keystrokes`**: attach single keys or combinations with Ctrl, Shift, Alt, AltGr or Meta to a
  `JFrame`, running an `Action` or a plain `Runnable`
- **`Positioning`**: implement it on a component to place it next to or under another one, with optional offsets

```java
if (Dialogs.showConfirmationDialog("Discard unsaved changes?")) {
    Dialogs.showInfoDialog("Changes discarded");
}

Keybind.addKeybind(frame, "save", Keystrokes.ctrlPlus("S"), () -> save());
```

### Time

Package `time`: `TimeUtil`, `TimeUnit`, `TimeUnitConversion`, `TaskTimer`

- **`TimeUtil`**: capture the current moment or parse an ISO-8601 string, then read it as a formatted date, time or
  parts of either, or convert it to `Instant`, `LocalDateTime`, `ZonedDateTime`, `Calendar` or the `java.sql` types
- **`TimeUnitConversion`**: convert between milliseconds, seconds, minutes, hours and days, optionally formatted
  with the unit suffix
- **`TaskTimer`**: time how long a task takes, down to the nanosecond

```java
TimeUtil now = TimeUtil.captureInstant();
now.getFullDate();       // e.g. "November 23, 2026"
now.getActualTime();     // e.g. "16:37:18"

TimeUnitConversion.calculate(3, TimeUnit.HOURS, TimeUnit.MINUTES);               // 180
TimeUnitConversion.formatWithUnit(90.0, TimeUnit.SECONDS, TimeUnit.MINUTES);     // "1.5m"

TaskTimer timer = new TaskTimer();
runTask();
timer.formatElapsedTime(TimeUnit.MILLISECONDS);   // e.g. "20.277ms"
```

### Tokeniser

Package `tokeniser`: `Token`, `UnclosedQuoteException`

- Split a command line into arguments on spaces, keeping double-quoted parts (and escaped quotes) together
- Read flags (`-f`) and options (`--force`) separately, or any argument by position, including as a `Path`
- A quote that's never closed throws `UnclosedQuoteException`

```java
Token token = Token.tokenise("save -f --force \"my file.txt\" notes");

token.getArguments();      // [save, -f, --force, my file.txt, notes]
token.getFlags();          // [-f]
token.getOptions();        // [--force]
token.getFirstArgument();  // "save"
```

### Operating system and paths

Package `wora`: `OperatingSystem`, `PathFinder`

- **`OperatingSystem`**: find the current OS with `current()`, or identify any `os.name` value with
  `fromName(String)`
  - Covers Windows, macOS, Linux, Solaris, FreeBSD and AIX; anything else is `null`, so you decide the fallback
  - Quick checks: `isWindows()`, `isMacOS()`, `isLinux()`, `isSunOS()`, `isFreeBSD()`, `isAIX()`
- **`PathFinder`**: OS-specific paths for common folders
  - User home via the `USER_HOME` constant, and Documents via `DOCUMENTS_FOLDER` or `getDocumentsFolder()`
  - App data via `APPDATA_FOLDER` or `getAppDataFolder()`
    - Windows: `%APPDATA%` (or `AppData\Roaming` in the home folder if it's unset)
    - macOS: `~/Library/Application Support`
    - Linux and other systems: `~/.config`
  - Local app data via `getLocalAppDataFolder()`, for files that shouldn't roam
    - Windows: `%LOCALAPPDATA%` (or `AppData\Local` in the home folder if it's unset)
    - macOS and Linux: the same as `getAppDataFolder()`
  - `getUserFolder(String)` for one folder in the home folder, and `createPathFromHomeRoot(String...)` for deeper
    paths

```java
OperatingSystem.current();                  // e.g. LINUX, or null on an unlisted system
OperatingSystem.fromName("Windows 11");     // WINDOWS

Path settings = PathFinder.getLocalAppDataFolder().resolve("my-app");
Path repository = PathFinder.createPathFromHomeRoot(".m2", "repository");   // ~/.m2/repository
```

### Library version

The root package: `LeUtils`

- `LeUtils.getVersion()` returns the version of le-utils in use, also when it's shaded into another jar, or `dev`
  if it can't be read (such as when running from source without a Maven build)

---

## Building

Requires JDK 11 or newer; the Maven wrapper downloads Maven itself.

```
./mvnw verify          # mvnw.cmd verify on Windows
```

This compiles the library, runs the tests and builds the sources and Javadoc jars. The Javadoc check fails the build
on any warning, so every public class and method needs complete Javadoc.

---

## License

Le Utils is released under the [MIT License](LICENSE).
