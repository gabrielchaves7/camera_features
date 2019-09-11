package br.com.httpsdtidigital.camera_features;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.annotation.TargetApi;
import android.hardware.camera2.*;
import android.content.Context;
import android.app.Activity;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** CameraFeaturesPlugin */
public class CameraFeaturesPlugin implements MethodCallHandler {
  Activity activity;
  Context context;
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "camera_features");
    channel.setMethodCallHandler(new CameraFeaturesPlugin(registrar.activity(), registrar.context()));
  }

  public CameraFeaturesPlugin(Activity activity, Context context) {
      this.activity = activity;
      this.context = context;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getCameraFeatures")) {




      CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
      ArrayList<String> retorno = new ArrayList<String>();
        try {
            JSONArray array = new JSONArray();

          String[] ids = manager.getCameraIdList();
          for (String id : ids) {
            CameraCharacteristics cameraCha = manager.getCameraCharacteristics(id);
            List<CameraCharacteristics.Key<?>> keys = cameraCha.getKeys();

            for(int i =0; i<keys.size(); i++){
                JSONObject item = new JSONObject();
                item.put(keys.get(i).getName(), cameraCha.get(keys.get(i)).toString());
                array.put(item);
            }
          }
            retorno.add(array.toString());
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        result.success(retorno.toString());
    } else {
      result.notImplemented();
    }
  }
}
