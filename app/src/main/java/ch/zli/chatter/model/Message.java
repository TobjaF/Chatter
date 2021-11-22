package ch.zli.chatter.model;

public class Message {
    private String text;
    private String name;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Empty constructor needed for Firestore serialization
    public Message(){}

    public Message(String text, String name, String imageUrl) {
        this.text = text;
        this.name = name;
    }
}
