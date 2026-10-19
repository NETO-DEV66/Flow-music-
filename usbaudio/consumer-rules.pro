# Keep classes and constructors looked up from jni_bridge.cpp.
-keep class com.tunevol.usbaudio.model.DacFormat { <init>(...); }
-keep class com.tunevol.usbaudio.model.DacCapabilities { <init>(...); }

# Keep names used by the exported JNI method symbols.
-keepclasseswithmembernames class com.tunevol.usbaudio.UsbAudioDriver { native <methods>; }
