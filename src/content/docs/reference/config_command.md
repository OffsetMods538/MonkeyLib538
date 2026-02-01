---
title: Config command
---

MonkeyLib538 allows registering config commands. They are built up as so:
- Root node (usually mod id)
  - `reset` - Resets the config to default values and saves to disk.
  - `reload` - Reloads changes to the config from disk. Mods may optionally execute a callback to reload other things as well.
  - `location` - Gets the absolute path to the config file.
  - `get`
    - `optionName` - Gets the given config option's current value.
  - `set`
    - `optionName` - If there's no way to represent the option as a command argument, executing at this level will tell the user to modify the file manually.
      - `newValue` - Sets the given config option to the given new value and saves to disk. It may be required to execute `reload` after this for some changes to take effect.
