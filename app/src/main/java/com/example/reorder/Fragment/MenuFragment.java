package com.example.reorder.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.R;
import com.example.reorder.Result.JoinResult;
import com.example.reorder.Api.CartSetApi;
import com.example.reorder.globalVariables.CurrentMenuInfo;
import com.example.reorder.globalVariables.CurrentSelectStore;
import com.example.reorder.globalVariables.CurrentStoreMenuInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tv_menu_name;
    private TextView tv_menu_price;
    private TextView tv_menu_count;
    private Button bt_down;
    private Button bt_up;
    private Button bt_ok;
    private Button bt_cancle;
    private TextView tv_total_price;
    private int count;
    private int price;
    private int total;
    private List<CartInfo> cartInfoList;
    private ImageView menu_image;
    String url= serverURL.getUrl();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        View view=inflater.inflate(R.layout.fragment_menu, container, false);
        tv_menu_name=view.findViewById(R.id.menu_name);
        tv_menu_name.setText(CurrentMenuInfo.getMenu_name());

        tv_menu_price=view.findViewById(R.id.menu_price);
        tv_menu_price.setText(Integer.toString(CurrentMenuInfo.getMenu_price()));
        tv_menu_count=view.findViewById(R.id.menu_count);

        tv_total_price=view.findViewById(R.id.tv_total_price);
        tv_total_price.setText(Integer.toString(CurrentMenuInfo.getMenu_price()));

        menu_image=view.findViewById(R.id.menu_image);
        switch(CurrentMenuInfo.getMenu_id()){
            case 915://야채피자
                menu_image.setImageResource(R.drawable.vegipizza);
                break;
            case 253://포테이토피자
                menu_image.setImageResource(R.drawable.potatopizza);
                break;
            case 312://불고기피자
                menu_image.setImageResource(R.drawable.boolgogipizza);
                break;
            case 692://야채피자
                menu_image.setImageResource(R.drawable.papperpizza);
                break;
            case 458://파인애플피자
                menu_image.setImageResource(R.drawable.pineapplepizza);
                break;
            case 526://치즈피자
                menu_image.setImageResource(R.drawable.cheesepizza);
                break;
            case 573://스윗골드피자
                menu_image.setImageResource(R.drawable.sweetgoldpizza);
                break;
            case 651://바이트골드피자
                menu_image.setImageResource(R.drawable.bitegoldpizza);
                break;
            case 254://단호박피자
                menu_image.setImageResource(R.drawable.pumpkinpizza);
                break;
            case 34://59박스1
                menu_image.setImageResource(R.drawable.box59_1);
                break;
            case 191://59박스2
                menu_image.setImageResource(R.drawable.box59_2);
                break;
            case 501://치킨텐더
                menu_image.setImageResource(R.drawable.chickentender);
                break;
            case 70://치즈 오븐 스파게티
                menu_image.setImageResource(R.drawable.cheesespagetti);
                break;
            case 303://윙봉
                menu_image.setImageResource(R.drawable.chickenbong);
                break;
                //59쌀피자

            case 12://떡볶이
                menu_image.setImageResource(R.drawable.ddokbokki);
                break;
            case 84://오뎅
                menu_image.setImageResource(R.drawable.umuk);
                break;
            case 684://순대
                menu_image.setImageResource(R.drawable.sundae);
                break;
            case 41://튀김
                menu_image.setImageResource(R.drawable.fried);
                break;
                //온수 떡볶이

            case 433://탕수육
                menu_image.setImageResource(R.drawable.tangsu);
                break;
            case 731://볶음밥
                menu_image.setImageResource(R.drawable.friedrice);
                break;
            case 316://짬뽕
                menu_image.setImageResource(R.drawable.jjambbong);
                break;
            case 463://짜장면
                menu_image.setImageResource(R.drawable.jjajang);
                break;
            case 713://IMF=이미지 없음
                menu_image.setImageResource(R.drawable.jjajang);
                break;
                //함지박

            case 340://순두부특선
                menu_image.setImageResource(R.drawable.softdubusoup);
                break;
            case 161://순두부
                menu_image.setImageResource(R.drawable.softdubusoup);
                break;
            case 717://김치찌개특선
                menu_image.setImageResource(R.drawable.gimchisoup);
                break;
            case 480://김치찌개
                menu_image.setImageResource(R.drawable.gimchisoup);
                break;
            case 993://된장찌개특선
                menu_image.setImageResource(R.drawable.misosoup);
                break;
            case 574://된장찌개
                menu_image.setImageResource(R.drawable.misosoup);
                break;
            case 95://두부전골
                menu_image.setImageResource(R.drawable.dubujungol);
                break;
                //맷돌

            case 743://딸기요거트쿱푸치노
                menu_image.setImageResource(R.drawable.ddalgiyogutchino);
                break;
            case 668://레몬요거트쿱푸치노
                menu_image.setImageResource(R.drawable.lemonyogutchino);
                break;
            case 880://블루베리요거트쿱푸치노
                menu_image.setImageResource(R.drawable.blueberryyogutchino);
                break;
            case 751://요거트쿱푸치노
                menu_image.setImageResource(R.drawable.yogutchino);
                break;
            case 764://녹차쿱푸치노
                menu_image.setImageResource(R.drawable.malchachino);
                break;
            case 372://망고쿱푸치노
                menu_image.setImageResource(R.drawable.mangochino);
                break;
            case 168://캐모마일티
                menu_image.setImageResource(R.drawable.kaemomilecha);
                break;
            case 616://페퍼민트티
                menu_image.setImageResource(R.drawable.pappermintcha);
                break;
            case 174://생강차
                menu_image.setImageResource(R.drawable.sanggangcha);
                break;
            case 16://대추차
                menu_image.setImageResource(R.drawable.daechucha);
                break;
            case 617://어니언베이글
                menu_image.setImageResource(R.drawable.lemonyogutchino);
                break;
            case 685://벨기에 와플
                menu_image.setImageResource(R.drawable.waffle);
                break;
            case 431://또띠아피자
                menu_image.setImageResource(R.drawable.ddotiapizza);
                break;
            case 90://허니브레드
                menu_image.setImageResource(R.drawable.honneybread);
                break;
                //자드

            case 862://뼈해장국
                menu_image.setImageResource(R.drawable.bonesoup);
                break;
            case 74://감자탕
                menu_image.setImageResource(R.drawable.gamjatang);
                break;
            case 465://감자탕
                menu_image.setImageResource(R.drawable.gamjatang);
                break;
            case 657://감자탕
                menu_image.setImageResource(R.drawable.gamjatang);
                break;
                //조마루

            case 227://치킨마요
                menu_image.setImageResource(R.drawable.chickenmayo);
                break;
            case 606://참치마요
                menu_image.setImageResource(R.drawable.chamchimayo);
                break;
            case 409://콤보
                menu_image.setImageResource(R.drawable.combo);
                break;
            case 64://소불
                menu_image.setImageResource(R.drawable.sobulgogi);
                break;
            case 623://칠리콤보
                menu_image.setImageResource(R.drawable.chilicombo);
                break;
            case 712://돈불새우
                menu_image.setImageResource(R.drawable.donbulsaeu);
                break;
            case 244://함박
                menu_image.setImageResource(R.drawable.hambag);
                break;
            case 984://왕돈가스
                menu_image.setImageResource(R.drawable.bigdonkas);
                break;
            case 728://토마토더블정식
                menu_image.setImageResource(R.drawable.tomatodouble);
                break;
                //토마토

            case 235://아메리카노
                menu_image.setImageResource(R.drawable.americano_ediya);
                break;
            case 368://카페라떼
                menu_image.setImageResource(R.drawable.cafelatte_ediya);
                break;
            case 770://콜드브루 아메리카노
                menu_image.setImageResource(R.drawable.coldbruamericano);
                break;
            case 294://콜드브루 라떼
                menu_image.setImageResource(R.drawable.coldblulatte);
                break;
            case 883://카라멜 마끼야또
                menu_image.setImageResource(R.drawable.caramelmakiyaddo);
                break;
            case 925://바닐라 라떼
                menu_image.setImageResource(R.drawable.banilalatte);
                break;
            case 983://민트 모카
                menu_image.setImageResource(R.drawable.mintmoca);
                break;
            case 125://화이트 초콜릿 모카
                menu_image.setImageResource(R.drawable.whitechocomoca);
                break;
            case 708://녹차라떼
                menu_image.setImageResource(R.drawable.malchlatte);
                break;
            case 575://커피 플렛치노
                menu_image.setImageResource(R.drawable.coffechino);
                break;
            case 123://망고 플렛치노
                menu_image.setImageResource(R.drawable.manggochino_ediya);
                break;
            case 432://꿀복숭아 플렛치노
                menu_image.setImageResource(R.drawable.pichchino);
                break;
            case 299://민트 초코칩 플렛치노
                menu_image.setImageResource(R.drawable.mintchocochino);
                break;
                //이디야

            case 667://베이컨 치즈 머핀
                menu_image.setImageResource(R.drawable.bakencheesmufin);
                break;
            case 37://베이컨 치즈 베이글
                menu_image.setImageResource(R.drawable.bakencheesebaygle);
                break;
            case 884://햄치즈 토스트
                menu_image.setImageResource(R.drawable.mintchocochino);
                break;
            case 44://스테이크 햄 vip
                menu_image.setImageResource(R.drawable.steakhamvip);
                break;
            case 514://아메리카노
                menu_image.setImageResource(R.drawable.americano);
                break;
            case 304://아이스티
                menu_image.setImageResource(R.drawable.icetea);
                break;
            case 430://딸기 바나나 쥬스
                menu_image.setImageResource(R.drawable.ddalbajuce);
                break;
            case 662://바나나 쥬스
                menu_image.setImageResource(R.drawable.bananajuce);
                break;
            case 889://딸기 쥬스
                menu_image.setImageResource(R.drawable.ddalgijuce);
                break;
            case 805://키위쥬스
                menu_image.setImageResource(R.drawable.kiwijuce);
                break;
            case 811://애플 망고 쥬스
                menu_image.setImageResource(R.drawable.applemanggojuce);
                break;
            case 33://밀크 쉐이크
                menu_image.setImageResource(R.drawable.milkshake);
                break;
            case 120://딸기 쉐이크
                menu_image.setImageResource(R.drawable.ddalgishake);
                break;
            case 885://자몽에이드
                menu_image.setImageResource(R.drawable.jamongade);
                break;
            case 600://카라멜 마끼야또
                menu_image.setImageResource(R.drawable.caramel_isac);
                break;
            case 330://카페라떼
                menu_image.setImageResource(R.drawable.cafelatte);
                break;
            case 589://카페모카
                menu_image.setImageResource(R.drawable.cafemoca);
                break;
            case 852://얼그레이티
                menu_image.setImageResource(R.drawable.urgraytea);
                break;
            case 114://레몬에이드
                menu_image.setImageResource(R.drawable.lemonade);
                break;
            //이삭


        }

        bt_up=view.findViewById(R.id.bt_up);
        bt_cancle=view.findViewById(R.id.bt_cancle);
        bt_down=view.findViewById(R.id.bt_down);
        bt_ok=view.findViewById(R.id.bt_go_cart);
        count=1;
        total=0;
        //전달 받은 price를 밑에 price에 넣어주고 수량 증가,감소시 반영은 버튼 안에 구현
        price= CurrentMenuInfo.getMenu_price();
        tv_menu_count.setText(String.valueOf(count));

        bt_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<10){
                    count++;
                    tv_menu_count.setText(String.valueOf(count));
                    total=count*price;
                    tv_total_price.setText(String.valueOf(total));
                    CurrentMenuInfo.setMenu_count(count);
                }
                else
                    Toast.makeText(getContext(),"수량은 10개가 최대입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        bt_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>1){
                    count--;
                    tv_menu_count.setText(String.valueOf(count));
                    total=count*price;
                    tv_total_price.setText(String.valueOf(total));
                    CurrentMenuInfo.setMenu_count(count);
                }
                else
                    Toast.makeText(getContext(),"수량은 최소 1개입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationnActivity)NavigationnActivity.mContext).onBackPressed();
            }
        });


        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = CurrentUserInfo.getUser().getUserInfo().getId();
                int menu_id=CurrentMenuInfo.getMenu_id();
                final String menu_name=CurrentMenuInfo.getMenu_name();
                int menu_price=CurrentMenuInfo.getMenu_price();
                int menu_count=CurrentMenuInfo.getMenu_count();
                int store_id= CurrentSelectStore.getSt_id();
                String store_name=CurrentSelectStore.getSt_name();

                try {
                    HashMap<String, String> input = new HashMap<>();
                    input.put("id", String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()));
                    input.put("menu_id", String.valueOf(menu_id));
                    input.put("menu_name", menu_name);
                    input.put("menu_price", String.valueOf(menu_price));
                    input.put("menu_count", String.valueOf(menu_count));
                    input.put("store_id",String.valueOf(store_id));
                    input.put("store_name",store_name);

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create()).build();

                    CartSetApi cartSetApi = retrofit.create(CartSetApi.class);

                    cartSetApi.setUserCartInfo(input).enqueue(new Callback<JoinResult>() {
                        @Override
                        public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                            if (response.isSuccessful()) {
                                JoinResult map = response.body();
                                if (map!=null) {
                                    switch (map.getResult()) {
                                        case 1:
                                            Toast.makeText(getContext(),"장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show();
                                            ((NavigationnActivity)NavigationnActivity.mContext).onBackPressed();
                                            break;
                                        case 0:
                                            Toast.makeText(getContext(),"실패", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 2://다른 매장의 제품을 장바구니에 넣으려고 할 시.
                                            Toast.makeText(getContext(),
                                                    "장바구니에는 1개의 매장의 제품들만 담을 수 있습니다."
                                                    , Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<JoinResult> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
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