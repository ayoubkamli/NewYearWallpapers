package org.room76.newyearwallpaper.service;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;

/**
 * Created by Alexey on 12/29/17.
 */

public abstract class OpenGLES2WallpaperService extends GLWallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new OpenGLES2Engine();
    }

    class OpenGLES2Engine extends GLWallpaperService.GLEngine {

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            // Check if the system supports OpenGL ES 2.0.
            final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
            final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

            if (supportsEs2) {
                // Request an OpenGL ES 2.0 compatible context.
                setEGLContextClientVersion(2);

                // On Honeycomb+ devices, this improves the performance when
                // leaving and resuming the live wallpaper.
                setPreserveEGLContextOnPause(true);

                // Set the renderer to our user-defined renderer.
                setRenderer(getNewRenderer());
            }
        }
    }

    public abstract GLSurfaceView.Renderer getNewRenderer();
}
