package com.itay.hw11;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class carView extends View {

    private Bitmap background;
    private Bitmap car;
    private Bitmap obstacle;
    private Paint scorePaint = new Paint();
    private Paint toastPaint = new Paint();
    private Bitmap lifeBar[] = new Bitmap[2];
    private Boolean canDrawNewObstacle = true;
    private int lifeCounter = 3;
    private int obstacleX, obstacleY, obstacleSpeed;
    private int carPosition = -1;
    private int maxCarLeft = 1;
    private int maxCarRight = -2;
    private final int carY = 1600;
    private int carX;
    private int touch = 0;
    private int location_2 = 710;
    private int location_1 = 540;
    private int location0 = 350;
    private int location1 = 180;


    private Bitmap resizedCar;
    private Bitmap resizedBackground;
    private Bitmap resizedlifeBar[] = new Bitmap[2];
    private Bitmap resizedObstacle;


    public carView(Context context) {
        super(context);

        car = BitmapFactory.decodeResource(getResources(), R.drawable.car1);
        resizedCar = Bitmap.createScaledBitmap(car, 190, 240, true);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.roadbackground_png);
        resizedBackground = Bitmap.createScaledBitmap(background, 1080, 2340, true);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        obstacle = BitmapFactory.decodeResource(getResources(), R.drawable.rock_image);
        resizedObstacle = Bitmap.createScaledBitmap(obstacle, 150, 150, true);

        lifeBar[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart2);
        lifeBar[1] = BitmapFactory.decodeResource(getResources(), R.drawable.grey_heart);
        resizedlifeBar[0] = Bitmap.createScaledBitmap(lifeBar[0], 80, 80, true);
        resizedlifeBar[1] = Bitmap.createScaledBitmap(lifeBar[1], 80, 80, true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        obstacleSpeed = -52;
        obstacleY = obstacleY - obstacleSpeed;

        canvas.drawBitmap(resizedBackground, 0, 0, null);

        if (canDrawNewObstacle) {
            obstacleX = getNewLocation();
        }
        canvas.drawBitmap(resizedObstacle, obstacleX, obstacleY, null);

        setLifeBar(canvas, lifeCounter);

        if (carPosition == -1) {
            carX = location_1;
            canvas.drawBitmap(resizedCar, carX, carY, null);

        } else if (carPosition == -2) {
            carX = location_2;
            canvas.drawBitmap(resizedCar, carX, carY, null);
        } else if (carPosition == 0) {
            carX = location0;
            canvas.drawBitmap(resizedCar, carX, carY, null);

        } else if (carPosition == 1) {
            carX = location1;
            canvas.drawBitmap(resizedCar, carX, carY, null);
        }

        if (obstacleY > 2340) {
            obstacleY = 200;
            canDrawNewObstacle = true;
        }

        if (lifeCounter == 0) {
            lifeCounter = 3;
        }
        if (hitObstacleChecker(carX, obstacleY)) {
            obstacleY = -100;
            lifeCounter--;

            vibrate();
            Toast.makeText(getContext(), "Dodge the rocks", Toast.LENGTH_LONG).show();

        }
    }

    private int getNewLocation() {
        double randomNum = ((int) (Math.random() * 10)) % 4;

        canDrawNewObstacle = false;
        if (randomNum == 0) {
            return location_2;
        }
        if (randomNum == 1) {
            return location_1;
        }
        if (randomNum == 2) {
            return location0;
        }
        if (randomNum == 3) {
            return location1;
        }
        return 540;
    }

    public boolean hitObstacleChecker(int carX, int obstacleY) {
        if (1360 < obstacleY && obstacleY < 1600 && carX == obstacleX) {
            return true;

        }
        return false;
    }

    public void setLifeBar(Canvas canvas, int lifeCounter) {
        int heartLocation = 720;
        for (int i = 0; i < lifeCounter; i++) {
            canvas.drawBitmap(resizedlifeBar[0],heartLocation,35,null);
            heartLocation += 90;

        }
        for (int i = 0; i < (3 - lifeCounter); i++) {
            canvas.drawBitmap(resizedlifeBar[1], heartLocation, 35,null);
            heartLocation+=90;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        if (touchX < 540) {
            moveLeft();
            return true;
        } else {
            moveRight();
        }
        return true;
    }

    private void moveRight() {
        if (carPosition > maxCarRight) carPosition--;
    }

    private void moveLeft() {
        if (carPosition < maxCarLeft) carPosition++;
    }
    private void vibrate() {
        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
