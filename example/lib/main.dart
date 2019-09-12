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
  String _camera_info = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    String camera_info;
    try {
      var teste = new List<String>();
      teste.add("FLASH_INFO_AVAILABLE");
      teste.add("LENS_FACING");
      camera_info = await CameraFeatures.getCameraFeatures(teste);
    } on PlatformException {
      camera_info = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _camera_info = camera_info;
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
              Text(_camera_info)
            ],
          ),
        ),
      ),
    );
  }
}
