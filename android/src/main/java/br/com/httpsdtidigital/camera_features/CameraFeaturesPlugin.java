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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
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
        List<String> campos = call.argument("fields");
        List<String> camposRenomeados = new ArrayList<String>();
        for (String campo : campos) {
            camposRenomeados.add(campo.replace("_", "").toUpperCase());
        }


      CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
      ArrayList<String> retorno = new ArrayList<String>();
        try {
            String[] ids = manager.getCameraIdList();
            CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics("0");
            List<CameraCharacteristics.Key<?>> keys = cameraCharacteristics.getKeys();
            JSONObject main = new JSONObject();
            try {
                for (String id : ids) {
                    JSONObject jsonString = new JSONObject();
                    for(int i =0; i< keys.size(); i ++){
                        String nome = keys.get(i).getName().replace("android.","");
                        nome = nome.replace(".", "").toUpperCase();
                        if(camposRenomeados.contains(nome)){
                            String valor = cameraCharacteristics.get(keys.get(i)).toString();
                            jsonString.put(nome, valor).toString();
                        }
                    }
                    main.put("cameraID: " + id, jsonString);
                }
                retorno.add(main.toString());
            } catch (IllegalArgumentException e1) {
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        result.success(retorno.toString());
    } else {
      result.notImplemented();
    }
  }
}
