package com.bookpin.app.auth

import com.bookpin.app.auth.response.AuthResponse
import com.bookpin.domain.auth.SocialLoginClient
import com.bookpin.domain.auth.SocialUserInfo
import com.bookpin.domain.auth.TokenPair
import com.bookpin.domain.auth.TokenProvider
import com.bookpin.domain.user.SocialType
import com.bookpin.domain.user.User
import com.bookpin.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val socialLoginClients: List<SocialLoginClient>,
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider
) {

    @Transactional
    fun socialLogin(provider: SocialType, accessToken: String): AuthResponse {
        val socialUserInfo = getSocialUserInfo(provider, accessToken)
        val existingUser = userRepository.findBySocialIdAndSocialProvider(socialUserInfo.socialId, provider)
        val user = findOrCreateUser(existingUser, socialUserInfo, provider)
        val tokenPair = createTokenPair(user.id)

        return AuthResponse(
            userId = user.id,
            accessToken = tokenPair.accessToken,
            refreshToken = tokenPair.refreshToken,
            isNewUser = existingUser == null
        )
    }

    fun refreshToken(refreshToken: String): TokenPair {
        require(tokenProvider.validateToken(refreshToken)) { "Invalid refresh token" }

        val userId = tokenProvider.getUserIdFromToken(refreshToken)
        userRepository.findById(userId)
            ?: throw IllegalArgumentException("User not found")

        return createTokenPair(userId)
    }

    private fun getSocialUserInfo(provider: SocialType, accessToken: String): SocialUserInfo {
        val client = socialLoginClients.find { it.getProviderType() == provider }
            ?: throw IllegalArgumentException("Unsupported social provider: $provider")

        return client.getUserInfo(accessToken)
    }

    private fun findOrCreateUser(
        existingUser: User?,
        socialUserInfo: SocialUserInfo,
        provider: SocialType
    ): User {
        return if (existingUser != null) {
            updateExistingUser(existingUser, socialUserInfo)
        } else {
            createNewUser(socialUserInfo, provider)
        }
    }

    private fun updateExistingUser(user: User, socialUserInfo: SocialUserInfo): User {
        val updatedUser = user.updateProfile(
            email = socialUserInfo.email,
            nickname = socialUserInfo.nickname,
            profileImageUrl = socialUserInfo.profileImageUrl
        )
        return userRepository.save(updatedUser)
    }

    private fun createNewUser(socialUserInfo: SocialUserInfo, provider: SocialType): User {
        val newUser = User(
            socialId = socialUserInfo.socialId,
            socialProvider = provider,
            email = socialUserInfo.email,
            nickname = socialUserInfo.nickname,
            profileImageUrl = socialUserInfo.profileImageUrl
        )
        return userRepository.save(newUser)
    }

    private fun createTokenPair(userId: Long): TokenPair {
        return TokenPair(
            accessToken = tokenProvider.createAccessToken(userId),
            refreshToken = tokenProvider.createRefreshToken(userId)
        )
    }
}
