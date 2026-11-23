# Changelog

Every release of Le Utils, newest first. Breaking changes are listed at the top of the version that made them, so
check each version between yours and the one you're moving to.

Versions follow [semantic versioning](https://semver.org): a major version can break existing code, a minor version
only adds to it, and a patch only fixes it. Releases before 5.0.0 are recorded in the
[git history](https://github.com/Andruid929/le-utils/tags).

## [5.1.0] - 2026-11-23

No breaking changes: 5.1.0 only adds to the 5.0 API, so code built against 5.0 keeps working without recompiling.

### Additions

- **[OperatingSystem](src/main/java/io/github/andruid929/leutils/wora/OperatingSystem.java)** (new)
  - Enum of `WINDOWS`, `MAC_OS`, `LINUX`, `SUN_OS` (Solaris), `FREE_BSD` and `AIX`, each with a short `getCode()`.
  - `current()` tells you which one the program is running on, and `fromName(String)` identifies any `os.name`
    value. Both return `null` for a system that isn't listed (such as OpenBSD or z/OS) instead of guessing.
  - `isWindows()`, `isMacOS()`, `isLinux()`, `isSunOS()`, `isFreeBSD()` and `isAIX()` checks.

- **[PathFinder](src/main/java/io/github/andruid929/leutils/wora/PathFinder.java)**
  - `getLocalAppDataFolder()` for files that should stay on this machine, such as caches or credentials:
    `%LOCALAPPDATA%` on Windows, so it never roams with the user's profile, and the same folder as
    `getAppDataFolder()` everywhere else.

- **[Config](src/main/java/io/github/andruid929/leutils/config/Config.java)**
  - Every getter has an overload that takes a default value, returned when the key is missing, e.g.
    `config.getInteger("port", 8080)`. A value that's present but malformed still throws, so typos in a config file
    aren't hidden behind the default.

- **[LeUtils](src/main/java/io/github/andruid929/leutils/LeUtils.java)** (new)
  - `LeUtils.getVersion()` returns the library's version, which also works when le-utils is shaded into another jar.

### Changes

- `Config.add(String, String)` and `Config.addFromMap(Map)` throw `IllegalArgumentException` for a `null` value,
  matching the other `add` overloads. They used to throw `NullPointerException`, so this only matters to code that
  catches `NullPointerException` specifically. `addFromMap` keeps the entries it added before the `null` one.
- `PathFinder` now works out the OS through `OperatingSystem`. Solaris, FreeBSD, AIX and systems it doesn't
  recognise get the Linux-style `~/.config` folder instead of failing.

### Bug fixes

- `getAppDataFolder()` on Windows no longer throws when `%APPDATA%` is unset or blank; it falls back to
  `AppData\Roaming` in the user's home folder.
- An OS name that merely contains "win", such as "Darwin", is no longer mistaken for Windows.
- `Config.readConfig()`'s error for a folder now shows the path instead of a `{}` placeholder.

## [5.0.0] - 2026-08-28

### Breaking changes

- `Config.readConfig(Path)` now throws an `IOException` if the path is a folder or the file does not exist. See the
  `failOnMissingFile` flag below.
- The `strings` package (`io.github.andruid929.leutils.strings`) has been removed, as promised in past deprecation
  notices. Everything in its one class moved to the [stringutil](src/main/java/io/github/andruid929/leutils/stringutil)
  package.

### Additions

- **[StringFormatter](src/main/java/io/github/andruid929/leutils/stringutil/StringFormatter.java)**
  - `interpolateAll()` to replace all placeholders with a single value.

- **[Config](src/main/java/io/github/andruid929/leutils/config/Config.java)**
  - `failOnMissingFile` flag that decides whether an exception is thrown when `readConfig()` cannot find the path
    specified (or if the path points to a folder). This flag stops the Config from returning empty values which
    would then overwrite the persisted values on the next write. On by default, and can be enabled or disabled by
    calling the new static `setFailOnMissingFile(boolean)`... disable it at your own risk.
  - Lines that start with `#` are treated as comments and aren't picked up as invalid lines.

### Changes

- `StringFormatter.interpolate()` now uses a `StringBuilder` instead of multiple regex calls, for better
  performance.

### Bug fixes

- Empty lines are no longer collected by [Config](src/main/java/io/github/andruid929/leutils/config/Config.java) as
  invalid lines.
- `StringFormatter.interpolate()` now operates on literals instead of the mixed regex and literal logic before.

[5.1.0]: https://github.com/Andruid929/le-utils/compare/v5.0.0...v5.1.0
[5.0.0]: https://github.com/Andruid929/le-utils/compare/v4.4.0...v5.0.0
