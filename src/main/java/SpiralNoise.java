import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class SpiralNoise extends PApplet {
    public static void main(String[] args) {
        PApplet.main(SpiralNoise.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        stroke(STROKE_COLOR.red(), STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());
        fill(BORDER_COLOR.red(), BORDER_COLOR.green(), BORDER_COLOR.blue(), BORDER_COLOR.alpha());
        noLoop();
    }

    @Override
    public void draw() {
        for (int x = 0; x < width; x += STEP) {
            for (int y = 0; y <= height; y += STEP) {
                PVector p = new PVector(x + random(-OFFSET_RANGE, OFFSET_RANGE),
                        y + random(-OFFSET_RANGE, OFFSET_RANGE));
                for (int l = 0; l < LINE_LENGTH; l++) {
                    point(p.x, p.y);
                    final float sideNumber =
                            NOISE_MIN_DIRECTIONS + NOISE_DIRECTIONS_FACTOR * (((dist(p.x, p.y, width / 2f,
                                    height / 2f) / NOISE_RING_RADIUS)) % NOISE_RING_FACTOR);
                    final float angle = TWO_PI / sideNumber
                            * floor(noise(p.x * NOISE_SCALE, p.y * NOISE_SCALE) * sideNumber);
                    p.add(PVector.fromAngle(angle).mult(LINE_STEP_SIZE));
                }
            }
        }

        rect(0, 0, width, BORDER_SIZE);
        rect(0, 0, BORDER_SIZE, height);
        rect(0, height - BORDER_SIZE, width, BORDER_SIZE);
        rect(width - BORDER_SIZE, 0, BORDER_SIZE, height);

        saveSketch(this);
    }
}
