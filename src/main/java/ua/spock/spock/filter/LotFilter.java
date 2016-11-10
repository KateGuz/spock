package ua.spock.spock.filter;

import ua.spock.spock.entity.SortType;

public class LotFilter {
    private SortType sortType;
    private Integer categoryId;

    public LotFilter(SortType sortType, Integer categoryId) {
        this.sortType = sortType;
        this.categoryId = categoryId;
    }

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
}
