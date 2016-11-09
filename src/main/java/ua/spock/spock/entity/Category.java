package ua.spock.spock.entity;


import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private Category parent;
    private List<Category> children = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        List<String> childrenIds = new ArrayList<>();
        if (children.size() >0) {
            for (Category category : children) {
                childrenIds.add("{id= " + category.getId() + ", name= " + category.getName() + "}");
            }
        }
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + (parent == null ? "null" : parent.getId()) +
                ", children =" +(children.size() == 0 ? "none" : childrenIds) +
                '}';
    }
}
