package ua.spock.spock.entity;

public class Image {
    private int lotId;
    private byte[] image;

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
