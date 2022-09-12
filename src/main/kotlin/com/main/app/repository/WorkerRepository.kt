package com.main.app.repository

import com.main.app.entity.Worker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WorkerRepository : JpaRepository<Worker, Long> {

    fun findWorkersByNameContaining(name : String) : List<Worker>

    @Query("select w from Worker w where w.isUsed = true")
    fun findActivatedWorker() : List<Worker>

    @Modifying
    @Query("update Worker w set w.isUsed = :isActivate where w.no = :no")
    fun updateWorkerStatus(no: Long, isActivate : Boolean) : Int

    fun findWorkerByIdAndPwdAndIsUsedIsTrue(id: String, pwd : String) : Worker?
}