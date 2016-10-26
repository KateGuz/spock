package ua.spock.spock.dao.mapper.util;

public enum LotQueryType {
    GET_ALL("GetAll"), GET_ONE("GetOne");
    private String id;

    LotQueryType(String id) {
        this.id = id;
    }

    public LotQueryType getQueryTypeById(String id){
        for (LotQueryType lotQueryType : LotQueryType.values()) {
            if(lotQueryType.getId().equals(id)){
                return lotQueryType;
            }
        }
        throw new IllegalArgumentException("No lotQueryType found for id = " + id);
    }

    public String getId() {
        return id;
    }
}
