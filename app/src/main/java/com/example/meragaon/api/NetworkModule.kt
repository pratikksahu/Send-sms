package com.example.meragaon.api

import android.content.Context
import androidx.room.Room
import com.example.meragaon.api.local.SmsSentDAO
import com.example.meragaon.api.local.SmsSentDatabase
import com.example.meragaon.api.remote.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val username = "AC9e82334f9d08597429d553b0aae0c432"
    private const val password = "6f41e6a08a1bdfb5e61caddaf8253514"
    private const val BASE_URL = "https://api.twilio.com"

    @Singleton
    @Provides
    fun getInstance(client:OkHttpClient) : Retrofit{

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
    }
    @Provides
    fun provideClient():OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(username, password))
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()

    @Provides
    fun provideSMSService(retrofit: Retrofit):SMSService{
        return retrofit.create(SMSService::class.java)
    }

    @Provides
    @Singleton
    fun provideSmsSentDatabase(@ApplicationContext context: Context): SmsSentDatabase{
        return Room.databaseBuilder(context,
            SmsSentDatabase::class.java,
        "SmsSentDatabase").build()
    }
    @Provides
    fun provideSmsSentDAO(smsSentDatabase: SmsSentDatabase) : SmsSentDAO{
        return smsSentDatabase.smsSentDAO()
    }

}

class BasicAuthInterceptor(user: String, password: String) :
    Interceptor {
    private val credentials: String

    init {
        credentials = Credentials.basic(user, password)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Content-Type","application/x-www-form-urlencoded")
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}
