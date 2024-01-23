package main;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundPlayer {
    public List<Pixel> list;
    public static int inst = 14;

    public SoundPlayer(List<Pixel> list) {
        this.list = list;
    }

    public SoundPlayer runf(RunType rt) throws MidiUnavailableException, InterruptedException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        MidiChannel[] channels = synth.getChannels();
        System.out.println(synth.getAvailableInstruments()[inst]);

        if (inst > channels.length) {
            System.err.println("Inst is out of bounds. (index " + inst + " of length " + channels.length + ").");
        }
        if (channels[inst] == null) {
            System.out.println(inst + " is null. Lowering to closest acceptable instrument");
            while (true) {
                if (channels[inst] == null) {
                    inst--;
                } else {
                    break;
                }
            }
        }

        if (rt.equals(RunType.Iterate)) { // iterate through each pixel and play the noise
            for (Pixel p : this.list) {
                channels[inst].noteOn((int) p.score, 80);
                Thread.sleep(Main.playFor);
            }
        } else if (rt.equals(RunType.Simultaneous)) { // submit each pixel's noise operation to an executor pool.
            try (ExecutorService exe = Executors.newFixedThreadPool(16)) {
                for (Pixel p : this.list) {
                    int finalInst = inst;
                    exe.submit(() -> {
                        channels[finalInst].noteOn((int) p.score, 80);
                        try {
                            Thread.sleep(Main.playFor);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                    });
                }
            }
        }
        synth.close();
        return this;
    }

    public static class Pixel {
        public int r;
        public int g;
        public int b;
        public double score;

        public Pixel(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.score = (0.299 * this.r + 0.587 * this.g + 0.114 * this.b);
        }
    }
}

