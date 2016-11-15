package ua.spock.spock.filter;

import ua.spock.spock.entity.SortType;

public class LotFilter {
    private SortType sortType;
    private Integer categoryId;
    private Integer userId;


    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
