# ImageNoise

A quick and dirty project which takes an input image file, and goes each pixel and obtains the RGB values of said pixels.

Then, it assesses the luminance of the RGB values, and plays a corresponding MIDI note based on the value.

Can be run via iteration, or can be executed using a Thread Pool.

## Iteration sounds much better
Refer [here](https://soundprogramming.net/file-formats/general-midi-instrument-list/) for a list of MIDI instruments.