package com.educaciontacna.drednot.ui.utils;

import com.educaciontacna.drednot.BuildConfig;

/**
 * Created by rogergcc on 5/02/2021.
 * Copyright Ⓒ 2021 . All rights reserved.
 */
public class MyConstants {

    private MyConstants() {
    }

    public static final int TIPO_ESTADO_PENDIENTE = 0;
    public static final int TIPO_ESTADO_NOTIFICADO = 1;
    public static final int TIPO_ESTADO_3 = 2;
    public static final int TIPO_ESTADO_4 = 3;

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";
    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String APP_VERSION_NAME = BuildConfig.VERSION_NAME;
}