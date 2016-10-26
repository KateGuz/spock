package ua.spock.spock.entity;

public class Bid {
    private int id;
    private Lot lot;
    private User user;
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", lotId=" + (lot == null ? "null" : lot.getId()) +
                ", user=" + user +
                ", value=" + value +
                '}';
    }
}
