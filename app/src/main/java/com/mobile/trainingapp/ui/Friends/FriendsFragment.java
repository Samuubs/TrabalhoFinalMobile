package com.mobile.trainingapp.ui.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import com.mobile.trainingapp.MainActivity;
import com.mobile.trainingapp.Mock;
import com.mobile.trainingapp.NewFriendActivity;
import com.mobile.trainingapp.R;
import com.mobile.trainingapp.adapter.AdapterUser;
import com.mobile.trainingapp.model.User;
import com.mobile.trainingapp.ui.Inbox.InboxFragment;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private FriendsViewModel friendsViewModel;
    private ListView listView;
    private Button newFriendButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendsViewModel =
                ViewModelProviders.of(this).get(FriendsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_friends, container, false);

        listView = (ListView) root.findViewById(R.id.idListViewFriends);
        AdapterUser adapter = new AdapterUser(root.getContext(), Mock.getInstance().mockUsers());
        listView.setAdapter(adapter);

        newFriendButton = (Button) root.findViewById(R.id.idButtonNovoAmigo);
        newFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), NewFriendActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                InboxFragment fragment = new InboxFragment();

                // Passando valor para o proximo fragment
                Bundle arguments = new Bundle();
                arguments.putString( "chave" , "valor");
                fragment.setArguments(arguments);

                fragmentTransaction.addToBackStack("xyz");
                fragmentTransaction.hide(FriendsFragment.this);
                fragmentTransaction.add(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        return root;
    }
}