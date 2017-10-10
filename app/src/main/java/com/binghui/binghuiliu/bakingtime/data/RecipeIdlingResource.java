package com.binghui.binghuiliu.bakingtime.data;

import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import com.bumptech.glide.request.ResourceCallback;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecipeIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback callback;

    private AtomicBoolean isIdleNow = new AtomicBoolean(false);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean idleState) {
        isIdleNow.set(idleState);
        if (idleState && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}

