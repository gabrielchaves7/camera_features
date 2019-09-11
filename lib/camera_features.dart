import 'dart:async';

import 'package:flutter/services.dart';

class CameraFeatures {
  static const MethodChannel _channel =
      const MethodChannel('camera_features');

  static Future<String> getCameraFeatures(List<String> fields) async {
    final String version = await _channel.invokeMethod('getCameraFeatures', <String, dynamic> {
      'fields': fields
    });
    return version;
  }
}
