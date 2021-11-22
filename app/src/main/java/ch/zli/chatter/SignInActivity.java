package ch.zli.chatter;

import static java.util.Collections.list;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import ch.zli.chatter.databinding.ActivitySignInBinding;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;

import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;

    // --->>> private ActivityResultLauncher<Intent> signIn = registerForActivityResult(FirebaseAuthUIActivityResultContract.class, this.onSignInResult);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();

            // --->>> signIn.launch(signInIntent);
        } else {
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Log.d("SignInActivity", "Sign in successful!");
            goToMainActivity();
        } else {
            Toast.makeText(
                    this,
                    "There was an error signing in",
                    Toast.LENGTH_LONG).show();

            IdpResponse response = result.getIdpResponse();

            if (response == null) {
                Log.w("SignInActivity", "Sign in canceled");
            } else {
                Log.w("SignInActivity", "Sign in error", response.getError());
            }
        }
    }
}