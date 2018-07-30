package enums;

public enum BookWebEnum {
    笔趣阁("BeQuGeBooks"),书趣阁("ShuQuGeBooks");
    BookWebEnum(String name){
        this.name = name;
    }
    private String name;

    public String getName() {
        return name;
    }

}
