package com.main.app.entity

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "worker")
class Worker(id : String, pwd : String, name : String, email : String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private val no : Long = 0

    @Column(name = "id", length = 20)
    private val id : String = id

    @Column(name = "pwd", length = 20)
    private val pwd : String = pwd

    @Column(name = "name", length = 20)
    private val name : String = name

    @Column(name = "email", length = 40)
    private val email : String = email

    @Column(name = "isUsed")
    private val isUsed : Boolean = false

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate")
    private var createDate : Date? = null

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update")
    private var update : Date? = null

    @Column(name = "provider")
    private val provider : String = "none"

    @Column(name = "providerId")
    private val providerId : String = "none"

    fun getNo() = no

    fun getId() = id

    fun getPwd() = pwd

    fun getName() = name

    fun getEmail() = email

    fun getIsUsed() = isUsed
}