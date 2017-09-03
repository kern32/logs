package entity;

public enum Type {

    SUCCESS("success"), DANGER("danger"), INFO("info"), WARNING("warning"), ACTIVE("active");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
