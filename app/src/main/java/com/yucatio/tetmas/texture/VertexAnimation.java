package com.yucatio.tetmas.texture;

public class VertexAnimation {
    public static final int ANIMATION_LOOPING = 0;
    public static final int ANIMATION_NONLOOPING = 1;

    private KeyFrame[] keyFrames;

    /**
     *
     * @param keyFrames キーフレーム。ただし、1つめの要素のkeyTimeが0, それ以降の要素はkeyTime順に並んでいるものとします
     */
    public VertexAnimation(KeyFrame ... keyFrames) {
        this.keyFrames = keyFrames;
    }

    public float[] getVertex(float stateTime, int mode) {
        if (mode == ANIMATION_NONLOOPING) {
            stateTime = Math.min(stateTime, keyFrames[keyFrames.length-1].keyTime);
        } else {
            stateTime = stateTime % keyFrames[keyFrames.length-1].keyTime;
        }

        float[] targetVertex = new float[12];
        for (int i = 1; i < keyFrames.length; i++) {
            if (stateTime <= keyFrames[i].keyTime) {
                float keyTimeLength = keyFrames[i].keyTime - keyFrames[i - 1].keyTime;
                float targetTime = stateTime - keyFrames[i - 1].keyTime;
                float t = targetTime / keyTimeLength;

                for (int j = 0; j < targetVertex.length; j++) {
                    targetVertex[j] = t * keyFrames[i].vertexes[j] + (1 - t) * keyFrames[i - 1].vertexes[j];
                }
                break;
            }
        }
        return targetVertex;
    }

    public static class KeyFrame {
        float keyTime;
        float[] vertexes;

        public KeyFrame(float keyTime, float[] vertexes) {
            this.keyTime = keyTime;
            this.vertexes = vertexes;
        }
    }

}
