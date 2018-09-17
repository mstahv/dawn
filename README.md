[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/dawn)
[![Stars on vaadin.com/directory](https://img.shields.io/vaadin-directory/star/dawn.svg)](https://vaadin.com/directory/component/dawn)

# Dawn

This is an "alternative build" of the awesome Valo theme introduced in Vaadin 7.3. It can be used as a direct replacement in your Vaadin app for the artifact *com.vaadin:vaadin-themes*.

Relevant changes to the basic Valo theme:

 * Loads FontAwesome font from bootstrapcdn
  * loads faster (most likely cached by your browsers) and spends less bandwidth on your server
 * Loads Open Sans font from Google Fonts
  * loads faster (most likely cached by your browsers) and spends less bandwidth on your server
 * Leaves out heavy font files from the jar file
  * saves ~ 7 MB of your war file size
  * deploys faster
 * Leaves out older legacy themes

##Pom dependency

```
<dependency>
    <groupId>org.peimari</groupId>
    <artifactId>dawn</artifactId>
    <version>4</version>
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

