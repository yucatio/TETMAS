package com.yucatio.tetmas.texture;

public class TextureRegion {
    public final float u1, v1;
    public final float u2, v2;
    public final float[] textureCoord;
    public final Texture texture;

    public TextureRegion(Texture texture, float x, float y, float width, float height) {
        this.u1 = x / texture.getWidth();
        this.v1 = y / texture.getHeight();
        this.u2 = u1 + width / texture.getWidth();
        this.v2 = v1 + height / texture.getHeight();
        textureCoord = new float[] {
                u1, v1,
                u1, v2,
                u2, v1,
                u2, v2
        };

        this.texture =texture;
    }
}
