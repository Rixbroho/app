package com.example.app.model

/**
 * Model representing a User in the Restaurant Finder App.
 * Includes fields for profile management and account settings.
 */
data class UserModel(
    val userId: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val contact: String = "",
    val dob: String = ""
) {
    /**
     * Converts the model into a Map for Firebase Firestore updates.
     * This allows updating specific profile fields without overwriting the entire document.
     */
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "email" to email,
            "firstName" to firstName,
            "lastName" to lastName,
            "contact" to contact,
            "dob" to dob
        )
    }
}