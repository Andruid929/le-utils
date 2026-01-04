# Le Utils

A lightweight Java utility library providing helpful functions for common repetitive tasks.

## Features

### Error Handling

- **ErrorMessageHandler**: Simple utility for formatting and printing exception messages
    - Get clean, formatted error messages from exceptions
    - Print errors to custom output streams
    - Handles exceptions with or without messages

### String Tokenisation

- **String Tokenizer**: Parse and tokenise strings with quote handling
    - Respects quoted strings (double quotes)
    - Custom exception handling for unclosed quotes

## Requirements

- Java 21 or higher
- Dependencies:
    - Gson 2.13.2
    - JetBrains Annotations 26.0.2 (optional)

## Installation

### Maven

Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>io.github.andruid929</groupId>
    <artifactId>le-utils</artifactId>
    <version>2.0.0</version>
</dependency>
```

### Gradle

Add the following dependency to your `build.gradle`:
```groovy
implementation 'io.github.andruid929:le-utils:2.0.0'
```
