package ch.zli.chatter;
/* Disclaimer:
 * Der Code in diesem .java-File ist entstanden beim befolgen eines offiziellen Firebase-Android Codelab, und gleicht diesem deshalb stark
 * Es wurden einige kleine Anpassungen gemacht und sonst ist der grösste Unterschied, dass das Codelab Kotlin benützt, dieses Projekt hingegen Java
 *
 * Das betreffende Tutorial ist verfügbar unter https://firebase.google.com/codelabs/firebase-android,
 * der dazugehörige Source-Code ist verfügbar unter https://github.com/firebase/codelab-friendlychat-android
 *
 * */
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import ch.zli.chatter.R;
import ch.zli.chatter.databinding.ActivityMainBinding;
import ch.zli.chatter.model.Message;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private LinearLayoutManager manager;
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private ChatMessageAdapter adapter;


    private final String TAG = "MainActivity";
    private final String MESSAGES_CHILD = "messages";
    private final String ANONYMOUS = "anonymous";
    private final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null){
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }


        // Initialize Realtime Database
        db = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = db.getReference().child(MESSAGES_CHILD);

        // The FirebaseRecyclerAdapter class and options come from the FirebaseUI library
        // See: https://github.com/firebase/FirebaseUI-Android

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(messagesRef, Message.class)
                .build();

        adapter = new ChatMessageAdapter(options, getUserName());
        binding.progressBar.setVisibility(ProgressBar.INVISIBLE);
        manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        binding.messageRecyclerView.setLayoutManager(manager);
        binding.messageRecyclerView.setAdapter(adapter);

        // Scroll down when a new message arrives
        // See MyScrollToBottomObserver for details
        adapter.registerAdapterDataObserver(
                new ScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
        );

        // Disable the send button when there's no text in the input field
        // See MyButtonObserver for details
        binding.messageEditText.addTextChangedListener(new ButtonObserver(binding.sendButton));

        // When the send button is clicked, send a text message
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(
                        binding.messageEditText.getText().toString(),
                        getUserName(),
                        null
                );
                db.getReference().child(MESSAGES_CHILD).push().setValue(message);
                binding.messageEditText.setText("");

            }
        };

        binding.sendButton.setOnClickListener(btnClick);

    }

    @Override
    protected void onStart(){
    super.onStart();
        if (auth.getCurrentUser() == null){
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }
    }

    @Override
    public void onPause() {
        adapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_out_menu ) {
            signOut();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }

    }

    private void signOut() {
        AuthUI.getInstance().signOut(this);
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    private String getUserName() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        } else return ANONYMOUS;
    }
}