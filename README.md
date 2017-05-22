# droid4me.log
Extension of the droid4me framework which contains logger implementation

Available implementations :

* logEntries
* logmatic
* log4j

## Usage

### As Library Project

Check out this repository and add it as a library project.

Import the project into your favorite IDE and add `android.library.reference.1=/path/to/droid4me.log/the_choosen_module` to your `project.properties`.

### Generate an aar file

Library releases are not available on Maven Central or JCenter but you can generate an aar file by your owned :

```console
gradle clean choosen_module:assembleRelease
```

or

```console
gradle -b choosen_module/build.gradle clean assembleRelease
```

## Author

The Android Team @Smart&Soft, software agency http://www.smartnsoft.com

## License

droid4me.log is available under the MIT license. See the LICENSE file for more info.
