fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## iOS

### ios download_metadata

```sh
[bundle exec] fastlane ios download_metadata
```

Download existing metadata and screenshots from App Store Connect

### ios upload_metadata

```sh
[bundle exec] fastlane ios upload_metadata
```

Upload metadata to App Store Connect (no binary)

### ios upload_screenshots

```sh
[bundle exec] fastlane ios upload_screenshots
```

Upload screenshots to App Store Connect

### ios upload_icon

```sh
[bundle exec] fastlane ios upload_icon
```

Upload app icon to App Store Connect

### ios upload_all

```sh
[bundle exec] fastlane ios upload_all
```

Upload everything (metadata, screenshots, icon)

### ios validate_metadata

```sh
[bundle exec] fastlane ios validate_metadata
```

Validate metadata without uploading

### ios build

```sh
[bundle exec] fastlane ios build
```

Build the app for App Store release

### ios upload_binary

```sh
[bundle exec] fastlane ios upload_binary
```

Upload binary to App Store Connect

### ios release

```sh
[bundle exec] fastlane ios release
```

Full release: build and upload everything

### ios test

```sh
[bundle exec] fastlane ios test
```

Run tests

### ios screenshots

```sh
[bundle exec] fastlane ios screenshots
```

Take screenshots for all locales

### ios sync_certs

```sh
[bundle exec] fastlane ios sync_certs
```

Sync certificates and provisioning profiles

### ios add_device

```sh
[bundle exec] fastlane ios add_device
```

Register new devices

### ios version

```sh
[bundle exec] fastlane ios version
```

Print current version and build number

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
