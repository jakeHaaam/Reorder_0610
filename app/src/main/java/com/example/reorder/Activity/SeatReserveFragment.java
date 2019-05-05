package com.example.reorder.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.R;

import java.sql.Array;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeatReserveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeatReserveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeatReserveFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button bt_order_ok;
    private Button bt_order_cancle;
    private GridLayout grid;
    private Context context;
    int checked_count=0,max_count=10;
    private static int[] seat_state;
    public static boolean[] seat_checked;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SeatReserveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeatReserveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeatReserveFragment newInstance(String param1, String param2) {
        SeatReserveFragment fragment = new SeatReserveFragment();
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
        View view=inflater.inflate(R.layout.fragment_seat_reserve, container, false);
        bt_order_ok=view.findViewById(R.id.bt_order_ok);
        bt_order_cancle=view.findViewById(R.id.bt_order_cancle);
        grid= view.findViewById(R.id.grid);
        context=grid.getContext();
        seat_state=new int[100];
        seat_checked=new boolean[100];//seat갯수 만큼 불린형 생성
        Arrays.fill(seat_checked,false);//불린형 false로 초기화

        //int seatcount;받아온 seat의 갯수를 넣어주고 밑에 for문에 100대신 입력
            //grid.setColumnCount();<-여기에 받아오는 행렬의 행값 입력 해주면 해당 행까지만 나오고 자동으로 다음 열로 이동해서 생성 됨
            for(int j=0;j<100;j++)//버튼 동적 생성 부분
            {
                seat_state[j]=0;//db에서 받아온 seat_state를 seat_id에 맞춰 집어넣어줘야 함
                //if()
                final Button bt = new Button(context);
                bt.setId(j + 1);//id=1,2,3,4 이런식으로 할당 받아짐
                bt.setText(String.valueOf(j + 1));
                if(seat_state[j]==0){ //"0"은 사용 가능한 상태
                    bt.setBackgroundColor(Color.WHITE);
                    bt.setOnClickListener(this);}
                else if(seat_state[j]==1){//"1"은 예약중인 상태-예약 불가
                    bt.setBackgroundColor(Color.YELLOW);
                bt.setClickable(false);}
                else if(seat_state[j]==2){//"2"는 현재 사용중인 상태-예약 불가
                    bt.setBackgroundColor(Color.GREEN);
                    bt.setClickable(false);}
                grid.addView(bt);
            }

        bt_order_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터를 order페이지에 전송 필요=어떤?
                ((NavigationnActivity)NavigationnActivity.mContext).replaceFragment(5);
            }
        });

        bt_order_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationnActivity)NavigationnActivity.mContext).replaceFragment(5);
            }
        });

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

    @Override
    public void onClick(View v) {
        int id=v.getId();

        //if(( ==Color.BLUE)
            //v.setBackgroundColor(Color.WHITE);
          //  else
        if(checked_count<max_count) {

            if (seat_checked[id] != true) {
                seat_checked[id]=true;
                v.setBackgroundColor(Color.BLUE);
                checked_count++;
            }
            else{
                seat_checked[id]=false;
                checked_count--;
                v.setBackgroundColor(Color.WHITE);
            }
        }
        else {
            if (seat_checked[id] != true)
                Toast.makeText(getContext(), "좌석 선택은 10개가 최대 입니다.", Toast.LENGTH_SHORT).show();
            else{
                checked_count--;
                seat_checked[id]=false;
                v.setBackgroundColor(Color.WHITE);
            }
        }
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
