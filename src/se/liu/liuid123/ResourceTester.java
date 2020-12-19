package se.liu.liuid123;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * A simple test class used to exemplify how resources work.
 */
public class ResourceTester
{
    public static void main(String[] args) {
        testResourceText();
        testResourceImage();
    }

    private static void testResourceText() {
        // Reading a text file from *resources* is a bit cumbersome.
        // We don't know if the file is stored directly in the file system
        // or inside an *archive* (JAR file), so should access it
        // as a "resource" identified by an URL.
        final URL readme = ClassLoader.getSystemResource("README.md");

        // Then we can use this URL to open an *input stream*,
        // create an InputStreamReader that converts bytes to characters
        // according to the default character encoding (typically UTF-8),
        // and then createa a BufferedReader which can be used to read lines.
        //
        // All of this is insiude a "try" statement that ensures the streams
        // and readers are closed when we are done.
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(readme.openStream()))) {
            System.out.println("Contents of the file:");

            // Read and print strings until you get null, which indicates "end of file"
            // for Reader objects
            String str = reader.readLine();
            while (str != null) {
                System.out.println(str);
                str = reader.readLine();
            }
        } catch (IOException e) {
            // TODO: Needs to be handled somehow.  This code is incomplete and should be
            //  extended by course participants.
            e.printStackTrace();
        }
    }


    private static void testResourceImage() {
        // Like above, we need to access the image through a resource.
        final URL image = ClassLoader.getSystemResource("images/hello_world.png");

        // The ImageIcon class can read an entire image directly from any URL.
        ImageIcon icon = new ImageIcon(image);

        // We won't go all the way to showing the image here, but we can print
        // some information about it!
        System.out.println("Read an image with width " + icon.getIconWidth() + " and height " + icon.getIconHeight());
    }
}
