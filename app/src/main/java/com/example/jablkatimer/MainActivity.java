package com.example.jablkatimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView scoreText, timerText;
    Button startButton;
    GridLayout gridLayout;
    ImageView[] images;
    ListView scoreListView;

    int score = 0;
    int timeLeft = 15;
    CountDownTimer timer;
    Random random = new Random();

    int currentVisible = -1;

    ArrayList<String> scoreHistory;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);
        startButton = findViewById(R.id.startButton);
        gridLayout = findViewById(R.id.grid);
        scoreListView = findViewById(R.id.scoreList);

        scoreHistory = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoreHistory);
        scoreListView.setAdapter(adapter);

        images = new ImageView[gridLayout.getChildCount()];

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            images[i] = (ImageView) gridLayout.getChildAt(i);
            images[i].setVisibility(View.INVISIBLE);

            final int index = i;
            images[i].setOnClickListener(v -> {
                if (index == currentVisible) {
                    score++;
                    scoreText.setText("Pkt: " + score);
                    showRandomImage();
                }
            });
        }

        startButton.setOnClickListener(v -> startGame());
    }

    private void startGame() {
        score = 0;
        timeLeft = 15;

        scoreText.setText("Pkt: 0");
        timerText.setText("Czas: 15");

        startButton.setEnabled(false);

        startTimer();
        showRandomImage();
    }

    private void startTimer() {
        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (millisUntilFinished / 1000);
                timerText.setText("Czas: " + timeLeft);
            }

            @Override
            public void onFinish() {
                timerText.setText("Czas: 0");
                endGame();
            }
        }.start();
    }

    private void showRandomImage() {
        for (ImageView img : images) {
            img.setVisibility(View.INVISIBLE);
        }

        currentVisible = random.nextInt(images.length);
        images[currentVisible].setVisibility(View.VISIBLE);
    }

    private void endGame() {
        for (ImageView img : images) {
            img.setVisibility(View.INVISIBLE);
        }

        scoreHistory.add(0, "Wynik: " + score + " pkt");
        adapter.notifyDataSetChanged();

        startButton.setEnabled(true);
    }
}