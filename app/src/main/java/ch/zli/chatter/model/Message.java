package ch.zli.chatter.model;

public class Message {
    private String text;
    private String name;
    private String imageUrl;

    // Empty constructor needed for Firestore serialization
    public Message(){}

    public Message(String text, String name, String imageUrl) {
        this.text = text;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
