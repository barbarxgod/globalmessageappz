package com.example.myapplication.ui.addtogroup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DirectoryModel;
import com.example.myapplication.GroupModel;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class addtogroupFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    RecyclerView groupsRcyView, directoriesRcyView;
    TextView selectedGroupTxtView;

    GroupModel groupModel;

    ArrayList<GroupModel> groupModelArrayList;
    ArrayList<DirectoryModel> groupsDirectoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addtogroup, container, false);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        groupsRcyView = view.findViewById(R.id.rycGroupSelector);
        directoriesRcyView = view.findViewById(R.id.rycMsgs);
        selectedGroupTxtView = view.findViewById(R.id.addingGroupSelected);

        groupModelArrayList = new ArrayList<>();
        groupsDirectoryList = new ArrayList<>();

        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGrant -> {
            if (isGrant) {
                FetchDirectories();
            } else {
                Toast.makeText(getContext(), "Uygulamanın düzgün çalışması için rehber okuma izni gerekli", Toast.LENGTH_SHORT).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(Manifest.permission.READ_CONTACTS);
        } else {
            FetchDirectories();
        }
        FetchGroups();
        return view;
    }

    private void FetchGroups() {
        String uid = mAuth.getCurrentUser().getUid();

        mStore.collection("/userdata/" + uid + "/groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            groupModelArrayList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                GroupModel groupModel = new GroupModel(documentSnapshot.getString("name"), documentSnapshot.getString("description"), documentSnapshot.getString("image"), (List<String>) documentSnapshot.get("numbers"), documentSnapshot.getId());
                groupModelArrayList.add(groupModel);
            }

            groupsRcyView.setAdapter(new GroupAdapter(groupModelArrayList, position -> {
                groupModel = groupModelArrayList.get(position);
                selectedGroupTxtView.setText("Seçili grup: " + groupModel.getName());
            }));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            groupsRcyView.setLayoutManager(linearLayoutManager);
        });
    }

    private void FetchDirectories() {
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        groupsDirectoryList.clear();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            DirectoryModel contactModel = new DirectoryModel(name, number, image);
            groupsDirectoryList.add(contactModel);
        }
    directoriesRcyView.setAdapter(new DirectoryAdapter(groupsDirectoryList, position -> {
        DirectoryModel directoryModel = groupsDirectoryList.get(position);
        if(groupModel != null) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Kişi Ekle")
                    .setMessage(directoryModel.getName() + " kişisini " + groupModel.getName() + " grubuna eklemek istiyor musunuz?")
                    .setPositiveButton("Evet", (dialog, which) -> {
                        mStore.collection("/userdata/" + mAuth.getCurrentUser().getUid() + "/groups").document(groupModel.getUid()).update( new HashMap<String, Object>(){{
                            put("numbers", FieldValue.arrayUnion(directoryModel.getNumber()));
                        }}).addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Kişi eklendi", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .setNegativeButton("Hayır", null)
                    .show();
        }
    }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        directoriesRcyView.setLayoutManager(linearLayoutManager);
    }
}