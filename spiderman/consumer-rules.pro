#crash的可视化日志工具
-keep class com.simple.spiderman.** { *; }
-keepnames class com.simple.spiderman.** { *; }
-keep public class * extends android.app.Activity
-keep class * implements Android.os.Parcelable {
    public static final Android.os.Parcelable$Creator *;
}
# support
-keep public class * extends android.support.annotation.** { *; }
-keep public class * extends android.support.v4.content.FileProvider
# androidx
-keep public class * extends androidx.annotation.** { *; }
-keep public class * extends androidx.core.content.FileProvider