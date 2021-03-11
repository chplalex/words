import org.gradle.api.JavaVersion

object Config {
    //const val application_id = "com.chplalex.words"
    const val sdk_compile = 30
    const val sdk_min = 21
    const val sdk_target = 30
    const val build_tools_version = "30.0.3"
    val java_version = JavaVersion.VERSION_1_8
    const val jvm_target = "1.8"
    const val test_instrumentation_runner = "androidx.test.runner.AndroidJUnitRunner"
}

object Release {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val base = ":base"
    const val dynamic = ":dynamic"
    const val history = ":history"
    const val main = ":main"
    const val model = ":model"
    const val navigation = ":navigation"
    const val repo = ":repo"
    const val utils = ":utils"
}

object Versions {
    const val coroutines = "1.4.2"
    const val glide = "4.12.0"
    const val koin = "2.2.2"
    const val lifecycle = "2.0.0"
    const val navigation = "2.3.3"
    const val retrofit = "2.9.0"
    const val room = "2.3.0-beta02"
}

object Libs {
    //Gradle
    const val gradle = "com.android.tools.build:gradle:4.1.2"
    //AndroidX
    const val androidx_appcompat = "androidx.appcompat:appcompat:1.3.0-beta01"
    const val androidx_swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    //Design
    const val design_material = "com.google.android.material:material:1.3.0"
    //Google Play
    const val google_play_core = "com.google.android.play:core:1.6.3"
    //Kotlin
    const val kotlin_core = "androidx.core:core-ktx:1.3.2"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31"
    //Coroutines
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    //Retrofit 2
    const val retrofit_retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofit_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
    //LifeCycle
    const val lifecycle_viewmodel = "android.arch.lifecycle:viewmodel:${Versions.lifecycle}"
    const val lifecycle_extensions = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
    // Navigation
    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigation_safe_args = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    //Koin
    const val koin_scope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koin_viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val koin_fragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
    // Picasso
    const val picasso = "com.squareup.picasso:picasso:2.71828"
    // Glide
    const val glide_glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    // Room
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    //Test
    const val test_junit = "junit:junit:4.13.2"
    const val androidx_test_ext = "androidx.test.ext:junit:1.1.2"
    const val androidx_test_runner = "androidx.test:runner:1.3.0"
    const val androidx_test_espresso_core = "androidx.test.espresso:espresso-core:3.3.0"
    const val androidx_test_annotation = "androidx.annotation:annotation:1.1.0"
}