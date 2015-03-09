# Dawn

This is an "alternative build" of the awesome Valo theme introduced in Vaadin 7.3. It can be used as a direct replacement in your Vaadin app for the artifact *com.vaadin:vaadin-themes*.

Relevant changes to the basic Valo theme:

 * Loads FontAwesome font from bootstrapcdn
  * loads faster (most likely cached by your browsers) and spends less bandwidth on your server
 * Loads Open Sans font from Google Fonts
  * loads faster (most likely cached by your browsers) and spends less bandwidth on your server
 * Leaves out heavy font files from the jar file
  * saves ~ 6 MB of your war file size
  * deploys faster

##Pom dependency

```
<dependency>
    <groupId>org.peimari</groupId>
    <artifactId>dawn</artifactId>
    <version>3</version>
</dependency>
```

As stated above, you can also remove (or exclude ) the dependency to *com.vaadin:vaadin-themes*.

Builds distributed via [Vaadin Directory](https://vaadin.com/directory).

## Planned features

 * CDN distribution of the whole theme, with separate even slimmer module that just adds details about the location of the theme
 * Couple of alternative pre-built Valo variations:
  * dawn-pro - smaller fonts, smaller margins
  * dawn-dark
  * dawn-pro-dark

