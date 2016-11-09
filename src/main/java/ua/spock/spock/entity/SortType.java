package ua.spock.spock.entity;

public class SortType {
    private String sortType;
    private Integer categoryId;

    public SortType(String sortType, Integer categoryId) {
        this.sortType = sortType;
        this.categoryId = categoryId;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
