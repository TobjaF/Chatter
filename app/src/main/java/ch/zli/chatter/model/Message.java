package ch.zli.chatter.model;
/* Disclaimer:
 * Der Code in diesem .java-File ist entstanden beim befolgen eines offiziellen Firebase-Android Codelab, und gleicht diesem deshalb stark
 *
 * Das betreffende Tutorial ist verfügbar unter https://firebase.google.com/codelabs/firebase-android,
 * der dazugehörige Source-Code ist verfügbar unter https://github.com/firebase/codelab-friendlychat-android
 *
 * */
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
