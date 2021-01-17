package com.example.chatakudong.menu;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatakudong.R;
import com.example.chatakudong.adapter.AdapterTab;
import com.example.chatakudong.databinding.FragmentTabBinding;
import com.example.chatakudong.model.Tab;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link KontakFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class KontakFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private FragmentTabBinding binding;
    private List<Tab> list = new ArrayList<>();
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    public KontakFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment KontakFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static KontakFragment newInstance(String param1, String param2) {
//        KontakFragment fragment = new KontakFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_tab, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab, container, false);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(firebaseUser != null){
            bacaKontak();
        }

        return binding.getRoot();
    }

    private void bacaKontak() {
        firebaseFirestore.collection("Akun").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    String ID = snapshot.getString("id");

                    Tab akun = new Tab(ID,
                            snapshot.getString("keterangan"),
                            snapshot.getString("nama"),
                            snapshot.getString("notelpon"),
                            snapshot.getString("tanggal"));

                    if(ID != null && !ID.equals(firebaseUser.getUid())){
                        list.add(akun);
                    }
                }
                binding.recyclerView.setAdapter(new AdapterTab(list, getContext()));
            }
        });
    }
}