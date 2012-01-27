package com.aramgutang.games.snakesaver;

import android.app.Activity;
import android.os.Bundle;

public class SnakesaverActivity extends Activity {
	private SnakesaverView view;
	private SnakesaverThread thread;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use res/layout/main.xml
        setContentView(R.layout.main);
        
        this.view = (SnakesaverView)findViewById(R.id.snakesaver);
        this.thread = this.view.thread;
    }
}