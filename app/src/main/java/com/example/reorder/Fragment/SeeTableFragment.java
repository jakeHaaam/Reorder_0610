package com.example.reorder.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.example.reorder.R;
import com.example.reorder.globalVariables.CurrentSeatInfo;
import com.example.reorder.globalVariables.CurrentSeeTableInfo;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeeTableFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeeTableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridLayout grid_see_table;
    private Context context;
    int checked_count,max_count;
    private static int[] seat_state;
    public static boolean[] seat_checked;

    private OnFragmentInteractionListener mListener;

    public SeeTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeeTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeeTableFragment newInstance(String param1, String param2) {
        SeeTableFragment fragment = new SeeTableFragment();
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
        View view=inflater.inflate(R.layout.fragment_see_table,container,false);
        grid_see_table=view.findViewById(R.id.grid_see_table);

        grid_see_table= view.findViewById(R.id.grid);
        context=grid_see_table.getContext();
        checked_count=0;
        max_count=1;

        int st_num= CurrentSeeTableInfo.getStoreSeat().getStoreSeatInfo().getSeat_num();
        int st_row=CurrentSeeTableInfo.getStoreSeat().getStoreSeatInfo().getSeat_row();
        seat_state=new int[st_num];
        seat_checked=new boolean[st_num+1];//seat갯수 만큼 불린형 생성
        Arrays.fill(seat_checked,false);//불린형 false로 초기화
        grid_see_table.setColumnCount(st_row);//<-여기에 받아오는 행렬의 행값 입력 해주면 해당 행까지만 나오고 자동으로 다음 열로 이동해서 생성 됨
        for(int j=0;j<st_num;j++)//버튼 동적 생성 부분
        {
            seat_state[j]= CurrentSeatInfo.getSeat().getSeatInfoList().get(j).getSeat_statement();//db에서 받아온 seat_state를 seat_id에 맞춰 집어넣어줘야 함
            final Button bt = new Button(context);
            bt.setId(j + 1);//id=1,2,3,4 이런식으로 할당 받아짐
            bt.setText(String.valueOf(j + 1));
            if(seat_state[j]==0){ //"0"은 사용 가능한 상태
                bt.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.seat_able_button));
            }
            else if(seat_state[j]==1){//"1"은 예약중인 상태-예약 불가
                bt.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.seat_reserved_button));
            }
            else if(seat_state[j]==2){//"2"는 현재 사용중인 상태-예약 불가
                bt.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.seat_using_button));
            }
            grid_see_table.addView(bt);
        }

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
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
