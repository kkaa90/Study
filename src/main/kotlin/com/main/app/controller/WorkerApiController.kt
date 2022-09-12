package com.main.app.controller

import com.main.app.model.CreateWorkerRequestModel
import com.main.app.model.LoginRequestModel
import com.main.app.model.SimpleWorkerModel
import com.main.app.service.WorkerOauthService
import com.main.app.service.WorkerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/api/worker")
@RestController
class WorkerApiController {
    @Autowired
    private lateinit var workerService: WorkerService

    @Autowired
    private lateinit var workerOauthService: WorkerOauthService

    @PostMapping("/create")
    fun create(@RequestBody req: CreateWorkerRequestModel): ResponseEntity<Void> {
        val isCreateComplete = workerService.createWorker(req)
        return if (isCreateComplete) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/search-by-name")
    fun searchByName(@RequestParam("name") name: String): ResponseEntity<List<SimpleWorkerModel>> {
        val resultList = workerService.searchWorkerByName(name)
        return if (resultList != null) {
            if (resultList.isNotEmpty()) {
                ResponseEntity.ok(resultList)
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/get-active-worker-list")
    fun getActiveWorkerList() : ResponseEntity<List<SimpleWorkerModel>> {
        val resultList = workerService.getActivateWorkerList()
        return if (resultList != null) {
            if (resultList.isNotEmpty()) {
                ResponseEntity.ok(resultList)
            } else {
                ResponseEntity.notFound().build()
            }
        } else {
            ResponseEntity.internalServerError().build()
        }
    }

    @PutMapping("/activate-user/{no}")
    fun activeUser(@PathVariable("no") no : Long) : ResponseEntity<Void>{
        val isUpdateComplete = workerService.activateWorker(no)
        return if(isUpdateComplete){
            ResponseEntity.ok().build()
        }else{
            ResponseEntity.internalServerError().build()
        }
    }

    @PutMapping("/deactivate-user/{no}")
    fun deActiveUser(@PathVariable("no") no : Long) : ResponseEntity<Void>{
        val isUpdateComplete = workerService.deActivateWorker(no)
        return if(isUpdateComplete){
            ResponseEntity.ok().build()
        }else{
            ResponseEntity.internalServerError().build()
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody req : LoginRequestModel) : ResponseEntity<SimpleWorkerModel>{
        val worker = workerService.getActivateWorker(req.id, req.pwd)
        return if(worker != null){
            ResponseEntity.ok(worker)
        }else{
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/delete/{no}")
    fun delete(@PathVariable("no") no : Long) : ResponseEntity<Void>{
        val isDeleteComplete = workerService.deleteWorkerById(no)
        return if(isDeleteComplete){
            ResponseEntity.ok().build()
        }else{
            ResponseEntity.internalServerError().build()
        }
    }
}