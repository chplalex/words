object Versions {
    const val coroutines = "1.4.2"
    const val retrofit = "2.9.0"
    const val lifecycle = "2.0.0"
    const val navigation = "2.3.3"
    const val koin = "2.2.2"
    const val glide = "4.12.0"
    const val room = "2.3.0-beta02"
}

object Libs {
    //AndroidX
    const val androidx_appcompat = "androidx.appcompat:appcompat:1.3.0-beta01"
    const val androidx_swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    //Design
    const val design_material = "com.google.android.material:material:1.3.0"
    //Kotlin
    const val kotlin_core = "androidx.core:core-ktx:1.3.2"
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
    const val test_runner= "androidx.test:runner:1.3.0"
    const val test_espresso_core = "androidx.test.espresso:espresso-core:3.3.0"
}