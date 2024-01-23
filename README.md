# ImageNoise

Image to noise synthesizer, goes through each pixel and obtains the RGB values of that pixel.

Then, it assesses the luminance of all of the RGB values, and plays a corresponding MIDI note based on the value.

Can be run via iteration, or can be executed using a Thread Pool.

### Iteration sounds much better
Refer [here](https://soundprogramming.net/file-formats/general-midi-instrument-list/) for a list of MIDI instruments, use smaller images for better output (smaller dimensions, for fewer pixels, and thus fewer conflicting notes).
