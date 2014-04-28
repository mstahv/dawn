Valo
====

Sass based Vaadin theme


***The development of the Valo theme continues in the main Vaadin git repository, still repo will not receive direct updates anymore***
 * Once there's a stable build ready, it will be synced to GitHub as well 
 * You can still report issues you find here in this GitHub repository

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
