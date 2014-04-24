# Dawn

This project builds the yet to be released, but already amazing Valo theme as a jar file that can easily tested in a project today. 

####WARNING: You don't want to use Vaadin apps with the old default theme after applying this to one of your hobby projects

##Usage instructions:

 * check out this project
 * (optional) parametrize your theme
 * "mvn install"
 * add the built artifact to your project
 * Use dawn them in you UI: @Theme("dawn")
 
## Another, bit more advanced setup:

 * fork this repository, so that you can make your own customizations
 * Use git submodule to add it to your multimodule Maven setup
  * `git submodule add https://github.com/YOUR_ACCOUNT_FOR_FORK/dawn.git dawn`
  * add dawn to list of sub modules like in [this example](https://github.com/peterl1084/cdiexample/blob/master/pom.xml#L23)
  * add dependency to your web app module
 * Use dawn them in you UI: @Theme("dawn")
 * do full build, enjoy, no extra setup needed for your co-workers/contributors

##Pom dependency (once built)

```
<dependency>
    <groupId>org.peimari</groupId>
    <artifactId>dawn</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Also note, that you can get rid of vaadin-themes and don't need these goals in vaadin-maven-plugin (theme is already built):
```
<goal>update-theme</goal>
<goal>compile-theme</goal>
```

... and, you most probably don't ever need the "runtime theme compilation", not even with this sass theme, so get rid of theme-compiler as well, e.g.:

```
    <dependency>
            <groupId>com.vaadin</groupId>
            <artifactId>vaadin-server</artifactId>
            <version>${vaadin.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>com.vaadin</groupId>
                    <artifactId>vaadin-theme-compiler</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

# REST IS THE ORIGINAL README, NO NEED TO INSTALL ANYTHING OR USE ANY SPECIAL SERVLETS, JUST ADD THE DEPENDENCY/JAR AND USE THEME CALLED "dawn"
# ##########


Valo
====

Sass based Vaadin theme


*This is a development version of the Valo theme, not suitable for production yet.*


Testing it out
====

You need the Ruby Sass compiler to compile the theme. Visit http://sass-lang.com/install and do the command line installation.

Then use the ThemeServlet from this repo as your servlet implementation, which should handle on-the-fly Sass compilation using the command line Sass compiler (you need the Sass.java file from this repo as well).

    @WebServlet(value = "/*")
    public static class Servlet extends ThemeServlet {
    }


Once you have it up and running, check out the themes/valo/shared/variables.scss file for stuff to adjust initially. Later you can start checking out individual components as well, and see what mixins and functions you can override if necessary.

The valo-demo and valo-test themes contain use examples as well, which you can also refer to.
