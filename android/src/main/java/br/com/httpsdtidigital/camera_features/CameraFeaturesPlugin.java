package br.com.httpsdtidigital.camera_features;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.annotation.TargetApi;
import android.graphics.Camera;
import android.hardware.camera2.*;
import android.content.Context;
import android.app.Activity;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<String> campos = call.argument("fields");
      CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
      ArrayList<String> retorno = new ArrayList<String>();
        try {
            Map<String, Object> temp = new HashMap<String, Object>();

          String[] ids = manager.getCameraIdList();
          for (String id : ids) {
            Class<?> c = manager.getCameraCharacteristics(id).getClass();
            Field[] fields = c.getFields();

            for( Field field : fields ){
              try {
                  if(campos.contains(field.getName())){
                      temp.put(field.getName(), field.get(c).toString());
                  }
              } catch (IllegalArgumentException e1) {
              } catch (IllegalAccessException e) {
                  e.printStackTrace();
              }
            }
            temp.toString();
          }

            retorno.add(temp.toString());
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        result.success(retorno.toString());
    } else {
      result.notImplemented();
    }
  }
}
