import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:camera_features/camera_features.dart';

void main() {
  const MethodChannel channel = MethodChannel('camera_features');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '[{"cameraID: 0":{"FLASHINFOAVAILABLE":"true","LENSFACING":"1"},"cameraID: 1":{"FLASHINFOAVAILABLE":"true","LENSFACING":"1"}}]';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    var teste = new List<String>();
    teste.add("FLASH_INFO_AVAILABLE");
    teste.add("LENS_FACING");
    expect(await CameraFeatures.getCameraFeatures(teste), '[{"cameraID: 0":{"FLASHINFOAVAILABLE":"true","LENSFACING":"1"},"cameraID: 1":{"FLASHINFOAVAILABLE":"true","LENSFACING":"1"}}]');
  });
}
