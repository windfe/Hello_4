package comt.example.administrator.hello_4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String>dictionary;
    private String word;//����ʵ����������ʹ�������ã��������ڷ�����
    private  ArrayList<String> definitions;
    private MainActivity myActivity;
    private  ArrayAdapter<String> myAdatper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myActivity=this;//�������ı���ָ����

        this.definitions=new ArrayList<>();
        //���Ӳ�����ListView���                                                //���Լ���
        myAdatper=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,definitions);//���������������֣�����


        lookup();
        pick5Definitions();//��lookup();��˷���������dictionaryΪ��

        //TextView����,��ʾkey
        TextView wordText=findViewById(R.id.wordTextView);
        wordText.setText(word);


        final ListView defnListView=findViewById(R.id.definitions_ListView);
        defnListView.setAdapter(myAdatper);//����

        //�����ڲ���ʵ����Ӧ�¼�
        defnListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {//position�û����������һ��
                String chosedDefnText=defnListView.getItemAtPosition(position).toString();//�ѵ�����Ǹ��¼�values���
                String correctDefn=dictionary.get(word);
                if (correctDefn.equals(chosedDefnText)){
                    Toast.makeText(myActivity,"correct",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(myActivity,"error",Toast.LENGTH_SHORT).show();

                }
                pick5Definitions();//�������ݡ����¡�
                TextView wordText=findViewById(R.id.wordTextView);
                wordText.setText(word);

            }
        });

    }


    //���ȡ��������롱values��
    private void pick5Definitions(){
    //��Ҫ���word����key
        ArrayList<String> choseWrods=new ArrayList<>(this.dictionary.keySet());//���ε�����,����key���������飬���������
        Collections.shuffle(choseWrods);//�����������ݴ���,��ÿ����ʾ�Ķ���ͬ

    //��ʼ����ʾ�ĵ���key
        this.word=choseWrods.get(0);//��ʾ����ĵ�һ����

        definitions.clear();//ÿ���������

     //��Ҫ���values����̬��ֵ
       //  this.definitions=new ArrayList<>();//this����ʵ���ھֲ�����  //���Է���ǰ�棬ÿ�����У���ʼ��һ��
        for (int i = 0; i <5 ; i++) {
            String defn=this.dictionary.get(choseWrods.get(i));//k��ȡey�����get(i)ֵ��Ϊkey,�Ӷ����values��ֵ
            definitions.add(defn);//��values�����������μ���ֵ
        }
        Collections.shuffle(definitions);//���ݴ��ң�ȷ��listview�ĵ�һ����textview������ͬ

        myAdatper.notifyDataSetChanged();//֪ͨListView����

/*    ���Ի�һ��λ�ã�������������
        //TextView����,��ʾkey
        TextView wordText=findViewById(R.id.wordTextView);
        wordText.setText(word);

        //���Ӳ�����ListView���                                                //���Լ���
        ArrayAdapter<String> myAdatper=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,definitions);//���������������֣�����
        ListView defnListView=findViewById(R.id.definitions_ListView);
        defnListView.setAdapter(myAdatper);//����

        */



    }

    private void lookup(){
        if(dictionary==null){
            this.dictionary=new HashMap<>();
        }
        Scanner scan =new Scanner(getResources().openRawResource(R.raw.dictionary));
        while(scan.hasNext()){
            String line=scan.nextLine();
            String[] piece=line.split(":");
           // if (str.equalsIgnoreCase(piece[0])){
                this.dictionary.put(piece[0],piece[1]);

            //}

        }

    }



}
