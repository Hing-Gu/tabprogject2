package com.example.tt.tab1;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.ContentResolver;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.ListView;

import com.example.tt.R;

public class tab1 extends Fragment{


    public tab1() {
        // Required empty public constructor
    }

    public class PhoneBook<phoneBooks> {

        private String id;
        private String name;
        private String  tel;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
        public String  getTel() {
            return tel;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_tab1, container, false);
        List<PhoneBook> datas = new ArrayList<>();
        List<HashMap<String, String>> datas2 = new ArrayList<>();


        // 1. Resolver 가져오기(데이터베이스 열어주기)
        // 전화번호부에 이미 만들어져 있는 ContentProvider 를 통해 데이터를 가져올 수 있음
        // 다른 앱에 데이터를 제공할 수 있도록 하고 싶으면 ContentProvider 를 설정
        // 핸드폰 기본 앱 들 중 데이터가 존재하는 앱들은 Content Provider 를 갖는다
        // ContentResolver 는 ContentProvider 를 가져오는 통신 수단
        ContentResolver resolver = getActivity().getContentResolver();
        // 2. 전화번호가 저장되어 있는 테이블 주소값(Uri)을 가져오기
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        // 3. 테이블에 정의된 칼럼 가져오기
        // ContactsContract.CommonDataKinds.Phone 이 경로에 상수로 칼럼이 정의
        String[] projection = { ContactsContract.CommonDataKinds.Phone.CONTACT_ID // 인덱스 값, 중복될 수 있음 -- 한 사람 번호가 여러개인 경우
                ,  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                ,  ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 4. ContentResolver로 쿼리를 날림 -> resolver 가 provider 에게 쿼리하겠다고 요청
        Cursor cursor = resolver.query(phoneUri, projection, null, null, null);

        // 4. 커서로 리턴된다. 반복문을 돌면서 cursor 에 담긴 데이터를 하나씩 추출
        if(cursor != null){
            while(cursor.moveToNext()){
                // 4.1 이름으로 인덱스를 찾아준다
                int idIndex = cursor.getColumnIndex(projection[0]); // 이름을 넣어주면 그 칼럼을 가져와준다.
                int nameIndex = cursor.getColumnIndex(projection[1]);
                int numberIndex = cursor.getColumnIndex(projection[2]);
                // 4.2 해당 index 를 사용해서 실제 값을 가져온다.
                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);

                PhoneBook phoneBook = new PhoneBook();
                phoneBook.setId(id);
                phoneBook.setName(name);
                phoneBook.setTel(number);

                datas.add(phoneBook);

            }
        }

        final Comparator comparator = new Comparator() {

            private final Collator collator = Collator.getInstance();
            @Override
            public int compare(Object  o1, Object o2) {
                return collator.compare(((PhoneBook)o1).getName(), ((PhoneBook)o2).getName());
            }
        };

        Collections.sort(datas,comparator);


        //datas->hashmap->datas2으로 된 array
        for(int j = 0; j<datas.size(); j++){
            HashMap<String, String> hashMap1 = new HashMap<String, String>();
            hashMap1.put("name",datas.get(j).getName());
            hashMap1.put("Tel",datas.get(j).getTel());

            datas2.add(hashMap1);
        }
        cursor.close();


        ////////////////////////////////////////////////200101/////////////////////////////////////////// <permission 권한 줬음>
        //https://itlove.tistory.com/406 (참고)
//        Button button = null; //얘를 list view에 하나씩 넣어야 함..........
//        button = view.findViewById(R.id.btn); //button id를 넣어야 하는데 listview에 버튼 넣는 법을 모르겠음  (https://gusfree.tistory.com/740)
//        button.setOnClickListener(new View.OnClickListener() { //버튼이 클릭 됐을 때
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:01034778221"));  //파싱? 해당 번호로 전화거는 기능을 가진 intent 생성
//                startActivity(intent); //intent 실행
//            }
//        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        ListView MyListView = view.findViewById(R.id.list);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), datas2, R.layout.textview, new String[]{"name", "Tel"}, new int[]{R.id.textView2, R.id.textView3});
        MyListView.setAdapter(adapter);
        return view;
    }

}