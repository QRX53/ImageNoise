package main;

import javax.imageio.ImageIO;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static String fpath;
    public static RunType rt;
    public static int playFor = 100; // how long to play the noise for (in ms)

    public static void main(String[] args) {
        List<SoundPlayer.Pixel> pixels = new ArrayList<>();
        System.out.print("Enter the file path: ");
        fpath = new Scanner(System.in).nextLine();

        if (new File(fpath).exists()) {
            System.out.println("Loading file...");
            try {
                BufferedImage buff = ImageIO.read(new File(fpath));

                for (int x = 0; x < buff.getWidth(); x++) {
                    for (int y = 0; y < buff.getHeight(); y++) {
                        Color c = new Color((buff.getRGB(x, y)));
                        SoundPlayer.Pixel s = new SoundPlayer.Pixel(c.getRed(), c.getGreen(), c.getBlue());
                        pixels.add(s);
                    }
                }

            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
            }
            System.out.println("File loaded.");
        } else {
            System.err.println("No file at given path '" + fpath + "'");
            System.exit(-1);
        }

        System.out.print("Enter MIDI instrument number (0 - 15): ");
        SoundPlayer.inst = new Scanner(System.in).nextInt();
        System.out.println();

        System.out.print("Enter RunType (1 for simultaneous noise, 2 for iterating through each pixel (recommended)): ");
        int which = new Scanner(System.in).nextInt();
        System.out.println();

        if (which == 1) {
            rt = RunType.Simultaneous;
        } else if (which == 2) {
            rt = RunType.Iterate;
        } else {
            System.out.println("Incorrect option chosen, using Iteration.");
            rt = RunType.Iterate;
        }

        // src/main/resources/Slackline-VDN-081523-2-622x1024.jpg
        try {
            new SoundPlayer(pixels).runf(RunType.Iterate);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }
}