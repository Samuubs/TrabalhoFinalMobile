package com.mobile.trainingapp.ui.Inbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mobile.trainingapp.Mock;
import com.mobile.trainingapp.R;
import com.mobile.trainingapp.adapter.AdapterMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InboxFragment extends Fragment {

    private InboxViewModel inboxViewModel;
    private ListView listViewMessages;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inboxViewModel =
                ViewModelProviders.of(this).get(InboxViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inbox, container, false);


        listViewMessages = root.findViewById(R.id.idListViewInbox);
        AdapterMessage adapter = new AdapterMessage(root.getContext(), Mock.getInstance().getMessages());
        listViewMessages.setAdapter(adapter);
        return root;
    }
}