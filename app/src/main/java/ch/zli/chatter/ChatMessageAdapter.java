package ch.zli.chatter;
/* Disclaimer:
 * Der Code in diesem .java-File ist entstanden beim befolgen eines offiziellen Firebase-Android Codelab, und gleicht diesem deshalb stark
 * Es wurden einige kleine Anpassungen gemacht und sonst ist der grösste Unterschied, dass das Codelab Kotlin benützt, dieses Projekt hingegen Java

 * Das betreffende Tutorial ist verfügbar unter https://firebase.google.com/codelabs/firebase-android,
 * der dazugehörige Source-Code ist verfügbar unter https://github.com/firebase/codelab-friendlychat-android
 *
 * */

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.*;
//import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import ch.zli.chatter.databinding.ActivityMainBinding;
import ch.zli.chatter.databinding.MessageBinding;

import ch.zli.chatter.model.Message;
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.ktx.storage

public class ChatMessageAdapter extends FirebaseRecyclerAdapter<Message, ViewHolder> {

    private FirebaseRecyclerOptions<Message> options;
    private String currentUserName;
    //private ActivityMainBinding activityMainBindingbinding;
    //private MessageBinding messageBinding;
    final String TAG = "MessageAdapter";
    final int VIEW_TYPE_TEXT = 1;
    final int VIEW_TYPE_IMAGE = 2;


    public ChatMessageAdapter(FirebaseRecyclerOptions<Message> options, String currentUserName){
        super(options);
        this.options = options;
        this.currentUserName = currentUserName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.message, parent, false);
            MessageBinding binding = MessageBinding.bind(view);
            return new MessageViewHolder(binding);
        }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Message model) {
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
        messageViewHolder.bind(model);
    }

    /*@Override
    public int getItemViewType(int position){
        if (options.getSnapshots().get(position).getText() != null) return VIEW_TYPE_TEXT;
        else return VIEW_TYPE_IMAGE;
    }*/

    class MessageViewHolder extends ViewHolder{
        private MessageBinding binding;

        MessageViewHolder(MessageBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Message item) {
            binding.messageTextView.setText(item.getText());
            setTextColor(item.getName(), binding.messageTextView);


        if (item.getName() == null) binding.messengerTextView.setText("anonymous");
        else binding.messengerTextView.setText(item.getName());
        binding.messengerImageView.setImageResource(R.drawable.ic_account_circle_black_36dp);
        }

        private void setTextColor (String userName, TextView textView) {
            if (userName != "anonymous" && currentUserName == userName && userName != null) {
                textView.setBackgroundResource(R.drawable.rounded_message_blue);
                textView.setTextColor(Color.WHITE);
            } else {
                textView.setBackgroundResource(R.drawable.rounded_message_gray);
                textView.setTextColor(Color.BLACK);
            }
        }
    }

}
