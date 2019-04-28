package com.example.shin.final_project;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class cvs {
    public static Canvas canvas = new Canvas();
    public static SurfaceHolder ourHolder;
    public static boolean isCheck = true;
    public static boolean backG = true;
    public static boolean startGround1 = true;
    public static boolean startGround = true;
    public static boolean firstSet = true;
    public static boolean isAtck = false;
    public static boolean isBulletMoving = false;
    public static boolean isEnemyMoving = true;
    public static int cvsWidth;
    public static int cvsHeight;
    public static int percent = 10;
    public static int stage = 1;
    public static int gametimer = 60;

}
