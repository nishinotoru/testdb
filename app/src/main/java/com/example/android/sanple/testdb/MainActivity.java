package example.com.dbsample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * メイン画面に関連するクラス
 * MainActivity
 */
public class MainActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener {

    private EditText mEditText01Product;        // 品名
    private EditText mEditText01MadeIn;         // 品名
    private EditText mEditText01Number;         // 個数
    private EditText mEditText01Price;          // 単価

    private TextView mText01Kome01;             // 品名の※印
    private TextView mText01Kome02;             // 産地の※印
    private TextView mText01Kome03;             // 個数の※印
    private TextView mText01Kome04;             // 単価の※印

    private Button mButton01Regist;             // 登録ボタン
    private Button mButton01Show;               // 表示ボタン

    private RadioGroup mRadioGroup01Show;       // 選択用ラジオボタングループ

    private Intent intent;                      // インテント

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();        // 各部品の結びつけ処理

        init();             //初期値設定

        // ラジオボタン選択時
        mRadioGroup01Show.setOnCheckedChangeListener(this);

        // 登録ボタン押下時処理
        mButton01Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // キーボードを非表示
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // DBに登録
                saveList();

            }
        });

        // 表示ボタン押下時処理
        mButton01Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent != null) {
                    startActivity(intent);      // 各画面へ遷移
                } else {
                    Toast.makeText(MainActivity.this, "ラジオボタンが選択されていません。", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {

        mEditText01Product = (EditText) findViewById(R.id.editText01Product);   // 品名
        mEditText01MadeIn = (EditText) findViewById(R.id.editText01MadeIn);     // 産地
        mEditText01Number = (EditText) findViewById(R.id.editText01Number);     // 個数
        mEditText01Price = (EditText) findViewById(R.id.editText01Price);       // 単価

        mText01Kome01 = (TextView) findViewById(R.id.text01Kome01);             // 品名の※印
        mText01Kome02 = (TextView) findViewById(R.id.text01Kome02);             // 産地※印
        mText01Kome03 = (TextView) findViewById(R.id.text01Kome03);             // 個数の※印
        mText01Kome04 = (TextView) findViewById(R.id.text01Kome04);             // 単価の※印

        mButton01Regist = (Button) findViewById(R.id.button01Regist);           // 登録ボタン
        mButton01Show = (Button) findViewById(R.id.button01Show);               // 表示ボタン

        mRadioGroup01Show = (RadioGroup) findViewById(R.id.radioGroup01);       // 選択用ラジオボタングループ

    }

    /**
     * 初期値設定 (EditTextの入力欄は空白、※印は消す)
     * init()
     */
    private void init() {
        mEditText01Product.setText("");
        mEditText01MadeIn.setText("");
        mEditText01Number.setText("");
        mEditText01Price.setText("");

        mText01Kome01.setText("");
        mText01Kome02.setText("");
        mText01Kome03.setText("");
        mText01Kome04.setText("");
        mEditText01Product.requestFocus();      // フォーカスを品名のEditTextに指定
    }

    /**
     * ラジオボタン選択処理
     * onCheckedChanged()
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton01Product:         // 品名一覧(ListView×ArrayAdapter)を選択した場合
                intent = new Intent(MainActivity.this, SelectSheetProduct.class);
                break;
            case R.id.radioButton01ListView:        // ListView表示を選択した場合
                intent = new Intent(MainActivity.this, SelectSheetListView.class);
                break;
            case R.id.radioButton01TableLayout:     // TableLayout表示を選択した場合
                intent = new Intent(MainActivity.this, SelectSheetTable.class);
                break;
        }
    }

    /**
     * EditTextに入力したテキストをDBに登録
     * saveDB()
     */
    private void saveList() {

        // 各EditTextで入力されたテキストを取得
        String strProduct = mEditText01Product.getText().toString();
        String strMadeIn = mEditText01MadeIn.getText().toString();
        String strNumber = mEditText01Number.getText().toString();
        String strPrice = mEditText01Price.getText().toString();

        // EditTextが空白の場合
        if (strProduct.equals("") || strMadeIn.equals("") || strNumber.equals("") || strPrice.equals("")) {

            if (strProduct.equals("")) {
                mText01Kome01.setText("※");     // 品名が空白の場合、※印を表示
            } else {
                mText01Kome01.setText("");      // 空白でない場合は※印を消す
            }

            if (strMadeIn.equals("")) {
                mText01Kome02.setText("※");     // 産地が空白の場合、※印を表示
            } else {
                mText01Kome02.setText("");      // 空白でない場合は※印を消す
            }

            if (strNumber.equals("")) {
                mText01Kome03.setText("※");     // 個数が空白の場合、※印を表示
            } else {
                mText01Kome03.setText("");      // 空白でない場合は※印を消す
            }

            if (strPrice.equals("")) {
                mText01Kome04.setText("※");     // 単価が空白の場合、※印を表示
            } else {
                mText01Kome04.setText("");      // 空白でない場合は※印を消す
            }

            Toast.makeText(MainActivity.this, "※の箇所を入力して下さい。", Toast.LENGTH_SHORT).show();

        } else {        // EditTextが全て入力されている場合

            // 入力された単価と個数は文字列からint型へ変換
            int iNumber = Integer.parseInt(strNumber);
            int iPrice = Integer.parseInt(strPrice);

            // DBへの登録処理
            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.openDB();                                         // DBの読み書き
            dbAdapter.saveDB(strProduct, strMadeIn, iNumber, iPrice);   // DBに登録
            dbAdapter.closeDB();                                        // DBを閉じる

            init();     // 初期値設定

        }

    }

    // メニュー未使用
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // メニュー未使用
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}