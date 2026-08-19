# Keep Activity classes
-keep public class android.app.Activity {
    public void onCreate(android.os.Bundle);
}

# Keep our main activity
-keep class com.minimalos.launcher.** { *; }

# Remove logging in release build
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
