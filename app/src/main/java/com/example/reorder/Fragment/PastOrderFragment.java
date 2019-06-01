package com.example.reorder.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reorder.Adapter.LiveOrderStateAdapter;
import com.example.reorder.Adapter.PastOrderAdapter;
import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentLiveOrderStateInfo;
import com.example.reorder.globalVariables.CurrentPastOrderInfo;
import com.example.reorder.info.LiveOrderStateInfo;
import com.example.reorder.info.PastOrderInfo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PastOrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PastOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastOrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<PastOrderInfo> pastOrderInfos;
    private RecyclerView lv_past;
    private RecyclerView.Adapter past_order_state_adapter;

    private OnFragmentInteractionListener mListener;

    public PastOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PastOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PastOrderFragment newInstance(String param1, String param2) {
        PastOrderFragment fragment = new PastOrderFragment();
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
        View view=inflater.inflate(R.layout.fragment_past_order, container, false);

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        lv_past=view.findViewById(R.id.lv_past);
        lv_past.setLayoutManager(manager);
        pastOrderInfos= CurrentPastOrderInfo.getPastOrder().getPastOrderInfoList();
        past_order_state_adapter=new PastOrderAdapter(pastOrderInfos,inflater.getContext());
        lv_past.setAdapter(past_order_state_adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //      + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}