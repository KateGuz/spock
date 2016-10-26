package ua.spock.spock.entity;

public class Category {
    private int id;
    private String name;
    private Category parent;

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

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Category{" + "id=" + id + ", name='" + name + '\'');
        if (parent != null) {
            out.append(", parentName='" + parent.getName() +"'");
        }
        out.append('}');
        return out.toString();
    }
}
