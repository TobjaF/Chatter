package ch.zli.chatter;
/* Disclaimer:
 * Der Code in diesem .java-File ist entstanden beim befolgen eines offiziellen Firebase-Android Codelab, und gleicht diesem deshalb stark
 *
 * Das betreffende Tutorial ist verfügbar unter https://firebase.google.com/codelabs/firebase-android,
 * der dazugehörige Source-Code ist verfügbar unter https://github.com/firebase/codelab-friendlychat-android
 *
 * */
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;

public class ButtonObserver implements TextWatcher {

    private ImageView button;
    public ButtonObserver(ImageView button){
        this.button = button;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().trim().isEmpty()) {
            button.setEnabled(true);
            button.setImageResource(R.drawable.outline_send_24);
        } else {
            button.setEnabled(false);
            button.setImageResource(R.drawable.outline_send_gray_24);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
