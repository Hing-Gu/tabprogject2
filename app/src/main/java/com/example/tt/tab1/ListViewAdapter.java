package com.example.tt.tab1;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tt.R;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    private List<HashMap<String, String>> Contacts;

    public ListViewAdapter(Context ctx){
        mContext = ctx;
        Contacts = getContacts();
    }

    @Override
    public int getCount() {
        return Contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return Contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.textview,parent,false);
        }
        TextView Title = convertView.findViewById(R.id.textView2);
        TextView Tel = convertView.findViewById(R.id.textView3);
        final Button calling_btn = convertView.findViewById(R.id.calling_btn);

        final HashMap<String, String> listViewItem = Contacts.get(position);
        Title.setText(listViewItem.get("name"));
        Tel.setText(listViewItem.get("tel"));

        calling_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+listViewItem.get("tel")));  //파싱? 해당 번호로 전화거는 기능을 가진 intent 생성
                mContext.startActivity(intent); //intent 실행
            }
        });

        return convertView;
    }

    public List<HashMap<String, String>> getContacts(){
        ArrayList<PhoneBook> data = new ArrayList<>();
        List<HashMap<String, String>> data2 = new ArrayList<>();


        // 1. Resolver 가져오기(데이터베이스 열어주기)
        // 전화번호부에 이미 만들어져 있는 ContentProvider 를 통해 데이터를 가져올 수 있음
        // 다른 앱에 데이터를 제공할 수 있도록 하고 싶으면 ContentProvider 를 설정
        // 핸드폰 기본 앱 들 중 데이터가 존재하는 앱들은 Content Provider 를 갖는다
        // ContentResolver 는 ContentProvider 를 가져오는 통신 수단
        ContentResolver resolver = mContext.getContentResolver();
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

                data.add(phoneBook);

            }
        }

        final Comparator comparator = new Comparator() {

            private final Collator collator = Collator.getInstance();
            @Override
            public int compare(Object  o1, Object o2) {
                return collator.compare(((PhoneBook)o1).getName(), ((PhoneBook)o2).getName());
            }
        };

        Collections.sort(data,comparator);


        //datas->hashmap->datas2으로 된 array
        for(int j = 0; j<data.size(); j++){
            HashMap<String, String> hashMap1 = new HashMap<>();
            hashMap1.put("name",data.get(j).getName());
            hashMap1.put("tel",data.get(j).getTel());

            data2.add(hashMap1);
        }
        cursor.close();

        return data2;
    }

    // 성명 포함 연락처 삭제 (구현 완료)
//    public void deleteContactFromNameLIKE(String display_name) {
    public void deleteContactFromNameLIKE(int id) {
        String display_name = Contacts.get(id).get("name");
        ContentResolver contactHelper = mContext.getContentResolver();
        System.out.println("Contact Name Search : " + display_name);
        Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.NUMBER}; // 검색
        String where = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like \'%" + display_name + "%\'";
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = null;
        try {
            if (display_name != null && !display_name.equals("")) {
                cursor = contactHelper.query(contactUri, projection, where, null, sortOrder);
                if (cursor.moveToFirst()) {
                    int count = 0;
                    do {
                        long rawContactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                        contactHelper.delete(ContactsContract.RawContacts.CONTENT_URI, ContactsContract.RawContacts.CONTACT_ID + "=" + rawContactId,null);
                        count++;
                    } while (cursor.moveToNext());
                    System.out.println("Delete Contact Number Count = " + count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
                notifyDataSetChanged();
            }
        }
    }
}
