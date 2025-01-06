package id.ac.binus.caffind.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import id.ac.binus.caffind.R;
import id.ac.binus.caffind.activities.Login;
import id.ac.binus.caffind.activities.Register;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUnauthenticated#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUnauthenticated extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final String message;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentUnauthenticated(String message) {
        this.message = message;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUnauthenticated.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUnauthenticated newInstance(String param1, String param2, String message) {
        FragmentUnauthenticated fragment = new FragmentUnauthenticated(message);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_unauthenticated, container, false);

        TextView pageMessage = view.findViewById(R.id.txtMessage);
        pageMessage.setText(message);

        Button registerButton = view.findViewById(R.id.btnRegister);
        Button loginButton = view.findViewById(R.id.btnLogin);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Register.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        return view;
    }
}