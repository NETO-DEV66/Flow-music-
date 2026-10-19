# Keep classes and constructors looked up from jni_bridge.cpp.
-keep class com.flowtune.usbaudio.model.DacFormat { <init>(...); }
-keep class com.flowtune.usbaudio.model.DacCapabilities { <init>(...); }

# Keep names used by the exported JNI method symbols.
-keepclasseswithmembernames class com.flowtune.usbaudio.UsbAudioDriver { native <methods>; }
