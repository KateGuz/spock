package ua.spock.spock.entity;

import java.time.LocalDateTime;

public class Lot {
    private int id;
    private String title;
    private String description;
    private Category category;
    private User user;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Currency currency;
    private double startPrice;
    private double minStep;
    private Bid maxBid;
    private double quickBuyPrice;
    private LotType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getMinStep() {
        return minStep;
    }

    public void setMinStep(double minStep) {
        this.minStep = minStep;
    }

    public Bid getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(Bid maxBid) {
        this.maxBid = maxBid;
    }

    public double getQuickBuyPrice() {
        return quickBuyPrice;
    }

    public void setQuickBuyPrice(double quickBuyPrice) {
        this.quickBuyPrice = quickBuyPrice;
    }

    public LotType getType() {
        return type;
    }

    public void setType(LotType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Lot{" + "id=" + id + ", title='" + title + '\'');
        if (description != null) {
            out.append( ", description='" + description + '\'');
        }
        if (category != null) {
            out.append( ", categoryName='" + category.getName() + '\'');
        }
        if (user != null) {
            out.append( ", userName='" + user.getName() + '\'');
        }
        if (startDate != null) {
            out.append(", startDate=" + startDate);
        }
        out.append(", endDate=" + endDate + ", currency=" + currency + ", startPrice=" + startPrice);
        if (minStep != 0) {
            out.append(", minStep=" + minStep);
        }
        if (maxBid != null) {
            out.append(", maxBidValue=" + maxBid.getValue());
        }
        out.append(", quickBuyPrice=" + quickBuyPrice + ", type=" + type + '}');

        return out.toString();
    }
}
