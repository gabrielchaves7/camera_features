import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:camera_features/camera_features.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _cameraInfo = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    String cameraInfo;
    try {
      var teste = new List<String>();
      teste.add("FLASH_INFO_AVAILABLE");
      teste.add("LENS_FACING");
      cameraInfo = await CameraFeatures.getCameraFeatures(teste);
    } on PlatformException {
      cameraInfo = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _cameraInfo = cameraInfo;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: ListView(
            children: <Widget>[
              Text(_cameraInfo)
            ],
          ),
        ),
      ),
    );
  }
}
