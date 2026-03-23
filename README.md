# Le Utils

A lightweight Java utility library providing helpful functions for common repetitive tasks.

## Features

### Error Handling

- **ErrorMessageHandler**: Simple utility for formatting and printing exception messages
    - Get clean, formatted error messages from exceptions
    - Print errors to custom output streams
    - Handles exceptions with or without messages
    - Get stack traces from exceptions as Strings

### String Tokenisation

- **String Tokeniser**: Parse and tokenise strings with quote handling
    - Respects quoted strings (double quotes)
    - Respects escaped double quotes
    - Custom exception handling for unclosed quotes

### Date and Time Utilities

Epoll-based date and time utilities.

- **Time utilities**: Parse and format time strings
- **Date utilities**: Parse and format date strings

Time unit conversion.

- Convert between any two time units: milliseconds, seconds, minutes, hours, days

Task time calculation

- Time taken for tasks to complete down to the nanosecond

### Config

Create and read configs with a simple and readable key:value pair format.

- Collect configs in a global configuration, persist it to any file of your choosing,
  read the configs with an immutable object with getters.

### String utilities

- **Trim leading and/or trailing characters**
- **Normalise URL strings**

## What’s New (3.3.0)

### Config
  - Better handling of array parsing

### StringUtil
  - Normalise string URLs: Replace slashes with backslashes and spaces with either
    encoded spaces or hyphens.

## Requirements

- Java 21 or higher
- Dependencies:
    - JetBrains Annotations 26.0.2

## Installation

### Maven

In `pom.xml`:
```xml
<dependency>
    <groupId>io.github.andruid929</groupId>
    <artifactId>le-utils</artifactId>
    <version>3.4.0</version>
</dependency>
```

### Gradle

In `build.gradle`:
```groovy
implementation 'io.github.andruid929:le-utils:3.4.0'
```
