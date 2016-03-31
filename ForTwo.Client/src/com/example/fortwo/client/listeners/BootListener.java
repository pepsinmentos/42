package com.example.fortwo.client.listeners;

import android.content.Context;
import android.content.Intent;

public interface BootListener {
	void onBootComplete(Context context, Intent intent);
}
