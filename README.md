# QuiltEnergy

A Library mod to make an energy api for (Ender put "four" here ☠️) TeamVoided's mods
<br><br>

Quilt Energy is a Minecraft modding api (Not developed by the Quilt devs nor has any associations with them) that adds energy to game in a way that allows for multiple energy units.
<br><br>
Quilt Energy has extendable classes for making items that have energy as well as block entities.

## Using in mods
<details>
  <summary>Repository and dependency</summary>

### Repository
```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/TeamVoided/QuiltEnergy")
        credentials {
            username = "GITHUB_USERNAME"
            password = project.findProperty("gpr.key") ?: System.getenv("TOKEN")
        }
    }
}
```

### Dependency
```groovy
dependencies {
    modImplementation "team.voided:quilt_energy:VERSION"
    // latest release is 1.1.2+1.19
}
```

For details on generating a Personal Access Token visit [The GitHub help page](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)

</details>
