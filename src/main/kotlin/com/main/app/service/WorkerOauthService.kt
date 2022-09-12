package com.main.app.service

import com.main.app.entity.Worker
import com.main.app.repository.WorkerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.UUID
import javax.transaction.Transactional

@Service
class WorkerOauthService : DefaultOAuth2UserService(){
    @Autowired
    private lateinit var workerRepository: WorkerRepository

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val worker = super.loadUser(userRequest)

        val provider = userRequest?.clientRegistration?.registrationId
        val providerId = worker.attributes["sub"]
        val uuid = UUID.randomUUID().toString().substring(0,6)
        val id = provider+providerId
        val pwd = "패스워드$uuid"
        val name = provider+"_"+providerId
        val email = worker.attributes["email"]

        // 중복체크
        val r = workerRepository.findWorkersByNameContaining(name)

        if(r.isEmpty()){
            workerRepository.save(Worker(id,pwd,name, email as String))
        }

        return super.loadUser(userRequest)
    }
}