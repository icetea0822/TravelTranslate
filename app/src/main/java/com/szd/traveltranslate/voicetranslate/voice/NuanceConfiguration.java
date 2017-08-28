package com.szd.traveltranslate.voicetranslate.voice;

import android.net.Uri;

import com.nuance.speechkit.PcmFormat;

/**
 * All Nuance Developers configuration parameters can be set here.
 * <p>
 * Copyright (c) 2015 Nuance Communicatios. All rights reserved.
 */
public class NuanceConfiguration {

    //All fields are required.
    //Your credentials can be found in your Nuance Developers portal, under "Manage My Apps".
    public static final String APP_KEY = "9bb8309e518c6c9a3d422482c3c121ca9bad56ff007b4639d1923d8b194e21b406515257ddbef11a00159cc4d6be8e726be4716b60b7a53149e2bc6231dd1637";
    public static final String APP_ID = "NMDPTRIAL_shengzidong_126_com20170629015822";
    public static final String SERVER_HOST = "sslsandbox-nmdp.nuancemobility.net";
    public static final String SERVER_PORT = "443";

    //    public static String language = "!LANGUAGE!";
    //    public static String LANGUAGE_CODE = (NuanceConfiguration.language.contains("!") ? "eng-USA" : NuanceConfiguration.language);


    public static final Uri SERVER_URI = Uri.parse("nmsps://" + APP_ID + "@" + SERVER_HOST + ":" + SERVER_PORT);

    //Only needed if using NLU
    public static final String CONTEXT_TAG = "!NLU_CONTEXT_TAG!";
    public static final PcmFormat PCM_FORMAT = new PcmFormat(PcmFormat.SampleFormat.SignedLinear16, 16000, 1);

}



