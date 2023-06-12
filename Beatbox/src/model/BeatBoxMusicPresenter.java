package model;

public class BeatBoxMusicPresenter {
    private final BeatBoxMusicModel model;

    public BeatBoxMusicPresenter() {
        this.model = new BeatBoxMusicModel();
    }

    public int[] fetchInstrumentKeys() {
        return model.getInstruments();
    }
}
