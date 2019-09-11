import 'dart:async';

import 'package:flutter/services.dart';

class CameraFeatures {
  static const MethodChannel _channel =
      const MethodChannel('camera_features');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getCameraFeatures');
    return version;
  }
}
