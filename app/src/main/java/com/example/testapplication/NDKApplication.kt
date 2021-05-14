package com.example.testapplication

import android.app.Application

class NDKApplication : Application() {
    init {
        System.loadLibrary("ndk-lib")
//        System.loadLibrary("dlib");
    }
}

/**
3.5 Loading the local library
Before using the local method,
we need to inform the JVM to load the local library.
Since the local library only needs to be loaded once
and must be used before the native method,
the loading process can be placed into the Application class,
so that the program will automatically
link when it starts. Local library.
 */