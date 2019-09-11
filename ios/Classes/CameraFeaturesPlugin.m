#import "CameraFeaturesPlugin.h"
#import <camera_features/camera_features-Swift.h>

@implementation CameraFeaturesPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftCameraFeaturesPlugin registerWithRegistrar:registrar];
}
@end
