package com.example.chatakudong;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatakudong.databinding.ActivityMainBinding;
import com.example.chatakudong.menu.ChatFragment;
import com.example.chatakudong.menu.KontakFragment;
import com.example.chatakudong.sign.SignIn;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        aturViewPager(binding.tampilanPager);
        binding.tabLayout.setupWithViewPager(binding.tampilanPager);

        binding.buttonKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogKeluar();
            }
        });
    }

    private void dialogKeluar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Keluar dari akun?");
        builder.setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, SignIn.class));
                finish();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void aturViewPager(ViewPager viewPager){
        MainActivity.aturPagerAdapter adapter = new aturPagerAdapter(getSupportFragmentManager());
        adapter.tambahFragment(new ChatFragment(), "Chat");
        adapter.tambahFragment(new KontakFragment(), "Kontak");

        viewPager.setAdapter(adapter);
    }

    private static class aturPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> stringList = new ArrayList<>();

        public aturPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);
            return stringList.get(position);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
//            return null;
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
//            return 0;
            return fragmentList.size();
        }

        void tambahFragment(Fragment fragment, String judul){
            fragmentList.add(fragment);
            stringList.add(judul);
        }
    }
}