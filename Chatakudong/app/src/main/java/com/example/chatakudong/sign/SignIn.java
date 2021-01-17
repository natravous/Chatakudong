package com.example.chatakudong.sign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chatakudong.MainActivity;
import com.example.chatakudong.R;
import com.example.chatakudong.databinding.ActivitySignInBinding;
import com.example.chatakudong.model.Tab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class SignIn extends AppCompatActivity {

    private static final String TAG = "PhoneAuthActivity";

    ActivitySignInBinding binding;

    private FirebaseAuth mAuth;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private ProgressDialog progressDialog;

    private FirebaseFirestore mFireStore;

    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
//        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        mFireStore = FirebaseFirestore.getInstance();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


        progressDialog = new ProgressDialog(this);

        binding.buttonMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.buttonMasuk.getText().toString().equals("MASUK")){
                    progressDialog.setMessage("Mengirim Kode Verifikasi");
                    progressDialog.show();

                    String nomorPonsel = binding.editTextPonsel.getText().toString();
                    startPhoneNumberVerification(nomorPonsel);
                } else{
                    progressDialog.setMessage("Verifikasi Kode");
                    progressDialog.show();

                    verifyPhoneNumberWithCode(mVerificationId, binding.editTextVerifikasi.getText().toString());
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "Verifikasi Berhasil");
                signInWithPhoneAuthCredential(phoneAuthCredential);
                progressDialog.dismiss();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d(TAG, "Verifikasi Gagal: " + e.getMessage());
                progressDialog.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                binding.buttonMasuk.setText("Verifikasi");
                progressDialog.dismiss();
                // [START_EXCLUDE]
                // Update UI
//                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]

//        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            if(user != null){
                                String ID = user.getUid();
                                Tab akun = new Tab(ID, user.getPhoneNumber(),"","","");

                                mFireStore.collection("Akun").document(user.getUid()).collection(ID)
                                        .add(akun).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        startActivity(new Intent(SignIn.this, InfoAkun.class));
                                        finish();
                                    }
                                });
                            } else{
                                Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            }



                            // [START_EXCLUDE]
//                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
//                                mBinding.fieldVerificationCode.setError("Invalid code.");
                                Log.w(TAG, "KODE SALAH");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
//                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

}