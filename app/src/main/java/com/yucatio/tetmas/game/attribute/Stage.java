package com.yucatio.tetmas.game.attribute;

public enum Stage {
    EASY("easy001", "easy001"), MIDDLE("middle001", "middle001"), HARD("hard001", "hard001"), TWO_PLAYER("two_player001", "two_player001");

    private String gameDataId;
    private String winLossRecordFileName;

    private Stage(String gameDataId, String winLossRecordFileName) {
        this.gameDataId = gameDataId;
        this.winLossRecordFileName = winLossRecordFileName;
    }

    public String getGameDataId() {
        return gameDataId;
    }

    public String getWinLossRecordFileName() {
        return winLossRecordFileName;
    }

}
