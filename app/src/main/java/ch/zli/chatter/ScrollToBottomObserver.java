package ch.zli.chatter;
/* Disclaimer:
 * Der Code in diesem .java-File ist entstanden beim befolgen eines offiziellen Firebase-Android Codelab, und gleicht diesem deshalb stark
 *
 * Das betreffende Tutorial ist verfügbar unter https://firebase.google.com/codelabs/firebase-android,
 * der dazugehörige Source-Code ist verfügbar unter https://github.com/firebase/codelab-friendlychat-android
 *
 * */
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver;

public class ScrollToBottomObserver extends AdapterDataObserver{

    private RecyclerView recycler;
    private ChatMessageAdapter adapter;
    private LinearLayoutManager manager;

    ScrollToBottomObserver(
            RecyclerView recycler ,
            ChatMessageAdapter adapter ,
            LinearLayoutManager manager
    ){
        this.adapter = adapter;
        this.manager = manager;
        this.recycler = recycler;
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        int count = adapter.getItemCount();
        int lastVisiblePosition = manager.findLastCompletelyVisibleItemPosition();
        // If the recycler view is initially being loaded or the
        // user is at the bottom of the list, scroll to the bottom
        // of the list to show the newly added message.
        if (lastVisiblePosition == -1 || (positionStart >= count - 1 && lastVisiblePosition == positionStart - 1)) {
            recycler.scrollToPosition(positionStart);
        }
    }
}
