package com.example.reorder.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.Activity.NavigationnActivity;
import com.example.reorder.R;
import com.example.reorder.Result.JoinResult;
import com.example.reorder.Api.CartSetApi;
import com.example.reorder.globalVariables.CurrentMenuInfo;
import com.example.reorder.globalVariables.CurrentSelectStore;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.CartInfo;

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
            case 2827://야채피자
                menu_image.setImageResource(R.drawable.vegipizza);
                break;
            case 824://콤비네이션피자
                menu_image.setImageResource(R.drawable.combipizza);
                break;
            case 9392://포테이토피자
                menu_image.setImageResource(R.drawable.potatopizza);
                break;
            case 3863://불고기피자
                menu_image.setImageResource(R.drawable.bulgogipizza);
                break;
            case 547://패퍼로니피자
                menu_image.setImageResource(R.drawable.paperpizza);
                break;
            case 4648://파인애플피자
                menu_image.setImageResource(R.drawable.pineapplepizza);
                break;
            case 799://치즈피자
                menu_image.setImageResource(R.drawable.cheesepizza);
                break;
            case 5717://스윗골드피자
                menu_image.setImageResource(R.drawable.sweetgoldpizza);
                break;
            case 7375://바이트골드피자
                menu_image.setImageResource(R.drawable.bitegoldpizza);
                break;
            case 7102://단호박피자
                menu_image.setImageResource(R.drawable.pumpkinpizza);
                break;
            case 7132://59박스1
                menu_image.setImageResource(R.drawable.box59_1);
                break;
            case 7798://59박스2
                menu_image.setImageResource(R.drawable.box59_2);
                break;
            case 5773://치킨텐더
                menu_image.setImageResource(R.drawable.chickentender);
                break;
            case 9275://치즈 오븐 스파게티
                menu_image.setImageResource(R.drawable.cheesespagetti);
                break;
            case 4958://윙봉
                menu_image.setImageResource(R.drawable.chickenbong);
                break;
                //59쌀피자

            case 1718://떡볶이
                menu_image.setImageResource(R.drawable.duckbbockgi);
                break;
            case 731://오뎅
                menu_image.setImageResource(R.drawable.odang);
                break;
            case 9925://순대
                menu_image.setImageResource(R.drawable.sundae);
                break;
            case 8858://튀김
                menu_image.setImageResource(R.drawable.fried);
                break;
                //온수 떡볶이

            case 6342://탕수육
                menu_image.setImageResource(R.drawable.tangsu);
                break;
            case 9863://볶음밥
                menu_image.setImageResource(R.drawable.friedrice);
                break;
            case 7750://짬뽕
                menu_image.setImageResource(R.drawable.jjambbong);
                break;
            case 323://짜장면
                menu_image.setImageResource(R.drawable.jjajang);
                break;
            case 1218://군만두
                menu_image.setImageResource(R.drawable.gunmandu);
                break;
                //함지박

            case 7690://순두부
                menu_image.setImageResource(R.drawable.softdubusoup);
                break;
            case 5680://김치찌개
                menu_image.setImageResource(R.drawable.gimchisoup);
                break;
            case 139://된장찌개
                menu_image.setImageResource(R.drawable.misosoup);
                break;
            case 8507://두부전골
                menu_image.setImageResource(R.drawable.dubujungol);
                break;
                //맷돌

            case 9780://딸기요거트쿱푸치노
                menu_image.setImageResource(R.drawable.ddalgiyogutchino);
                break;
            case 5077://레몬요거트쿱푸치노
                menu_image.setImageResource(R.drawable.lemonyogutchino);
                break;
            case 8941://블루베리요거트쿱푸치노
                menu_image.setImageResource(R.drawable.blueberryyogutchino);
                break;
            case 6481://요거트쿱푸치노
                menu_image.setImageResource(R.drawable.yogutchino);
                break;
            case 4086://녹차쿱푸치노
                menu_image.setImageResource(R.drawable.malchachino);
                break;
            case 1035://망고쿱푸치노
                menu_image.setImageResource(R.drawable.mangochino);
                break;
            case 2122://캐모마일티
                menu_image.setImageResource(R.drawable.kaemomilecha);
                break;
            case 7807://페퍼민트티
                menu_image.setImageResource(R.drawable.pappermintcha);
                break;
            case 9775://생강차
                menu_image.setImageResource(R.drawable.sanggangcha);
                break;
            case 8659://대추차
                menu_image.setImageResource(R.drawable.daechucha);
                break;
            case 5218://어니언베이글
                menu_image.setImageResource(R.drawable.unionbaygle);
                break;
            case 1944://벨기에 와플
                menu_image.setImageResource(R.drawable.waffle);
                break;
            case 737://또띠아피자
                menu_image.setImageResource(R.drawable.ddotiapizza);
                break;
            case 1080://허니브레드
                menu_image.setImageResource(R.drawable.honneybread);
                break;
                //자드

            case 9182://뼈해장국
                menu_image.setImageResource(R.drawable.bonesoup);
                break;
            case 5813://감자탕
                menu_image.setImageResource(R.drawable.gamjatang);
                break;
                //조마루

            case 8628://치킨마요
                menu_image.setImageResource(R.drawable.chickenmayo);
                break;
            case 6785://참치마요
                menu_image.setImageResource(R.drawable.chamchimayo);
                break;
            case 3071://콤보
                menu_image.setImageResource(R.drawable.combo);
                break;
            case 3556://소불
                menu_image.setImageResource(R.drawable.sobulgogi);
                break;
            case 1737://칠리콤보
                menu_image.setImageResource(R.drawable.chilicombo);
                break;
            case 5305://돈불새우>png?
                menu_image.setImageResource(R.drawable.donbulsaeu);
                break;
            case 6238://함박
                menu_image.setImageResource(R.drawable.hambag);
                break;
            case 92://왕돈가스
                menu_image.setImageResource(R.drawable.bigdonkas);
                break;
            case 1222://토마토더블정식
                menu_image.setImageResource(R.drawable.tomatodouble);
                break;
                //토마토

            case 179://아메리카노
                menu_image.setImageResource(R.drawable.americano_ediya);
                break;
            case 7570://카페라떼
                menu_image.setImageResource(R.drawable.cafelatte_ediya);
                break;
            case 3079://콜드브루 아메리카노
                menu_image.setImageResource(R.drawable.coldbruamericano);
                break;
            case 8938://콜드브루 라떼
                menu_image.setImageResource(R.drawable.coldbrulatte);
                break;
            case 5498://카라멜 마끼야또
                menu_image.setImageResource(R.drawable.caramelmakiyaddo);
                break;
            case 5532://바닐라 라떼
                menu_image.setImageResource(R.drawable.banilalatte);
                break;
            case 3419://민트 모카
                menu_image.setImageResource(R.drawable.mintmoca);
                break;
            case 4819://화이트 초콜릿 모카
                menu_image.setImageResource(R.drawable.whitechocomoca);
                break;
            case 2044://녹차라떼
                menu_image.setImageResource(R.drawable.malchlatte);
                break;
            case 7973://커피 플렛치노
                menu_image.setImageResource(R.drawable.coffechino);
                break;
            case 4988://망고 플렛치노
                menu_image.setImageResource(R.drawable.manggochino_ediya);
                break;
            case 1183://꿀복숭아 플렛치노
                menu_image.setImageResource(R.drawable.pichchino);
                break;
            case 2239://민트 초코칩 플렛치노
                menu_image.setImageResource(R.drawable.mintchocochino);
                break;
                //이디야

            case 6708://베이컨 치즈 머핀
                menu_image.setImageResource(R.drawable.bakencheesmufin);
                break;
            case 7046://베이컨 치즈 베이글
                menu_image.setImageResource(R.drawable.bakencheesebaygle);
                break;
            case 4453://햄치즈 토스트
                menu_image.setImageResource(R.drawable.toast);
                break;
            case 4765://스테이크 햄 vip
                menu_image.setImageResource(R.drawable.steakhamvip);
                break;
            case 9952://아메리카노
                menu_image.setImageResource(R.drawable.americano);
                break;
            case 171://아이스티
                menu_image.setImageResource(R.drawable.icetea);
                break;
            case 5860://딸기 바나나 쥬스
                menu_image.setImageResource(R.drawable.ddalbajuce);
                break;
            case 2019://바나나 쥬스
                menu_image.setImageResource(R.drawable.bananajuce);
                break;
            case 7000://딸기 쥬스
                menu_image.setImageResource(R.drawable.ddalgijuce);
                break;
            case 9825://키위쥬스
                menu_image.setImageResource(R.drawable.kiwijuce);
                break;
            case 6967://애플 망고 쥬스
                menu_image.setImageResource(R.drawable.applemangojuce);
                break;
            case 5499://밀크 쉐이크
                menu_image.setImageResource(R.drawable.milkshake);
                break;
            case 2819://딸기 쉐이크
                menu_image.setImageResource(R.drawable.ddalgishake);
                break;
            case 5704://자몽에이드
                menu_image.setImageResource(R.drawable.jamongade);
                break;
            case 3888://카라멜 마끼야또
                menu_image.setImageResource(R.drawable.caramel_isac);
                break;
            case 5740://카페라떼
                menu_image.setImageResource(R.drawable.cafelatte);
                break;
            case 4336://카페모카
                menu_image.setImageResource(R.drawable.cafemoca);
                break;
            case 3208://얼그레이티
                menu_image.setImageResource(R.drawable.urgraytea);
                break;
            case 4656://레몬에이드
                menu_image.setImageResource(R.drawable.lemonade);
                break;
            //이삭

            default:
                menu_image.setImageResource(R.drawable.yeonsusi);
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