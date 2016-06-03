package com.robin.lazy.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.robin.lazy.cache.CacheLoaderManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private long lastTime;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonSave).setOnClickListener(this);
        findViewById(R.id.buttonLoad).setOnClickListener(this);
        textView=(TextView)findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonSave) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String area_strs = FileUtil.readAssets(MainActivity.this, "province.json");
                            lastTime = System.currentTimeMillis();
                            CacheLoaderManager.getInstance().pushString("area_strs", area_strs, 5);
                            textView.setText("保存数据用时:"+(System.currentTimeMillis() - lastTime) + "毫秒");
                        }
                    });
                }
            }).start();
        } else if (id == R.id.buttonLoad) {
            lastTime = System.currentTimeMillis();
            String area_strs=CacheLoaderManager.getInstance().loadString("area_strs");
            Toast.makeText(MainActivity.this, "加载数据用时:" + (System.currentTimeMillis() - lastTime) + "毫秒", Toast.LENGTH_SHORT).show();
            textView.setText(area_strs);
        }
    }
}