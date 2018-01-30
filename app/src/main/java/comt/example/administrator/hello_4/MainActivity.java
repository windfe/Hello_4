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
    private String word;//定义实例化变量，使变量调用，不局限于方法内
    private  ArrayList<String> definitions;
    private MainActivity myActivity;
    private  ArrayAdapter<String> myAdatper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myActivity=this;//主方法的变量指向本身

        this.definitions=new ArrayList<>();
        //连接并关联ListView组件                                                //得自己敲
        myAdatper=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,definitions);//关联：方法，布局，数组


        lookup();
        pick5Definitions();//先lookup();后此方法，否则dictionary为空

        //TextView关联,显示key
        TextView wordText=findViewById(R.id.wordTextView);
        wordText.setText(word);


        final ListView defnListView=findViewById(R.id.definitions_ListView);
        defnListView.setAdapter(myAdatper);//关联

        //声明内部类实现响应事件
        defnListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {//position用户点击的是哪一个
                String chosedDefnText=defnListView.getItemAtPosition(position).toString();//把点击的那个事件values输出
                String correctDefn=dictionary.get(word);
                if (correctDefn.equals(chosedDefnText)){
                    Toast.makeText(myActivity,"correct",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(myActivity,"error",Toast.LENGTH_SHORT).show();

                }
                pick5Definitions();//用于数据“更新”
                TextView wordText=findViewById(R.id.wordTextView);
                wordText.setText(word);

            }
        });

    }


    //随机取五个“翻译”values量
    private void pick5Definitions(){
    //需要五个word，即key
        ArrayList<String> choseWrods=new ArrayList<>(this.dictionary.keySet());//带参的数组,所有key都传入数组，不是有序的
        Collections.shuffle(choseWrods);//把数组中数据打乱,让每次显示的都不同

    //初始化显示的单词key
        this.word=choseWrods.get(0);//显示数组的第一个数

        definitions.clear();//每次运行清空

     //需要五个values，动态赋值
       //  this.definitions=new ArrayList<>();//this区分实例于局部变量  //可以放在前面，每次运行，初始化一次
        for (int i = 0; i <5 ; i++) {
            String defn=this.dictionary.get(choseWrods.get(i));//k获取ey数组的get(i)值作为key,从而获得values的值
            definitions.add(defn);//往values空数组中依次加入值
        }
        Collections.shuffle(definitions);//数据打乱，确保listview的第一个和textview不是相同

        myAdatper.notifyDataSetChanged();//通知ListView更新

/*    可以换一个位置，放在主方法里
        //TextView关联,显示key
        TextView wordText=findViewById(R.id.wordTextView);
        wordText.setText(word);

        //连接并关联ListView组件                                                //得自己敲
        ArrayAdapter<String> myAdatper=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,definitions);//关联：方法，布局，数组
        ListView defnListView=findViewById(R.id.definitions_ListView);
        defnListView.setAdapter(myAdatper);//关联

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
