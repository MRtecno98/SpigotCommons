# Spigot Commons
![](https://img.shields.io/github/workflow/status/MRtecno98/SpigotCommons/SpigotCommons) [![CodeFactor](https://www.codefactor.io/repository/github/mrtecno98/spigotcommons/badge)](https://www.codefactor.io/repository/github/mrtecno98/spigotcommons) ![](https://img.shields.io/github/license/MRtecno98/SpigotCommons) ![](https://img.shields.io/badge/documentation-WIP-blue)

Spigot Commons helps you coding boring and redundant stuff using the Spigot API.\
This work is licensed under the GNU General Public License v3.0, documentation will be available soon.

# Installation
You can easily add this library to your project using Maven or every other similar tool which supports maven respositories

## Maven
```
<repositories>
    <repository>
        <id>tecno-repo</id>
        <url>http://repo.mrtecno.online/repository/public-maven/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.spigot.libraries</groupId>
        <artifactId>spigot-commons</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```

## Gradle
### Groovy DSL
```
repositories {
    tecno {
        url "http://repo.mrtecno.online/repository/public-maven/"
    }
}

dependencies {
    implementation "com.spigot.libraries:spigot-commons:VERSION"
}
```

### Kotlin DSL
```
repositories {
    tecno {
        url = uri("http://repo.mrtecno.online/repository/public-maven/")
    }
}

dependencies {
    implementation("com.spigot.libraries:spigot-commons:VERSION")
}
```

# Compiling
You can also compile the library yourself by using maven with the `pom.xml` file present in this repository. <br>
You'll need to have Java 8 or higher and Apache Maven installed, 
then you just need to clone the repo on your local machine and start the maven build.

```
git clone https://github.com/MRtecno98/SpigotCommons.git
cd SpigotCommons
mvn clean package
```

And there you go, after the build the final jar will be stored in the `target` folder of the cloned repo.


## Custom maven profiles
The project's POM contains some custom profiles for automatically executing specific actions

* `major-version`: Deactivates the Git Commit ID Plugin and removes its snippet from the version, used for building major versions which are not commit-linked
