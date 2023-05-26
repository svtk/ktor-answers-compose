package remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.QuestionData
import model.Question
import model.User

// TODO Where to set it up properly?
expect val REMOTE_URL: String
val KOTLIN_QUESTIONS_API_URL get() = "$REMOTE_URL:8080/"

class QuestionsRepository {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getQuestions(): List<Question> {
        val response = client.request("$KOTLIN_QUESTIONS_API_URL/questions2")
        if (!response.status.isSuccess()) {
            println(response) // TODO logger
            return listOf()
        }
        return response.body()
    }

    suspend fun getQuestion(id: Long): Question? {
        val response = client.request("$KOTLIN_QUESTIONS_API_URL/questions2/{$id}")
        if (!response.status.isSuccess()) return null
        return response.body()
    }

    suspend fun getUsers(): List<User> {
        val response = client.request("$KOTLIN_QUESTIONS_API_URL/users2")
        if (!response.status.isSuccess()) return listOf()
        return response.body()
    }

    suspend fun submitQuestion(
        title: String,
        body: String,
        tags: String
    ): Question {
        val user = getUsers().random()
        val response = client.post("$KOTLIN_QUESTIONS_API_URL/questions2") {
            contentType(ContentType.Application.Json)
            setBody(
                QuestionData(
                    title = title,
                    body = body,
                    owner = user
                )
            )
        }
        return response.call.body<Question>()
    }
}